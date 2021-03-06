package tickets.core

import java.time.LocalDate

import tickets.core.Status._

case class Show(title: String, openingDay: LocalDate, genre: Genres.Value, id: Option[Long] = None, ticketsSoldForDay: Option[Int] = None) {
  val INTERVAL_CORRECTION = 5; val A_DAY = 1

  def availability(queryDate: LocalDate, showDate: LocalDate): ShowAvailability = {

    val isInSmallHall = isInTheSmallHall(showDate)
    val diff = showDate.toEpochDay - queryDate.toEpochDay
    val availability = ShowAvailability(title, _: Int, _: Int, _: String, id)

    if (diff < 0 || showDate.isAfter(openingDay.plusDays(99)))
      availability(0, 0, inThePast)
    else if (5 > diff && diff >= 0)
      availability(0, 0, soldOut)
    else if (25 <= diff)
      availability(if (isInSmallHall) 100 else 200, 0, saleNotStarted)
    else if (25 > diff && diff >= 5) {
      // tickets left is a function like f(x) = tickets*x where tickets {10 if bighall, 5 if smallhall} and x in [20; 1]
      // since sale is between days 24 to 5 we have an INTERVAL_CORRECTION(5) to have it between 19 and 0
      // also we correct by adding A_DAY(1) to have the actual correct interval of [20; 1]
      val ticketsPerDay = if (isInSmallHall) 5 else 10
      val ticketsLeft = (ticketsPerDay * (diff - INTERVAL_CORRECTION + A_DAY)) - ticketsSoldForDay.getOrElse(0)
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
