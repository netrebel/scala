package akka

import java.io.File

import akka.stream.io.{SynchronousFileSink, Framing, SynchronousFileSource}
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration.MILLISECONDS


/**
  * @author Miguel Reyes
  *         Date: 11/24/15
  *         Time: 12:49 PM
  */
object FreeHand4 extends Setup("FreeHand4") with App {
  override def demo(): RunnableGraph[_] = {

    val delim = ByteString("\n")
    val dir = new File("./src/main/resources")

    val src = SynchronousFileSource(new File(dir, "words.txt")) via
      Framing.delimiter(delim, maximumFrameLength = Int.MaxValue, allowTruncation = true).map(_.utf8String)

    val clock = Source.tick(FiniteDuration(25, MILLISECONDS), FiniteDuration(25, MILLISECONDS), ())

    val dst = Flow[String].map { l => ByteString(l) ++ delim } to
      SynchronousFileSink(new File(dir, "results.txt"))

    // Intermediate step
    // Keep.left tell it to keep only the counter
    val progress = Flow[String].conflate(_ => 0x1)((c, p) => c + 1).
      zipWith(clock)(Keep.left).
      scan(0x1)(_ + _) to Sink.foreach(count => println(s"Lines read: $count"))

    //Sends data in two directions. Progress is an intermediate step.
    src alsoTo progress to dst

  }


}
