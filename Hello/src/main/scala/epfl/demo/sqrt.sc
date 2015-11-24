object session {

  def sqrt(x: Double) = {
    def sqrtIter(guess: Double): Double =
      if (isGoodEnough(guess))
        guess
      else
        sqrtIter(improve(guess))

    def isGoodEnough(guess: Double) =
      Math.abs(guess * guess - x) < 0.01

    def improve(guess: Double) =
      (guess + x / guess) / 2


    sqrtIter(1.0)
  }

  sqrt(2)

}