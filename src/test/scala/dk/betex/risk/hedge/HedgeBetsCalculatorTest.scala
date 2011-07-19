package dk.betex.risk.hedge

import org.junit._
import Assert._
import dk.betex._
import dk.betex.api._
import dk.betex.api.IBet.BetTypeEnum._
import dk.betex.api.IBet.BetStatusEnum._
import org.junit._
import HedgeBetsCalculator._
import dk.betting.risk.prob._
import dk.betting.risk.liability._
class HedgeBetsCalculatorTest {

  @Test
  def calculateHedgeBet_single_back_bet_in_a_market_with_positive_profit {
    val bets = new Bet(1, 123, 10, 2, BACK, M, 1, 11, 1000, Option(2000)) :: Nil
    val marketPrices = Map(11l -> Tuple2(1.5, 1.51), 12l -> Tuple2(2.99, 3.0))

    val epBefore = getExpectedProfit(bets, marketPrices)
    assertEquals(2.982, epBefore.marketExpectedProfit, 0.001)
    assertEquals(Map(11l -> 9.5d, 12l -> -10d), epBefore.runnersIfWin)
    
    val hedgeBet = HedgeBetsCalculator.calculateHedgeBet(bets, marketPrices, 11)
    assertEquals(HedgeBet(-1, 1.5, LAY, 1, 11), hedgeBet)

    val epAfter = getExpectedProfit(hedgeBet, bets, marketPrices)
    assertEquals(-1, epAfter.marketExpectedProfit, 0.001)
    assertEquals(Map(11l -> 0d, 12l -> 0d), epAfter.runnersIfWin)

  }

  private def getExpectedProfit(bets: List[IBet], marketPrices: Map[Long, Tuple2[Double, Double]]): MarketExpectedProfit = {
    val probs = ProbabilityCalculator.calculate(marketPrices, 1)
    ExpectedProfitCalculator.calculate(bets, probs, 0.05, 1000)
  }

  private def getExpectedProfit(hedgeBet: HedgeBet, bets: List[IBet], marketPrices: Map[Long, Tuple2[Double, Double]]): MarketExpectedProfit = {
    val allBets = new Bet(1, 123, hedgeBet.betSize, hedgeBet.betPrice, hedgeBet.betType, M, hedgeBet.marketId, hedgeBet.runnerId, 1000, Option(2000)) :: bets
    getExpectedProfit(allBets, marketPrices)
  }

}