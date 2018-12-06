package com.github.xiaodongw.swagger.finatra

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import io.swagger.models.auth.BasicAuthDefinition
import io.swagger.models.{Contact, Info, Swagger}
import io.swagger.util.Json

object SampleSwagger extends Swagger {
  Json.mapper().setPropertyNamingStrategy(new PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy)
}

object SampleApp extends HttpServer {
  val info = new Info()
    .description("The Student / Course management API, this is a sample for swagger document generation")
    .version("1.0.1")
    .title("Student / Course Management API")
    .contact(new Contact().name("Miguel").email("miguel.reyes@enernoc.com").url("www.google.com"))
  SampleSwagger
    .info(info)
    .addSecurityDefinition("sampleBasic", {
      val d = new BasicAuthDefinition()
      d.setType("basic")
      d
    })


  override def configureHttp(router: HttpRouter) {
    router
      .filter[CommonFilters]
      .add(new SwaggerController(swagger = SampleSwagger))
      .add[SampleController]
  }
}
