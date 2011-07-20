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

class ThreeRunnersMarketWithBackBetTest {

  private val bets = new Bet(1, 123, 10, 3, BACK, M, 1, 11, 1000, Option(2000)) :: Nil
  private val marketPrices = Map(11l -> Tuple2(1.99, 2d), 12l -> Tuple2(3d, 3.05), 13l -> Tuple2(5.9, 6d))

  @Before
  def before {
    val epBefore = getExpectedProfit(bets, marketPrices)
    assertEquals(5.038, epBefore.marketExpectedProfit, 0.001)
    assertEquals(Map(11l -> 20d, 12l -> -10d, 13l -> -10d), epBefore.runnersIfWin)
  }

  @Test
  def hedge_runner11 {

    val hedgeBet = HedgeBetsCalculator.calculateHedgeBet(bets, marketPrices, 11).get
    assertEquals(HedgeBet(15.00, 2, LAY, 1, 11), round(hedgeBet))

    val epAfter = getExpectedProfit(hedgeBet, bets, marketPrices)
    assertEquals(5, epAfter.marketExpectedProfit, 0.001)
    assertEquals(Map(11l -> 5d, 12l -> 5d, 13l -> 5.0), round(epAfter.runnersIfWin))
  }

  @Test
  def hedge_runner12_then_runner13_then_runner12 {

    val hedgeBet = HedgeBetsCalculator.calculateHedgeBet(bets, marketPrices, 12).get
    assertEquals(HedgeBet(7.49, 3, BACK, 1, 12), round(hedgeBet))

    val epAfter = getExpectedProfit(hedgeBet, bets, marketPrices)
    assertEquals(4.977, epAfter.marketExpectedProfit, 0.001)
    assertEquals(Map(11l -> 12.51, 12l -> 4.98d, 13l -> -17.49), round(epAfter.runnersIfWin))

    val bets2 = toBet(hedgeBet) :: bets

    val hedgeBet2 = HedgeBetsCalculator.calculateHedgeBet(bets2, marketPrices, 13).get
    assertEquals(HedgeBet(4.58, 5.9, BACK, 1, 13), round(hedgeBet2))

    val epAfter2 = getExpectedProfit(hedgeBet2, bets2, marketPrices)
    assertEquals(4.939, epAfter2.marketExpectedProfit, 0.001)
    assertEquals(Map(11l -> 7.93, 12l -> 0.4d, 13l -> 4.94), round(epAfter2.runnersIfWin))

    val bets3 = toBet(hedgeBet2) :: bets2

    val hedgeBet3 = HedgeBetsCalculator.calculateHedgeBet(bets3, marketPrices, 12).get
    assertEquals(HedgeBet(2.26, 3.0, BACK, 1, 12), round(hedgeBet3))

    val epAfter3 = getExpectedProfit(hedgeBet3, bets3, marketPrices)
    assertEquals(4.921, epAfter3.marketExpectedProfit, 0.001)
    assertEquals(Map(11l -> 5.67, 12l -> 4.92d, 13l -> 2.68), round(epAfter3.runnersIfWin))
  }

}