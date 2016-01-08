package singleexamples

/**
  * @author Miguel Reyes
  *         Date: 1/6/16
  *         Time: 12:10 PM
  */
abstract class Entity(id: Int, name: String) {
  def entityId = id

  def entityName = name
}

trait ExportToJson {
  def exportToJson(entity: Entity): Unit = {
    println(s"{ id : ${entity.entityName}, name : ${entity.entityName} }")
  }
}

trait ExportToXML {
  def exportToXml(entity: Entity): Unit = {
    println(s"<xml> <id> ${entity.entityName} </id> <name> ${entity.entityName} </name> </xml>")
  }
}

case class User(id: Int, name: String) extends Entity(id, name) with ExportToJson with ExportToXML

case class Admin(id: Int, name: String) extends Entity(id, name)

object Entity extends App {
  val user = User(1, "Miguel")
  user.exportToJson(user)
  user.exportToXml(user)

  val admin = Admin(2, "Admin")

  def printType(entity: Entity) {
    entity match {
      case User(id, name) => println(s"I'm a User with id: $id and name: $name ")
      case Admin(id, name) => println(s"I'm an Admin with id: $id and name: $name ")
      case _ => println("Not found")
    }
  }

  printType(user)
  printType(admin)
  printType(new Entity(3, "hello") {})
}


