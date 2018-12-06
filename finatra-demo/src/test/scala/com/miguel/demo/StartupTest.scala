class StartupTest extends Test {
  val server = new EmbeddedHttpServer (
    stage = PRODUCTION,
    twitterServer = new PopularApiServer,
    clientFlags = Map(
      "com.twitter.server.resolverMap" -> "firebase=nil!"
    )
  )
  "server" in {
    server.assertHealthy()
  }
}
