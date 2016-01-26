package demo

import akka.actor.ActorSystem
import akka.http.Http
import akka.stream.FlowMaterializer
import akka.http.model._
import akka.stream.scaladsl._
import akka.stream.scaladsl.Source
import akka.stream.scaladsl.FlowGraphImplicits._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * @author Miguel Reyes
  *         Date: 1/26/16
  *         Time: 3:09 PM
  */
/**
  * Simple HTTP client created with akka-http
  */
object Client extends App {

  // the actor system to use. Required for flowmaterializer and HTTP.
  // passed in implicit
  implicit val system = ActorSystem("ServerTest")
  implicit val materializer = FlowMaterializer()

  val httpClient1 = Http(system).outgoingConnection("localhost", 8090).flow
  val httpClient2 = Http(system).outgoingConnection("localhost", 8091).flow

  // define a sink that will process the answer
  // we could also process this as a flow
  val printChunksConsumer = Sink.foreach[HttpResponse] { res =>
    if (res.status == StatusCodes.OK) {

      println("Recieved response : " + res);
      res.entity.getDataBytes().map {
        chunk =>
          System.out.println("Chunk: " + chunk.decodeString(HttpCharsets.`UTF-8`.value).substring(0, 80))
      }.to(Sink.ignore).run()
    } else
      println(res.status)
  }

  // we need to set allow cycles since internally the httpclient
  // has some cyclic flows (apparently)
  // we construct a sink, to which we connect a later to define source.
  val reqFlow2: Sink[HttpRequest] = Sink[HttpRequest]() { implicit b =>
    b.allowCycles()
    val source = UndefinedSource[HttpRequest]
    val bcast = Broadcast[HttpRequest]
    val concat = Concat[HttpResponse]

    // simple graph. Duplicate the request, send twice.
    // concat the result.
    bcast ~> httpClient1 ~> concat.first
    source ~> bcast ~> httpClient1 ~> concat.second ~> printChunksConsumer
    source
  }

  // make two calls, both return futures, first one shows direct linked sinks and
  // sources. Second one makes yse if our graph.

  // make number of calls
  val res = 1 to 5 map (i => {
    Source.single(HttpRequest()).to(reqFlow2).run().get(printChunksConsumer)
  })
  val f = Future.sequence(res)

  // make some calls with filled in request URI
  val f3 = Source.single(HttpRequest(uri = Uri("/getAllTickers"))).via(httpClient2).runWith(printChunksConsumer)
  val f4 = Source.single(HttpRequest(uri = Uri("/get?ticker=ADAT"))).via(httpClient2).runWith(printChunksConsumer)
  val f5 = Source.single(HttpRequest(uri = Uri("/get?tikcer=FNB"))).via(httpClient2).runWith(printChunksConsumer)

  for {
    f2Result <- f
    f2Result <- f3
    f2Result <- f4
    f2Result <- f5
  } yield ({
    println("All calls done")
    system.shutdown()
    system.awaitTermination()
  }
    )
}