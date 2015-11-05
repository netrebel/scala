import week4._
object example {
  def nth[T](n: Int, xs: List[T]): T =
    if (n == 0) xs.head
    else nth(n - 1, xs.tail)
  val list = new Cons(1, new Cons(2, new Cons(3, new Nil)))
//  nth(2, list)
}