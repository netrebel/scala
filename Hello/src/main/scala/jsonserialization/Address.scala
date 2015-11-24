package jsonserialization

/**
  * @author Miguel Reyes
  *         Date: 11/18/15
  *         Time: 9:00 PM
  */
case class Address(address1: String,
                   address2: Option[String],
                   city: String,
                   state: String,
                   zipcode: String)

object Address {

  import play.api.libs.json._

  implicit val addressFormats = Json.format[Address]

  def writeAddress(address: Address) = {
    Json.toJson(address)
  }

  def readAddress(jsonAddress: JsValue) = {
    jsonAddress.as[Address]
  }

}