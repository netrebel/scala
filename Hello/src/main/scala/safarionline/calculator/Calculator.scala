package safarionline.calculator

/**
  * @author Miguel Reyes
  *         Date: 11/5/15
  *         Time: 3:32 PM
  */
object Calculator {

  type Operator = (Int, Int) => Int

  object Operator {

    val operators: Map[String, Operator] =
      Map("+" -> (_ + _),
        "-" -> (_ - _),
        "*" -> (_ * _),
        "/" -> (_ / _))

    val tokens = operators map {
      _.swap
    }

    def unapply(token: String): Option[Operator] = {
      operators.get(token)

    }
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

  def step(stack: List[Expression], token: String): List[Expression] = token match {
    case Number(num) => NumberExpression(num) :: stack
    case Operator(op) => stack match {
      //Decompose stack into lhs, rhs and the rest. The rest
      case lhs :: rhs :: rest => OperationExpression(lhs, rhs, op) :: rest
      //In case there is not enough operands
      case _ => throw new IllegalArgumentException("Not enough operands")
    }
    case _ => throw new IllegalArgumentException("Illegal character" + token)
  }

  /**
    * Parse a postfix notation expression
    * @param expression Expression
    * @return Value
    */
  def parse(expression: String): Expression = {
    val tokens = expression.split(" ")

    //Fold left on the tokens to create the stack, calling "step" for each member of the stack
    val stack = tokens.foldLeft(List.empty[Expression]) {
      step
    }
    //Return the result stored in the head
    stack.head
  }

  def calculate(expression: Expression): Int = expression match {
    case NumberExpression(value) => value
    case OperationExpression(lhs, rhs, op) => op.apply(calculate(lhs), calculate(rhs))
  }

  def toInfix(expression: Expression): String = expression match {
    case NumberExpression(value) => value.toString
    case OperationExpression(lhs, rhs, op) => s"(${toInfix(lhs)} ${Operator.tokens(op)} ${toInfix(rhs)})"
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
