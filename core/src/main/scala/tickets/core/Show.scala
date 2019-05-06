package tickets.core

case class Show(title: String, openingDay: String, genre: String, id: Option[Long] = None) {
  def availability(queryDate: String, showDate: String, ticketsSoldForDay: Option[Int] = None): ShowAvailability = ???
  def availabilityWithPrice(queryDate: String, showDate: String, ticketsSoldForDay: Option[Int] = None): ShowAvailability  = ???
}
