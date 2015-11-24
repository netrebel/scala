object mergesort {

  def msort(xs: List[Int]): List[Int] = {
    val n = xs.length / 2
    println("-- " + xs)

    if (n == 0) xs
    else {
      def merge(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match {
        case (Nil, ys) => { println("case 1: " + ys); ys }
        case (xs, Nil) => { println("case 2: " + xs); xs}
        case (x :: xs1, y :: ys1) => {
//          println("Case 3, x : " + x + " y : " + y)
//          println(".. xs:" + xs + " ys:" + ys)
//          println(".. xs1:" + xs1 + " ys1:" + ys1)
          if (x < y) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
        }
      }
      val (fst, snd) = xs splitAt n
      merge(msort(fst), msort(snd))
    }
  }

  val list = List(2, -4, 3, 1)
  msort(list)
}