/**
  * @author Miguel Reyes
  *         Date: 11/12/15
  *         Time: 12:17 PM
  */
package object functions {

  /**
    * Example: map(List(1,2,3), { a: Int => a * 2})
    *
    * @param list Initial List
    * @param fn Function that takes parameter A and turns it into B
    * @tparam A Parameter
    * @tparam B Parameter
    * @return List of new elements of type B after applying the function.
    */
  def map[A, B](list: List[A], fn: A => B): List[B] = list match {
    case head :: tail => fn(head) :: map(tail, fn)
    case _ => Nil
  }

  /**
    *
    * Example: filter(List(1,2,3,4,5), {a: Int => a % 2 == 0})
    *
    * @param list Initial list
    * @param fn Function that takes parameter A and returns a Boolean
    * @tparam A Parameter to be evaluated
    * @return List of elements that match the function.
    */
  def filter[A](list: List[A], fn: A => Boolean): List[A] = list match {
    case head :: tail =>
      val rest = filter(tail, fn)
      if (fn(head))
        head :: rest
      else
        rest
    case _ => Nil
  }

  /**
    * Example: foldLeft(List(1,2,3), 0, { (acc: Int, e: Int) => acc + e }
    *
    * @param list Initial list
    * @param acc Accumulator of type B
    * @param fn Function that takes the accumulator (B) and an element of the list (A) and updates the accumulator (B)
    * @tparam B Accumulator
    * @tparam A Element of the list
    * @return Value accumulated
    */
  def foldLeft[A, B](list: List[A], acc: B, fn: (B, A) => B): B = list match {
    case head :: tail =>
      foldLeft(tail, fn(acc, head), fn)

    case _ => acc

  }

}
