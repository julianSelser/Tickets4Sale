package tickets.core

import java.time.LocalDate

case class Inventory(genres: Genre*)

object Inventory {
  def of(shows: List[Show], queryDate: LocalDate, showDate: LocalDate): Inventory =
    of(shows, queryDate, showDate, _.availability(queryDate, showDate))

  def withPrice(shows: List[Show], queryDate: LocalDate, showDate: LocalDate): Inventory =
    of(shows, queryDate, showDate, _.availabilityWithPrice(queryDate, showDate))

  private def of(shows: List[Show],
                 queryDate: LocalDate,
                 showDate: LocalDate,
                 showTransform: Show => ShowAvailability): Inventory = {

    val built = shows
      .filter(show => show.openingDay.isBefore(showDate) || show.openingDay.isEqual(showDate))
      .groupBy(_.genre)
      .map(_ match {
        case (genre, showsByGenre) => Genre(genre, showsByGenre.map(showTransform))
      })

    Inventory(built.toSeq:_*)
  }
}
