package singleexamples

//type PlayerId = Int
case class Player(id: Int, nickname: String, country: Country)

case class MatchResult(winner: Int, loser: Int)

case class CountryLeaderBoardEntry(country: Country, points: Int)

trait CountryLeaderboard {
  def addVictoryForCountry(country: Country): Unit

  def getTopCountries: List[CountryLeaderBoardEntry]
}

trait PlayerDatabase {
  def getPlayerById(playerId: Int): Player
}


case class Country(name: String) {
  def getName: String = name
}

object Countries {
  val Russia: Country = Country("Russia")
  val Germany: Country = Country("Germany")
}


class MatchResultObserver(playerDatabase: PlayerDatabase, countryLeaderBoard: CountryLeaderboard) {
  def recordMatchResult(result: MatchResult): Unit = {
    val player = playerDatabase.getPlayerById(result.winner)
    countryLeaderBoard.addVictoryForCountry(player.country)
  }
}

