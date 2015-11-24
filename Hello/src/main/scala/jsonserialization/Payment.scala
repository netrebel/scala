package jsonserialization

/**
  * @author Miguel Reyes
  *         Date: 11/18/15
  *         Time: 9:04 PM
  */
case class Payment(id: Long,
                   pType: String,
                   address: Address,
                   token: String,
                   cvv: String)

object Payment {

  import play.api.libs.json._

  def writePayment(payment: Payment) = {
    JsObject(Seq(
      "id" -> JsNumber(payment.id),
      "type" -> JsString(payment.pType),
      "address" -> Json.toJson(payment.address),
      "token" -> JsString(payment.token),
      "cvv" -> JsString(payment.cvv)
    ))
  }

  def readPayment(jsonPayment: JsValue) = {
    val id = (jsonPayment \ "id").as[Long]
    val pType = (jsonPayment \ "type").as[String]
    val address = (jsonPayment \ "address").as[Address]
    val token = (jsonPayment \ "token").as[String]
    val cvv = (jsonPayment \ "cvv").as[String]
    Payment(id, pType, address, token, cvv)
  }
}