package demo

import akka.actor.ActorSystem
import akka.http.Http
import akka.http.model.{HttpRequest, HttpResponse, StatusCodes}
import akka.stream.FlowMaterializer
import akka.stream.scaladsl.FlowGraphImplicits._
import akka.stream.scaladsl._
import demo.Util._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * @author Miguel Reyes
  *         Date: 1/25/16
  *         Time: 3:34 PM
  */
object Boot2 extends App {

  // the actor system to use. Required for flowmaterializer and HTTP.
  // passed in implicit
  implicit val system = ActorSystem("Streams")
  implicit val materializer = FlowMaterializer()

  // start the server on the specified interface and port.
  val serverBinding1 = Http().bind(interface = "localhost", port = 8090)
  serverBinding1.connections.foreach { connection =>
    connection.handleWith(broadCastMergeFlow)
  }

  val bCast = Broadcast[HttpRequest]

  // some basic steps that each retrieve a different ticket value (as a future)
  val step1 = Flow[HttpRequest].mapAsync[String](getTickerHandler("GOOG"))
  val step2 = Flow[HttpRequest].mapAsync[String](getTickerHandler("AAPL"))
  val step3 = Flow[HttpRequest].mapAsync[String](getTickerHandler("MSFT"))

  // We'll use the source and output provided by the http endpoint
  val in = UndefinedSource[HttpRequest]
  val out = UndefinedSink[HttpResponse]
  // when an element is available on one of the inputs, take
  // that one, igore the rest
  val merge = Merge[String]
  // since merge doesn't output a HttpResponse add an additional map step.
  val mapToResponse = Flow[String].map[HttpResponse](
    (inp: String) => HttpResponse(status = StatusCodes.OK, entity = inp)
  )
  // define another flow. This uses the merge function which
  // takes the first available response
  val broadCastMergeFlow = Flow[HttpRequest, HttpResponse]() {
    implicit builder =>

      bCast ~> step1 ~> merge
      in ~> bCast ~> step2 ~> merge ~> mapToResponse ~> out
      bCast ~> step3 ~> merge

      (in, out)
  }

  // waits for events on the three inputs and returns a response
  val zip = ZipWith[String, String, String, HttpResponse](
    (inp1, inp2, inp3) => new HttpResponse(status = StatusCodes.OK, entity = inp1 + inp2 + inp3)
  )

  // define a flow which broadcasts the request to the three
  // steps, and uses the zipWith to combine the elements before
  val broadCastZipFlow = Flow[HttpRequest, HttpResponse]() {
    implicit builder =>

      bCast ~> step1 ~> zip.input1
      in ~> bCast ~> step2 ~> zip.input2 ~> out
      bCast ~> step3 ~> zip.input3

      (in, out)
  }

  def getTickerHandler(tickName: String)(request: HttpRequest): Future[String] = {
    // query the database
    val ticker = Database.findTicker(tickName)

    Thread.sleep(Math.random() * 1000 toInt)

    // use a simple for comprehension, to make
    // working with futures easier.
    for {
      t <- ticker
    } yield {
      t match {
        case Some(bson) => convertToString(bson)
        case None => ""
      }
    }
  }

}
