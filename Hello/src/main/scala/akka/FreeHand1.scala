package akka

import akka.stream.scaladsl.{Flow, Sink, Source}


/**
  * @author Miguel Reyes
  *         Date: 11/23/15
  *         Time: 5:39 PM
  */
object FreeHand1 extends Setup("FreeHand1") with App {
  override def demo() = {
    val src = Source(1 to 10)
    val transformation = Flow[Int].map(n => n * 2)
    val dst = Sink.foreach(println)

    src via transformation to dst
  }
}
