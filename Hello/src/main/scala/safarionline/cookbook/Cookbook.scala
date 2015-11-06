package safarionline.cookbook

/**
  * @author Miguel Reyes
  *         Date: 11/6/15
  *         Time: 11:20 AM
  */
class Cookbook(val recipes: Map[String, Recipe]) {
  println(recipes)

  //Create additional constructor. It needs to call the class constructor
  def this() = this(Map.empty)
}

class Recipe(ingredients: List[String] = List.empty,
                  directions: List[String] = List.empty) {
  println(ingredients)
  println(directions)
}
