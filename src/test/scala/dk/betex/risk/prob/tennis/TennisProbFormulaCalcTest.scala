package dk.betex.risk.prob.tennis

import org.junit._
import Assert._
import dk.betex.risk.prob.tennis.TennisProbFormulaCalc$

class TennisProbFormulaCalcTest {

  @Test def point_probability_is_0_5 {
    assertEquals(0.5, TennisProbFormulaCalc.gameProb(0.5),0)
  }

  @Test def point_probability_is_1 {
    assertEquals(1, TennisProbFormulaCalc.gameProb(1),0)
  }

  @Test def point_probability_is_0 {
    assertEquals(0, TennisProbFormulaCalc.gameProb(0),0)
  }

  @Test def point_probability_is_0_7 {
    assertEquals(0.9007, TennisProbFormulaCalc.gameProb(0.7),0.0001)
  }

  @Test def point_probability_is_0_3 {
    assertEquals(0.0992, TennisProbFormulaCalc.gameProb(0.3),0.0001)
  }
  
   @Test def point_probability_is_0_55 {
    assertEquals(0.6231, TennisProbFormulaCalc.gameProb(0.55),0.0001)
  }

  @Test def point_probability_is_0_45 {
    assertEquals(0.3768, TennisProbFormulaCalc.gameProb(0.45),0.0001)
  }

}