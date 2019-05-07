package tickets.core.format

import java.time.LocalDate

import spray.json._
import DefaultJsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import tickets.core._

trait InventoryJsonFormat extends SprayJsonSupport {
  implicit val localDateFormat = new JsonFormat[LocalDate] {
    override def write(obj: LocalDate): JsValue = JsString(obj.toString)
    override def read(json: JsValue): LocalDate = json match {
      case JsString(v) => LocalDate.parse(v); case _ => throw new RuntimeException("D:")
    }
  }

  implicit val genresFormat = new JsonFormat[Genres.Value] {
    override def write(genre: Genres.Value): JsValue = JsString(genre.toString)
    override def read(json: JsValue): Genres.Value = ???
  }

  implicit val showFormat = jsonFormat6(ShowAvailability)
  implicit val genreFormat = jsonFormat2(Genre)
  implicit val inventoryFormat = jsonFormat1(Inventory.apply)
}
