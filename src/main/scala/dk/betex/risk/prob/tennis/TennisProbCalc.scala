package dk.betex.risk.prob.tennis

trait TennisProbCalc {

  /**Calculates probability of winning a tennis game by player on serve.
   * 
   * @param pointProb Probability of winning a point by a player on serve
   */
  def gameProb(pointProb:Double):Double
  
}