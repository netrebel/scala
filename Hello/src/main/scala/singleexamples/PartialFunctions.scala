package singleexamples

object PartialFunctions extends App {

  val doubleEvens: PartialFunction[Int, Int] = {
    case x: Int if (x % 2) == 0 => x * 2
  }

  val tripleOdds: PartialFunction[Int, Int] = {
    case x: Int if (x % 2) != 0 => x * 3
  }

  val printEven: PartialFunction[Int, String] = {
    case x: Int if (x % 2) == 0 => "Even"
  }

  val printOdds: PartialFunction[Int, String] = {
    case x: Int if (x % 2) != 0 => "Odd"
  }

  val whatToDo = doubleEvens orElse tripleOdds andThen (printEven orElse printOdds)
  assert("Even" == whatToDo(4))
  assert("Odd" == whatToDo(3))
}

