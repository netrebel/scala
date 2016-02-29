package com.miguel.demo

import com.google.inject.testing.fieldbinder.Bind
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.integration.tweetexample.main.services.admin.DatabaseClient
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.finatra.httpclient.HttpClient
import com.twitter.finatra.request.Header
import com.twitter.finatra.validation.{NotEmpty, PastTime}
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import org.joda.time.DateTime

/**
  * Created by miguel.reyes on 2/29/16.
  */
class PopularApiFeatureTest extends FeatureTest with Mockito {
  val server: EmbeddedHttpServer = new EmbeddedHttpServer(new PopularApiServer)

  //Bind Mocks
  @Bind val databaseClient = mock[DatabaseClient]
  @Bind @SearchClient val searchClient = mock[HttpClient]
  @Bind @EngagementsClient val engagementsClient = mock[HttpClient]

  "Handle POST request" in {

    server.httpPost(
      path = "/api/popular",
      headers = Map("api_key" -> "secret123"),
      postBody = """{ "start" : "2015-09-01", ... }""",
      andExpect = Status.Ok,
      withJsonBody =
        """
          {
            "start" : "2015-09-01T00:00:00Z",
            "end" : "2015-09-01T00:00:00Z",
            "tweets" : [...]
          }
        """)
    /*//Setup mocks
    databaseClient.executeSql() returns Future(SqlResponse(...))
    searchClient.execute() returns Future(SearchResponse(...))
    server.httpPost(...)*/

  }

}

case class PopularPostRequest(@PastTime start: DateTime,
                              @PastTime end: DateTime,
                              @Header @NotEmpty api_key: String)

case class ApiResponse(
                      start: DateTime, end: DateTime, tweets: Seq[TweetResponse]
                      )

case class TweetResponse(id: TweetId, message: String, counts: Map[EngagementType, Long])

class TweetId

class EngagementType

class EngagementsClient

class SearchClient