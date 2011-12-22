package dk.betex.risk.prob.tennis

trait TennisProbCalc {

  /**
   * Calculates probability of winning a tennis game by player on serve.
   *
   * @param pointProb Probability of winning a point by a player on serve
   */
  def gameProb(pointProb: Double): Double

  /**
   * Calculates probability of winning a tennis tie break.
   *
   * @param pointProbOnServe Probability of winning a point when serving
   * @param pointProbOnReceive Probability of winning a point when receiving serve
   */
  def tiebreakProb(pointProbOnServe: Double, pointProbOnReceive: Double): Double

  /**
   * Calculates probability of winning a tennis set.
   *
   * @param pointProbOnServe Probability of winning a point when serving
   * @param pointProbOnReceive Probability of winning a point when receiving serve
   */
  def setProb(pointProbOnServe: Double, pointProbOnReceive: Double): Double
  
   /**
   * Calculates probability of winning a tennis three-set match.
   *
   * @param pointProbOnServe Probability of winning a point when serving
   * @param pointProbOnReceive Probability of winning a point when receiving serve
   */
  def match3SetProb(pointProbOnServe: Double, pointProbOnReceive: Double): Double
  
   /**
   * Calculates probability of winning a tennis five-set match.
   *
   * @param pointProbOnServe Probability of winning a point when serving
   * @param pointProbOnReceive Probability of winning a point when receiving serve
   */
  def match5SetProb(pointProbOnServe: Double, pointProbOnReceive: Double): Double
}