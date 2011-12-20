package dk.betex.risk.prob.tennis

import scala.Math._

/**This calculator is based on research paper:
 * Probability Formulas and Statistical Analysis in Tennis, O'Malley, a. James, Journal of Quantitative Analysis in Sports, 2008
 */
object TennisProbFormulaCalc extends TennisProbCalc{

  /**Calculates probability of winning a tennis game by player on serve.
   * 
   * @param pointProb Probability of winning a point by a player on serve
   */
  def gameProb(pointProb:Double):Double = {
    val p = pointProb
    pow(p,4) * (15 - 4*p - (10*p*p)/(1 - 2*p * (1 - p)))
  }
}