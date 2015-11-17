package singleexamples

import org.scalatest.FunSuite
import singleexamples.Friends.{Dog, Human}
import singleexamples.Friends.seekHelp

/**
  * @author Miguel Reyes
  *         Date: 11/17/15
  *         Time: 1:32 PM
  */
class FriendsTest extends FunSuite {

  test("testSeekHelp") {

    val robert = new Human("Robert")
    assert(robert.listen() == "I am Robert your friend")
    assert(seekHelp(robert) ==  "Robert seek help")

    val dog = new Dog("Bobby")
    assert(dog.listen() == "I am Bobby your friend")
    assert(seekHelp(dog) == "Bobby seek help")
  }

}
