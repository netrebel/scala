package akka

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.RunnableGraph

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * @author Miguel Reyes
  *         Date: 11/23/15
  *         Time: 5:23 PM
  */
abstract class Setup(name: String) {
  self: App =>
  implicit val sys = ActorSystem(name)
  implicit val mat = ActorMaterializer()(sys)
  implicit val ec = sys.dispatcher
  implicit val sch = sys.scheduler


  Future.successful(()).
    flatMap(_ => demo().run() match {
      case f: Future[_] => f
      case o => Future.successful(o)
    }) andThen {
    case Failure(t) => t.printStackTrace()
    case Success(r) => println("Result : " + r)
  } onComplete {
    case _ => sys.shutdown()
  }

  def demo(): RunnableGraph[_]

}
