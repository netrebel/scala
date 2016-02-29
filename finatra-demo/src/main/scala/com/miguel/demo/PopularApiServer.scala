package com.miguel.demo

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, TraceIdMDCFilter, LoggingMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter

/**
  * Created by miguel.reyes on 2/29/16.
  */
class PopularApiServer extends HttpServer {

  override val modules = Seq(
    UserRepositoryModule,
    SearchServiceModule,
    EngagementServiceModule
  )

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[PopularController]
  }
}

object SearchServiceModule

object UserRepositoryModule

object EngagementServiceModule


