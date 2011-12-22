package dk.betex.risk.prob.tennis

import org.junit._
import Assert._
import dk.betex.risk.prob.tennis.TennisProbFormulaCalc$

class TennisProbFormulaCalcTest {

  /**Tests for gameProb, tiebreakProb, setProb, matchProb*/
  @Test def tiebreak_probability_serve_receive_0_5_and_0_5 {
    assertEquals(0.5, TennisProbFormulaCalc.gameProb(0.5), 0)

    assertEquals(0.5, TennisProbFormulaCalc.tiebreakProb(0.5, 0.5), 0.0)

    assertEquals(0.5, TennisProbFormulaCalc.setProb(0.5, 0.5), 0.0)
  }

  @Test def tiebreak_probability_serve_receive_1_and_0_5 {
    assertEquals(1, TennisProbFormulaCalc.gameProb(1), 0)
    assertEquals(0.5, TennisProbFormulaCalc.gameProb(0.5), 0)

    assertEquals(1, TennisProbFormulaCalc.tiebreakProb(1, 0.5), 0)
    assertEquals(1, TennisProbFormulaCalc.tiebreakProb(0.5, 1), 0)

    assertEquals(1, TennisProbFormulaCalc.setProb(1, 0.5), 0.0)
    assertEquals(1, TennisProbFormulaCalc.setProb(0.5, 1), 0.0)
  }

  @Test def tiebreak_probability_serve_receive_0_and_0_5 {
    assertEquals(0, TennisProbFormulaCalc.gameProb(0), 0)
    assertEquals(0.5, TennisProbFormulaCalc.gameProb(0.5), 0)

    assertEquals(0, TennisProbFormulaCalc.tiebreakProb(0, 0.5), 0)
    assertEquals(0, TennisProbFormulaCalc.tiebreakProb(0.5, 0), 0)

    assertEquals(0, TennisProbFormulaCalc.setProb(0, 0.5), 0.0)
    assertEquals(0, TennisProbFormulaCalc.setProb(0.5, 0.0), 0.0)
  }

  @Test def tiebreak_probability_serve_receive_0_6_and_0_4 {
    assertEquals(0.7357, TennisProbFormulaCalc.gameProb(0.6), 0.0001)
    assertEquals(0.2642, TennisProbFormulaCalc.gameProb(0.4), 0.0001)

    assertEquals(0.5, TennisProbFormulaCalc.tiebreakProb(0.6, 0.4), 0.0001)
    assertEquals(0.5, TennisProbFormulaCalc.tiebreakProb(0.4, 0.6), 0.0001)

    assertEquals(0.5, TennisProbFormulaCalc.setProb(0.6, 0.4), 0.00001)
    assertEquals(0.5, TennisProbFormulaCalc.setProb(0.4, 0.6), 0.0)

  }

  @Test def tiebreak_probability_serve_receive_0_8_and_0_2 {
    assertEquals(0.9782, TennisProbFormulaCalc.gameProb(0.8), 0.0001)
    assertEquals(0.0217, TennisProbFormulaCalc.gameProb(0.2), 0.0001)

    assertEquals(0.5, TennisProbFormulaCalc.tiebreakProb(0.8, 0.2), 0.0001)
    assertEquals(0.5, TennisProbFormulaCalc.tiebreakProb(0.2, 0.8), 0.0001)

    assertEquals(0.5, TennisProbFormulaCalc.setProb(0.8, 0.2), 0.00001)
    assertEquals(0.5, TennisProbFormulaCalc.setProb(0.2, 0.8), 0.00001)
  }

  @Test def tiebreak_probability_serve_receive_0_4_and_0_3 {
    assertEquals(0.26427, TennisProbFormulaCalc.gameProb(0.4), 0.0001)
    assertEquals(0.09921, TennisProbFormulaCalc.gameProb(0.3), 0.0001)

    assertEquals(0.11182, TennisProbFormulaCalc.tiebreakProb(0.4, 0.3), 0.0001)
    assertEquals(0.11182, TennisProbFormulaCalc.tiebreakProb(0.3, 0.4), 0.0001)
    /**opponent winning tiebreak prob.*/
    assertEquals(0.8881, TennisProbFormulaCalc.tiebreakProb(0.7, 0.6), 0.0001)
    assertEquals(1, TennisProbFormulaCalc.tiebreakProb(0.4, 0.3) + TennisProbFormulaCalc.tiebreakProb(0.7, 0.6), 0.00001)

    assertEquals(0.0039, TennisProbFormulaCalc.setProb(0.4, 0.3), 0.0001)
    assertEquals(0.0039, TennisProbFormulaCalc.setProb(0.3, 0.4), 0.0001)
    /**opponent winning set prob.*/
    assertEquals(0.9960, TennisProbFormulaCalc.setProb(0.7, 0.6), 0.0001)
    assertEquals(1, TennisProbFormulaCalc.setProb(0.4, 0.3) + TennisProbFormulaCalc.setProb(0.7, 0.6), 0.00001)
  }

  @Test def tiebreak_probability_serve_receive_0_7_and_0_4 {
    assertEquals(0.9007, TennisProbFormulaCalc.gameProb(0.7), 0.0001)
    assertEquals(0.26427, TennisProbFormulaCalc.gameProb(0.4), 0.0001)

    assertEquals(0.66297, TennisProbFormulaCalc.tiebreakProb(0.7, 0.4), 0.0001)
    assertEquals(0.66297, TennisProbFormulaCalc.tiebreakProb(0.4, 0.7), 0.0001)
    /**opponent winning tiebreak prob.*/
    assertEquals(0.3370, TennisProbFormulaCalc.tiebreakProb(0.6, 0.3), 0.0001)
    assertEquals(1, TennisProbFormulaCalc.tiebreakProb(0.7, 0.4) + TennisProbFormulaCalc.tiebreakProb(0.3, 0.6), 0.00001)

    assertEquals(0.7948, TennisProbFormulaCalc.setProb(0.7, 0.4), 0.0001)
    assertEquals(0.7948, TennisProbFormulaCalc.setProb(0.4, 0.7), 0.0001)
    /**opponent winning set prob.*/
    assertEquals(0.2051, TennisProbFormulaCalc.setProb(0.6, 0.3), 0.0001)
    assertEquals(1, TennisProbFormulaCalc.setProb(0.7, 0.4) + TennisProbFormulaCalc.setProb(0.3, 0.6), 0.00001)

  }

}