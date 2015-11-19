import org.scalatest.{Matchers, FlatSpec}
import play.api.libs.json._
import Payment._

/**
  * @author Miguel Reyes
  *         Date: 11/18/15
  *         Time: 9:12 PM
  */
class PaymentTest extends FlatSpec with Matchers {

  val address = Address("1375 Burlingame Ave.", None, "Burlingame", "California", "94010")


  "Payment " should "be converted to JSON correctly " in {
    val payment = Payment(1, "creditCard", address, "wdweadowei3209423", "123")
    val paymentJSON = writePayment(payment)
    (paymentJSON \ "id").get should be(JsNumber(1))
    (paymentJSON \ "type").get should be(JsString("creditCard"))
    (paymentJSON \ "address").get should be(Json.toJson(payment.address))
    (paymentJSON \ "token").get should be(JsString("wdweadowei3209423"))
    (paymentJSON \ "cvv").get should be(JsString("123"))
  }

  it should " be deserialized correctly " in {
      val paymentJSON: JsValue = JsObject(Seq(
        "id" -> JsNumber(1),
        "type" -> JsString("creditCard"),
        "address" -> Json.toJson(address),
        "token" -> JsString("wdweadowei3209423"),
        "cvv" -> JsString("123")
      ))
      val payment = readPayment(paymentJSON)
      payment.id should be (1)
      payment.pType should be ("creditCard")
      payment.address should be (address)
      payment.token should be ("wdweadowei3209423")
      payment.cvv should be ("123")
    }


}
