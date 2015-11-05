package safarionline

import scala.collection.mutable

/**
 * @author Miguel Reyes
 *         Date: 11/5/15
 *         Time: 3:32 PM
 */
object Calculator {

  def handleOperator(token: String, stack: mutable.Stack[Int]): Boolean = token match {
    case "+" =>
      val rhs = stack.pop()
      val lhs = stack.pop()
      stack.push(lhs + rhs)
      true
    case "-" =>
      val rhs = stack.pop()
      val lhs = stack.pop()
      stack.push(lhs - rhs)
      true
    case "*" =>
      val rhs = stack.pop()
      val lhs = stack.pop()
      stack.push(lhs * rhs)
      true
    case "/" =>
      val rhs = stack.pop()
      val lhs = stack.pop()
      stack.push(lhs / rhs)
      true
    case _ => false
  }

  def handleNumber(token: String, stack: mutable.Stack[Int]): Boolean = try {
    stack.push(token.toInt)
    true
  } catch {
    case _: NumberFormatException => false
  }

  def calculate(expression: String): Int = {
    val stack = new mutable.Stack[Int]

    for (token <- expression.split(" "))
      if (!handleOperator(token, stack) && !handleNumber(token, stack))
        throw new IllegalArgumentException("Invalid argument: " + token)

    stack.pop()
  }

  def main(args: Array[String]) {
    if (args.length != 1) {
      throw new IllegalArgumentException("Usage: Calculator <expression>")
    } else {
      println(calculate(args(0)))
    }
  }
}
