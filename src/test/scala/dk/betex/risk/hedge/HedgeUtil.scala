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
import org.apache.commons.math.util.MathUtils
import scala.collection._

object HedgeUtil {

    def getExpectedProfit(bets: List[IBet], marketPrices: Map[Long, Tuple2[Double, Double]]): MarketExpectedProfit = {
    val probs = ProbabilityCalculator.calculate(marketPrices, 1)
    ExpectedProfitCalculator.calculate(bets, probs, 0, 0)
  }

   def getExpectedProfit(hedgeBet: HedgeBet, bets: List[IBet], marketPrices: Map[Long, Tuple2[Double, Double]]): MarketExpectedProfit = {
    val allBets = toBet(hedgeBet) :: bets
    getExpectedProfit(allBets, marketPrices)
  }

   def toBet(hedgeBet:HedgeBet):IBet = 
     new Bet(1, 123, hedgeBet.betSize, hedgeBet.betPrice, hedgeBet.betType, M, hedgeBet.marketId, hedgeBet.runnerId, 1000, Option(2000))
   
  def round(hedgeBet: HedgeBet): HedgeBet = {
    hedgeBet.copy(betSize = MathUtils.round(hedgeBet.betSize, 2))
  }

  def round(runnersIfWin: Map[Long, Double]): Map[Long, Double] = {
    runnersIfWin.mapValues(MathUtils.round(_, 2))
  }
  
}