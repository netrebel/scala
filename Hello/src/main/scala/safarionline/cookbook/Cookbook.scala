package safarionline.cookbook

/**
  * Use "private" to force the use of the Factory method "make".
  * @param ingredients Ingredients list
  * @param directions Directions list
  */
case class Recipe(ingredients: Map[String, Mass],
                  directions: List[String]) {

  def shoppingList(kitchen: Map[String, Mass]): List[String] =
    for {
      (name, need) <- ingredients.toList
      have = kitchen.getOrElse(name, Grams(0))
      //Guard: Is the amount that we have less that what we need
      if have < need
    } yield name
}

trait Measured {

  def amount: Double

  def symbol: String

  override def toString: String = amount + symbol

}

/**
  * Use "sealed" says that no other classes outside the file are allowed to extend Mass
  * Also known as Algebraic data type.
  * By using abstract "defs" we can overrider them with whatever we want to in the implementations.
  */
sealed abstract class Mass() extends Ordered[Mass] with Measured {

  def toGrams: Grams

  def compare(that: Mass): Int = (this.toGrams.amount - that.toGrams.amount).toInt
}

case class Grams(amount: Double) extends Mass {
  override def toGrams = this

  override def symbol: String = "g"
}

case class Milligrams(amount: Double) extends Mass {
  override def toGrams = Grams(this.amount / 1000)

  override def symbol: String = "mg"
}

case class Kilograms(amount: Double) extends Mass {
  override def toGrams = Grams(this.amount * 1000)

  override def symbol: String = "kg"
}


object Cookbook {
  val pbj = Recipe(
    Map("peanut butter" -> new Grams(50),
      "bread" -> new Grams(250),
      "jelly" -> new Kilograms(0.5)),
    List("Add peanut butter to bread"))
  val bananaPancakes = Recipe(
    Map("flour mix" -> new Kilograms(0.5),
      "banana" -> new Grams(500)),
    List("Mix ingredients and bake"))

}
