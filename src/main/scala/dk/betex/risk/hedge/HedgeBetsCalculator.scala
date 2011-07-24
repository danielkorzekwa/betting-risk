package dk.betex.risk.hedge

import dk.betex.api._
import IBet.BetTypeEnum._
import dk.betex.risk.prob._
import dk.betex.risk.liability._
import scala.collection._

/**
 * Manages risk position by calculating hedge bets for market runners.
 *
 * @author korzekwad
 */
trait HedgeBetsCalculator {

  /**
   * Calculates hedge bet for a given runner and user's risk position represented by his bets and market prices.
   *
   * @param bets User's matched bets.
   * @param marketPrices Current market prices.
   * @param hedgeRunnerId The market runner that hedge bet is calculated for.
   *
   * @return Calculated hedge bet, which should be placed by a user to improve his risk position in a market.
   */
  def calculateHedgeBet(bets: List[IBet], marketPrices: Map[Long, Tuple2[Double, Double]], hedgeRunnerId: Long): Option[HedgeBet]

  case class HedgeBet(betSize: Double, betPrice: Double, betType: BetTypeEnum, marketId: Long, runnerId: Long)
}

object HedgeBetsCalculator extends HedgeBetsCalculator {

  /**hedge bet = (ifWin-ifLose)/price, it could also calculated as (marketExpected-ifWin/(price-1).*/
  def calculateHedgeBet(bets: List[IBet], marketPrices: Map[Long, Tuple2[Double, Double]], hedgeRunnerId: Long): Option[HedgeBet] = {
    if (bets.isEmpty) return None
    require(bets.map(_.marketId).distinct.size == 1, "All bets must be on the same market")
    require(marketPrices.contains(hedgeRunnerId),"Runner not found:" + hedgeRunnerId)
    
    val marketId = bets.head.marketId
    val probs = ProbabilityCalculator.calculate(marketPrices, 1)
    val riskReport = ExpectedProfitCalculator.calculate(bets, probs, 0, 0)
    val ifWin = riskReport.ifWin(hedgeRunnerId)
    val ifLose = riskReport.ifLose(hedgeRunnerId)
    val bestPrices = marketPrices(hedgeRunnerId)

    val ifWinIfLoseDelta = ifWin - ifLose
    val hedgeBet = if (ifWinIfLoseDelta > 0 && !bestPrices._2.isNaN) {
      val betPrice = bestPrices._2
      val betSize = Math.abs(ifWinIfLoseDelta) / betPrice
      Option(HedgeBet(betSize, betPrice, LAY, marketId, hedgeRunnerId))

    } else if (ifWinIfLoseDelta < 0 && !bestPrices._1.isNaN) {
      val betPrice = bestPrices._1
      val betSize = Math.abs(ifWinIfLoseDelta) / betPrice
      Option(HedgeBet(betSize, betPrice, BACK, marketId, hedgeRunnerId))
    } else None

    /**Minimum bet size is 1.*/
    if(hedgeBet.isDefined && hedgeBet.get.betSize >= 1) hedgeBet else None
  }

}