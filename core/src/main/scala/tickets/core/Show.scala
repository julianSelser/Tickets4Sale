package tickets.core

import java.time.LocalDate

import tickets.core.Status._

case class Show(title: String, openingDay: LocalDate, genre: Genres.Value, id: Option[Long] = None, ticketsSoldForDay: Option[Int] = None) {

  def availability(queryDate: LocalDate, showDate: LocalDate): ShowAvailability = {

    val isInSmallHall = isInTheSmallHall(showDate)
    val diff = showDate.toEpochDay - queryDate.toEpochDay
    val availability = ShowAvailability(title, _: Int, _: Int, _: String, id)

    if (diff < 0)
      availability(0, 0, inThePast)
    else if (diff <= 5 && diff >= 0)
      availability(0, 0, soldOut)
    else if (25 < diff)
      availability(if (isInSmallHall) 100 else 200, 0, saleNotStarted)
    else if (25 >= diff && diff > 5) {
      val ticketsPerDay = if(isInSmallHall) 5 else 10
      val ticketsLeft = (ticketsPerDay * (diff - 5)) - ticketsSoldForDay.getOrElse(0)
      val ticketsAvailable = ticketsPerDay - ticketsSoldForDay.getOrElse(0)

      availability(ticketsLeft.toInt, ticketsAvailable, openForSale)
    }
    else
      throw new RuntimeException("This should never happen, if is exhaustive")
  }

  def availabilityWithPrice(queryDate: LocalDate, showDate: LocalDate): ShowAvailability = {
    val price = priceFor(showDate)

    availability(queryDate, showDate).copy(price = Some(price))
  }

  def priceFor(showDate: LocalDate): Int = {
    val diff = datesDiff(showDate, openingDay)

    // since diff to opening day is 0
    // a diff of 80 is actually day 81
    if (diff >= 80 && diff < 100)
      (genre.price * 0.8).toInt
    else
      genre.price
  }

  private def datesDiff(date1: LocalDate, date2: LocalDate): Long = date1.toEpochDay - date2.toEpochDay

  private def isInTheSmallHall(showDate: LocalDate): Boolean = {
    val diff = datesDiff(showDate, openingDay)

    diff >= 60 && diff < 100
  }
}
