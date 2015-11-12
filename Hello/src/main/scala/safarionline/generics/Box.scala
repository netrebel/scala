package safarionline.generics

/**
  * @author Miguel Reyes
  *         Date: 11/10/15
  *         Time: 3:39 PM
  */
case class Box[A](private var contents: A)

object Box {

  def put[A](contents: A, box: Box[A]): Unit = box.contents = contents

  def get[A](box: Box[A]): A = box.contents

}
