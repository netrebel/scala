package safarionline.calculator

import scala.collection.mutable

/**
  * @author Miguel Reyes
  *         Date: 11/5/15
  *         Time: 3:32 PM
  */
object Calculator {

  trait Operator {
    def operate(lhs: Int, rhs: Int): Int
  }

  object Operator {

    val operators: Map[String, Operator] =
      Map("+" -> Add, "-" -> Subtract, "*" -> Multiply, "/" -> Divide)

    def unapply(token: String): Option[Operator] = {
      operators.get(token)

    }
  }

  case object Add extends Operator {
    override def operate(lhs: Int, rhs: Int): Int = lhs + rhs

    override val toString = "+"
  }

  case object Subtract extends Operator {
    override def operate(lhs: Int, rhs: Int): Int = lhs - rhs

    override val toString = "-"
  }

  case object Multiply extends Operator {
    override def operate(lhs: Int, rhs: Int): Int = lhs * rhs

    override val toString = "*"
  }

  case object Divide extends Operator {
    override def operate(lhs: Int, rhs: Int): Int = lhs / rhs

    override val toString = "/"
  }

  object Number {
    def unapply(token: String): Option[Int] = try {
      Some(token.toInt)
    } catch {
      case _: NumberFormatException => None
    }
  }

  def calculate(expression: String): Int = {
    val stack = new mutable.Stack[Int]

    for (token <- expression.split(" ")) token match {
      case Number(num) => stack.push(num)
      case Operator(op) =>
        val rhs: Int = stack.pop()
        val lhs: Int = stack.pop()
        stack.push(op.operate(lhs, rhs))

      case _ => throw new IllegalArgumentException("Illegal character" + token)
    }

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
