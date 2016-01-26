package demo

import akka.actor.ActorSystem
import akka.http.Http
import akka.http.model.HttpMethods._
import akka.http.model._
import akka.stream.FlowMaterializer

import akka.stream.scaladsl.Flow
import demo.Util._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * @author Miguel Reyes
  *         Date: 1/22/16
  *         Time: 3:46 PM
  */
/**
  * Simple Object that starts an HTTP server using akka-http. All requests are handled
  * through an Akka flow.
  */
object Boot extends App {

  // the actor system to use. Required for flowmaterializer and HTTP.
  // passed in implicit
  implicit val system = ActorSystem("Streams")
  implicit val materializer = FlowMaterializer()

  // start the server on the specified interface and port.
  val serverBinding2 = Http().bind(interface = "localhost", port = 8091)
  serverBinding2.connections.foreach { connection =>
    connection.handleWith(Flow[HttpRequest].mapAsync(asyncHandler))
  }

  // With an async handler, we use futures. Threads aren't blocked.
  def asyncHandler(request: HttpRequest): Future[HttpResponse] = {

    // we match the request, and some simple path checking
    request match {

      // match specific path. Returns all the avaiable tickers
      case HttpRequest(GET, Uri.Path("/getAllTickers"), _, _, _) => {

        // make a db call, which returns a future.
        // use for comprehension to flatmap this into
        // a Future[HttpResponse]
        for {
          input <- Database.findAllTickers
        } yield {
          HttpResponse(entity = convertToString(input))
        }
      }

      // match GET pat. Return a single ticker
      case HttpRequest(GET, Uri.Path("/get"), _, _, _) => {

        // next we match on the query paramter
        request.uri.query.get("ticker") match {

          // if we find the query parameter
          case Some(queryParameter) => {

            // query the database
            val ticker = Database.findTicker(queryParameter.toString)

            // use a simple for comprehension, to make
            // working with futures easier.
            for {
              t <- ticker
            } yield {
              t match {
                case Some(bson) => HttpResponse(entity = convertToString(bson))
                case None => HttpResponse(status = StatusCodes.OK)
              }
            }
          }

          // if the query parameter isn't there
          case None => Future(HttpResponse(status = StatusCodes.OK))
        }
      }

      // Simple case that matches everything, just return a not found
      case HttpRequest(_, _, _, _, _) => {
        Future[HttpResponse] {
          HttpResponse(status = StatusCodes.NotFound)
        }
      }
    }
  }


}


