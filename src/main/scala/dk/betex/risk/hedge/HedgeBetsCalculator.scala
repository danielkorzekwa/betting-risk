package dk.betex.risk.hedge

import dk.betex.api._
import IBet.BetTypeEnum._
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
  def calculateHedgeBet(bets: List[IBet], marketPrices: Map[Long, Tuple2[Double, Double]], hedgeRunnerId: Long): HedgeBet

  case class HedgeBet(betSize: Double, betPrice: Double, betType: BetTypeEnum, marketId: Long, runnerId: Long)
}

object HedgeBetsCalculator extends HedgeBetsCalculator {

  def calculateHedgeBet(bets: List[IBet], marketPrices: Map[Long, Tuple2[Double, Double]], hedgeRunnerId: Long): HedgeBet = {
    throw new UnsupportedOperationException("Not implemented yet")
  }

}