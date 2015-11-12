package safarionline.generics

/**
  * @author Miguel Reyes
  *         Date: 11/10/15
  *         Time: 8:37 PM
  */
case class PetBox[A](private var contents: A)

class Dog {override val toString = "Dog"}

class Puppy extends Dog {override val toString = "Puppy"}

object PetBox {

  /**
    * Lower bound: A is a super type of Puppy.
    * Puppy is the narrowest thing that can appear in the box
    *
    * @param box PetBox
    */
  def putPuppy[A >: Puppy](box : PetBox[A]) : Unit = box.contents = new Puppy

  /**
    * Upper bound: A is a subtype of Dog.
    * Anything in the box must be more specific than dog.
    * Dog is the widest thing that can appear in the box.
    *
    * @param box PetBox
    * @return Dog
    */
  def getDog[A <: Dog](box : PetBox[A]) : A = box.contents

  val puppyBox = PetBox(new Puppy)
  val dogBox = PetBox(new Dog)

}
