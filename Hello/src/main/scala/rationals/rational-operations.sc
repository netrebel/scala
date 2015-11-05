object exercise {

  val x = new Rational(1, 3)
  val y = new Rational(5, 7)
  val z = new Rational(3, 2)
  x.add(y)
  x.neg
  x.sub(y).sub(z)
  y.add(y)
  x.less(y)
  x.max(y)

}

class Rational(x: Int, y: Int) {

  require(y != 0, "denominator must be nonzero")

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
  private val g = gcd(x, y)

  def numerator = x/g

  def denominator = y/g

  def add(that: Rational) =
    new Rational(
      numerator * that.denominator + that.numerator * denominator,
      denominator * that.denominator)

  def neg: Rational =
    new Rational(
      -1 * numerator,
      denominator)

  def sub(that: Rational): Rational = add(that.neg)

  def less(that: Rational) = numerator * that.denominator < that.numerator * denominator

  def max(that: Rational) = if(this.less(that)) that else this

  override def toString = numerator + "/" + denominator
}