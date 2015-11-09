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

  sealed trait Expression

  case class NumberExpression(value: Int) extends Expression

  case class OperationExpression(lhs: Expression, rhs: Expression, op: Operator) extends Expression

  /**
    * Parse a postfix notation expression
    * @param expression Expression
    * @return Value
    */
  def parse(expression: String): Expression = {
    val stack = new mutable.Stack[Expression]

    for (token <- expression.split(" ")) token match {
      case Number(num) => stack.push(NumberExpression(num))
      case Operator(op) =>
        val rhs = stack.pop()
        val lhs = stack.pop()
        stack.push(OperationExpression(lhs, rhs, op))

      case _ => throw new IllegalArgumentException("Illegal character" + token)
    }

    stack.pop()

  }

  def calculate(expression: Expression): Int = expression match {
    case NumberExpression(value) => value
    case OperationExpression(lhs, rhs, op) => op.operate(calculate(lhs), calculate(rhs))
  }

  def toInfix(expression: Expression): String = expression match {
    case NumberExpression(value) => value.toString
    case OperationExpression(lhs, rhs, op) => s"(${toInfix(lhs)} $op ${toInfix(rhs)})"
  }

  def main(args: Array[String]) {
    if (args.length != 1) {
      throw new IllegalArgumentException("Usage: Calculator <expression>")
    } else {
      val expression = parse(args(0))
      println(s"${toInfix(expression)} = ${calculate(expression)}")
    }
  }
}
