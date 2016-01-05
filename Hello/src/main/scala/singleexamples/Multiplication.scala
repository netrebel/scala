package singleexamples

/**
  * @author Miguel Reyes
  *         Date: 1/4/16
  *         Time: 3:07 PM
  */

trait Operations {
  def multiply(): Int
}

case class Multiplication(value1: Int, value2: Int) {

  override def toString: String = s"My values are $value1 and $value2"

  def multiply(): Int = {
    value1 * value2
  }
}

object Multiplication {

  def main(args: Array[String]) {
    val multiplication = Multiplication(2, 3)
    println(multiplication)
    println(multiplication.multiply())

  }
}
