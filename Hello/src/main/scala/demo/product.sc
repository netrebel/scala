object exercise {

  def product(f: Int => Int)(a: Int, b: Int): Int = {
    if (a > b) 1
    else f(a) * product(f)(a + 1, b)
  }

  product(x => x * x)(2, 3)

  def factorial(n: Int) = product(x => x)(1, n)

  factorial(4)

  def sum(f: Int => Int)(a: Int, b: Int): Int = {
    if (a > b) 0
    else f(a) + product(f)(a + 1, b)
  }

  sum(x => x)(2, 3)

}