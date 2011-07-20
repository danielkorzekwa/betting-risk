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

  def calculateHedgeBet(bets: List[IBet], marketPrices: Map[Long, Tuple2[Double, Double]], hedgeRunnerId: Long): Option[HedgeBet] = {
    if (bets.isEmpty) None
    require(bets.map(_.marketId).distinct.size == 1, "All bets must be on the same market")

    val marketId = bets.head.marketId
    val probs = ProbabilityCalculator.calculate(marketPrices, 1)
    val riskReport = ExpectedProfitCalculator.calculate(bets, probs, 0, 0)
    val ifWin = riskReport.ifWin(hedgeRunnerId)
    val ifLose = riskReport.ifLose(hedgeRunnerId)
    val bestPrices = marketPrices(hedgeRunnerId)

    val hedgeBet = if (ifWin > ifLose && !bestPrices._2.isNaN) {
      val betPrice = bestPrices._2
      val betSize = (ifWin - ifLose) / betPrice
      Option(HedgeBet(betSize, betPrice, LAY, marketId, hedgeRunnerId))

    } else if (ifLose > ifWin && !bestPrices._1.isNaN) {
      val betPrice = bestPrices._1
      val betSize = (ifLose - ifWin) / betPrice
      Option(HedgeBet(betSize, betPrice, BACK, marketId, hedgeRunnerId))
    } else None

    hedgeBet
  }

}