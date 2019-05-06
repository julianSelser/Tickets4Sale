package tickets.core

import java.time.LocalDate.parse

import Status._
import org.scalatest.{FlatSpec, Matchers}
import tickets.core.Genres.{comedy, drama, musical}

class ShowSpec extends FlatSpec with Matchers {
  val showDate = parse("2019-01-15")
  val aDrama = Show("Everyman", parse("2019-01-01"), drama)

  "a Show" should "know its price for a show date" in {
    aDrama.priceFor(aDrama.openingDay) shouldBe 40
    aDrama.priceFor(aDrama.openingDay.plusDays(80)) shouldBe 32
  }

  it should "be sold out 5 days before a performance" in {
    val _5DaysBefore = parse("2019-01-11")
    val availability = aDrama.availability(_5DaysBefore, showDate)

    availability should have (
      'status (soldOut), 'ticketsLeft (0), 'ticketsAvailable (0))
  }

  it should "tell us if the performance is in the past" in {
    val availability = aDrama.availability(parse("2222-09-01"), aDrama.openingDay)

    availability should have (
      'status (inThePast), 'ticketsLeft (0), 'ticketsAvailable (0))
  }

  it should "tell us if the sale hasn't started" in {
    val _26daysBefore = parse("2018-12-21")

    val availability = aDrama.availability(_26daysBefore, showDate)

    availability should have (
      'status (saleNotStarted), 'ticketsLeft (200), 'ticketsAvailable (0))
  }

  it should "tell us its availability on the first and second day we can sell tickets" in {
    val firstDayOfSales = parse("2018-12-22")
    val secondDayOfSales = parse("2018-12-23")

    val avail1stDay = aDrama.availability(firstDayOfSales, showDate)
    val avail2ndDay= aDrama.availability(secondDayOfSales, showDate)

    avail1stDay should have (
      'status (openForSale), 'ticketsLeft (200), 'ticketsAvailable (10))

    avail2ndDay should have (
      'status (openForSale), 'ticketsLeft (190), 'ticketsAvailable (10))
  }

  it should "be able to recieve an ammount of sold tickets for the day" in {
    val firstSalesDate = parse("2018-12-22")
    val aDramaWithSomeSoldTickets = aDrama.copy(ticketsSoldForDay =  Some(4))

    val availability = aDramaWithSomeSoldTickets.availability(firstSalesDate, showDate)

    availability should have (
      'status (openForSale), 'ticketsLeft (196), 'ticketsAvailable (6))
  }

  it should "tell us if the price is discounted for the small hall in the 81th performance of the show" in {
    val _81daysAfterStart = aDrama.openingDay.plusDays(80)
    val _6daysBefore81thShow = _81daysAfterStart.minusDays(5)

    val availability = aDrama.availabilityWithPrice(_6daysBefore81thShow, _81daysAfterStart)

    availability should have (
      'status (openForSale), 'ticketsLeft (5), 'ticketsAvailable (5), 'price (Some(32)))
  }
}