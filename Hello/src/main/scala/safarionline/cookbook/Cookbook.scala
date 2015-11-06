package safarionline.cookbook

/**
  * Uses a companion "object" for Recipe.
  *
  * @author Miguel Reyes
  *         Date: 11/6/15
  *         Time: 11:20 AM
  */
class Cookbook(val recipes: Map[String, Recipe]) {
  println(recipes)

  //Create additional constructor. It needs to call the class constructor
  def this() = this(Map.empty)
}


/**
  * Use "private" to force the use of the Factory method "make".
  * @param ingredients Ingredients list
  * @param directions Directions list
  */
class Recipe(val ingredients: List[String],
              val directions: List[String]) {
}


/**
  * Create a singleton with a constructor method
  */
object Recipe {
  def apply(ingredients: List[String] = List.empty,
            directions: List[String] = List.empty): Recipe = {
    new Recipe(ingredients, directions)
  }

  def unapply(recipe: Recipe): Option[(List[String], List[String])] = {
    Some((recipe.ingredients, recipe.directions))
  }

}

object Cookbook {
  val pbj = Recipe(List("Peanut butter sandwich"), List("Add peanut butter to bread"))
  val pancakes = Recipe(List("Pancakes"), List("Mix ingredients and bake"))

}
