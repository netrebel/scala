package singleexamples

object Friends {

  trait Friend {
    def name: String

    def listen() = "I am " + name + " your friend"

    def help() = name + " seek help"
  }


  class Human(val name: String) extends Friend {

  }

  class Animal(val name: String) {

  }

  class Dog(override val name: String) extends Animal(name) with Friend {

  }

  def seekHelp(friend: Friend) = friend.help()

}


