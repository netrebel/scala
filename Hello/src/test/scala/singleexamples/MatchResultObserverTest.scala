package singleexamples

import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

class MatchResultObserverTest extends FlatSpec with MockFactory {

  val winner = Player(222, "boris", Countries.Russia)
  val loser = Player(333, "hans", Countries.Germany)

  "MatchResultObserver" should "update CountryLeaderBoard after finished match" in {
    val countryLeaderBoardMock = mock[CountryLeaderboard]
    val userDetailsServiceStub = stub[PlayerDatabase]

    // set expectations for "mock"
    (countryLeaderBoardMock.addVictoryForCountry _).expects(Countries.Russia)

    // configure "stubs"
    (userDetailsServiceStub.getPlayerById _).when(winner.id).returns(winner)
    (userDetailsServiceStub.getPlayerById _).when(loser.id).returns(loser)

    // run system under test
    val matchResultObserver = new MatchResultObserver(userDetailsServiceStub, countryLeaderBoardMock)
    matchResultObserver.recordMatchResult(MatchResult(winner = winner.id, loser = loser.id))
  }
}