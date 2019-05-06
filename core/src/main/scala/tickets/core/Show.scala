package tickets.core

import java.time.LocalDate

case class Show(title: String, openingDay: LocalDate, genre: Genres.Value, id: Option[Long] = None) {
  def priceFor(showDate: LocalDate): Int = {
    val datesDiff = showDate.toEpochDay - openingDay.toEpochDay

    // since diff to opening day is 0
    // a diff of 80 is actually day 81
    if(datesDiff >= 80 && datesDiff < 100)
      (genre.price * 0.8).toInt
    else
      genre.price
  }

  def availability(queryDate: String, showDate: String, ticketsSoldForDay: Option[Int] = None): ShowAvailability = ???
  def availabilityWithPrice(queryDate: String, showDate: String, ticketsSoldForDay: Option[Int] = None): ShowAvailability  = ???
}
