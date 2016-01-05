package singleexamples

import org.scalamock.scalatest.MockFactory


/**
  * @author Miguel Reyes
  *         Date: 1/4/16
  *         Time: 3:28 PM
  */
class MultiplicationTest extends UnitSpec with MockFactory {

  behavior of "MultiplicationTest"

  it should "multiply" in {
    val multiplication = Multiplication(2, 1)
    assert(2 == multiplication.multiply())
  }

}
