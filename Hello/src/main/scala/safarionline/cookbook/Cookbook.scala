package safarionline.cookbook

/**
  * Use "private" to force the use of the Factory method "make".
  * @param ingredients Ingredients list
  * @param directions Directions list
  */
case class Recipe(ingredients: List[String],
                  directions: List[String]) {
}

object Cookbook {
  val pbj = Recipe(
    List("Peanut butter", "Bread", "Jelly"),
    List("Add peanut butter to bread"))
  val bananaPancakes = Recipe(
    List("Flour mix", "Banana"),
    List("Mix ingredients and bake"))

}
