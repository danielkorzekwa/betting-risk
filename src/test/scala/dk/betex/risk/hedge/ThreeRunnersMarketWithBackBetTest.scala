package dk.betex.risk.hedge

import org.junit._
import Assert._
import dk.betex._
import dk.betex.api._
import dk.betex.api.IBet.BetTypeEnum._
import dk.betex.api.IBet.BetStatusEnum._
import org.junit._
import HedgeBetsCalculator._
import dk.betex.risk.prob._
import dk.betex.risk.liability._
import scala.collection._
import HedgeUtil._

class ThreeRunnersMarketWithTwoBackBetsTest {

  private val bets = new Bet(1, 123, 10, 3, BACK, M, 1, 11, 1000, Option(2000)) ::
    new Bet(1, 123, 10, 5, BACK, M, 1, 12, 1000, Option(2000)) :: Nil
  private val marketPrices = Map(11l -> Tuple2(1.99, 2d), 12l -> Tuple2(3d, 3.05), 13l -> Tuple2(5.9, 6d))

  @Before
  def before {
    val epBefore = getExpectedProfit(bets, marketPrices)
    assertEquals(11.569, epBefore.marketExpectedProfit, 0.001)
    assertEquals(Map(11l -> 10d, 12l -> 30d, 13l -> -20d), epBefore.runnersIfWin)
  }

  @Test
  def hedge_runner11 {

    val hedgeBet = HedgeBetsCalculator.calculateHedgeBet(bets, marketPrices, 11).get
    assertEquals(HedgeBet(1.58, 1.99, BACK, 1, 11), round(hedgeBet))

    val epAfter = getExpectedProfit(hedgeBet, bets, marketPrices)
    assertEquals(11.565, epAfter.marketExpectedProfit, 0.001)
    assertEquals(Map(11l -> 11.57d, 12l -> 28.42d, 13l -> -21.58), round(epAfter.runnersIfWin))
  }
  
  @Test
  def hedge_runner12 {

    val hedgeBet = HedgeBetsCalculator.calculateHedgeBet(bets, marketPrices, 12).get
    assertEquals(HedgeBet(9.03, 3.05, LAY, 1, 12), round(hedgeBet))

    val epAfter = getExpectedProfit(hedgeBet, bets, marketPrices)
    assertEquals(11.493, epAfter.marketExpectedProfit, 0.001)
    assertEquals(Map(11l -> 19.03d, 12l -> 11.49d, 13l -> -10.97), round(epAfter.runnersIfWin))
  }
  
  @Test
  def hedge_runner12_then_runner11 {

    val hedgeBet = HedgeBetsCalculator.calculateHedgeBet(bets, marketPrices, 12).get
    assertEquals(HedgeBet(9.03, 3.05, LAY, 1, 12), round(hedgeBet))

    val epAfter = getExpectedProfit(hedgeBet, bets, marketPrices)
    assertEquals(11.493, epAfter.marketExpectedProfit, 0.001)
    assertEquals(Map(11l -> 19.03d, 12l -> 11.49d, 13l -> -10.97), round(epAfter.runnersIfWin))
    
      val hedgeBet2 = HedgeBetsCalculator.calculateHedgeBet(toBet(hedgeBet) :: bets, marketPrices, 11).get
    assertEquals(HedgeBet(7.55, 2, LAY, 1, 11), round(hedgeBet2))

    val epAfter2 = getExpectedProfit(hedgeBet2, toBet(hedgeBet) :: bets, marketPrices)
    assertEquals(11.474, epAfter2.marketExpectedProfit, 0.001)
    assertEquals(Map(11l -> 11.47d, 12l -> 19.05d, 13l -> -3.42), round(epAfter2.runnersIfWin))
    
  }

}