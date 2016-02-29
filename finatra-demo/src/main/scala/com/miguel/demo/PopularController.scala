package com.miguel.demo

import com.google.inject.Inject
import com.twitter.finatra.http.Controller
import org.joda.time.DateTime

/**
  * Created by miguel.reyes on 2/29/16.
  */
class PopularController @Inject()(usersRepository: UsersRepository,
                                  searchService: SearchService,
                                  engagementService: EngagementService) extends Controller {
  post("/api/popular") {
    request: PopularPostRequest =>
      for {
        user <- usersRepository.getByApiKey(request.apiKey)
        tweets <- searchService.findTweets(user, request)
        engagements <- engagementService.lookup(user, tweets)
      } yield {
        ApiResponse.create(request, tweets, engagements)
      }
  }
}

class UsersRepository {
  def getByApiKey(apiKey: String): User = new User("miguel", apiKey)
}

class SearchService {
  def findTweets(user: User, request: PopularPostRequest) : List[Tweets]= ???
}

class EngagementService {
  def lookup(user: User, tweets: List[Tweets]) = ???
}

case class PopularPostRequest(val apiKey: String)

class User(name: String, apiKey: String)

case class Tweets(tweet: String, date: DateTime)

object ApiResponse {
  def create(request: PopularPostRequest, tweets: List[Tweets], engagements: Any) = ???
}
