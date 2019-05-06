package tickets.core.format


import spray.json._, DefaultJsonProtocol._
import tickets.core.{Genre, Genres, Inventory, ShowAvailability}

trait InventoryJsonFormat {
  implicit val genresFormat = new JsonFormat[Genres.Value] {
    override def write(genre: Genres.Value): JsValue = JsString(genre.toString)
    override def read(json: JsValue): Genres.Value = ???
  }

  implicit val showFormat = jsonFormat6(ShowAvailability)
  implicit val genreFormat = jsonFormat2(Genre)
  implicit val inventoryFormat = jsonFormat1(Inventory.apply)
}
