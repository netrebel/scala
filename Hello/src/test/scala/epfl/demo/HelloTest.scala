package epfl.demo

import org.scalatest.FunSuite

/**
 * @author Miguel Reyes
 *         Date: 10/22/15
 *         Time: 3:37 PM
 */
class HelloTest extends FunSuite {

  test("Say Hello Test works correctly") {
    val hello = new Hello
    assert(hello.sayHello("Scala") == "Hello, Scala!")
  }

}
