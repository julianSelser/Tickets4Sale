package tickets.core

import java.time.LocalDate.parse

import Status._
import org.scalatest.{FlatSpec, Matchers}
import tickets.core.Genres.{comedy, drama, musical}

class ShowSpec extends FlatSpec with Matchers {
  val aDrama = Show("Everyman", parse("2018-08-07"), drama)

  "a Show" should "know its price for a show date" in {
    val showDate = aDrama.openingDay

    aDrama.priceFor(showDate) shouldBe 40
    aDrama.priceFor(showDate.plusDays(80)) shouldBe 32
  }

  it should "be sold out 5 days before a performance" in {
    //As per documentation, strictly 5 days before, the show is sold out
    //Also, Ill assume the opening day is the first performance, and its also sold out on that day
    val _5DaysBefore = parse("2018-08-02")
    val availability = aDrama.availability(_5DaysBefore, aDrama.openingDay)

    availability should have (
      'status (soldOut), 'ticketsLeft (0), 'ticketsAvailable (0))
  }

  it should "tell us if the performance is in the past" in {
    val availability = aDrama.availability(parse("2020-09-01"), aDrama.openingDay)

    availability should have (
      'status (inThePast), 'ticketsLeft (0), 'ticketsAvailable (0))
  }

  it should "tell us if the sale hasn't started" in {
    val _26daysBefore = aDrama.openingDay.minusDays(26)

    val availability = aDrama.availability(_26daysBefore, aDrama.openingDay)

    availability should have (
      'status (saleNotStarted), 'ticketsLeft (200), 'ticketsAvailable (0))
  }

  it should "tell us its availability on the first and second day we can sell tickets" in {
    val _25daysBefore = aDrama.openingDay.minusDays(25)
    val _24daysBefore = aDrama.openingDay.minusDays(24)

    val avail1stDay = aDrama.availability(_25daysBefore, aDrama.openingDay)
    val avail2ndDay= aDrama.availability(_24daysBefore, aDrama.openingDay)

    avail1stDay should have (
      'status (openForSale), 'ticketsLeft (200), 'ticketsAvailable (10))

    avail2ndDay should have (
      'status (openForSale), 'ticketsLeft (190), 'ticketsAvailable (10))
  }

  it should "be able to recieve an ammount of sold tickets for the day" in {
    val aDramaWithSomeSoldTickets = aDrama.copy(ticketsSoldForDay =  Some(4))
    val _25daysBefore = aDramaWithSomeSoldTickets.openingDay.minusDays(25)

    val availability = aDramaWithSomeSoldTickets.availability(_25daysBefore, aDrama.openingDay)

    availability should have (
      'status (openForSale), 'ticketsLeft (196), 'ticketsAvailable (6))
  }

  it should "tell us if the price is discounted for the small hall in the 81th performance of the show" in {
    val _81daysAfterStart = aDrama.openingDay.plusDays(80)
    val _6daysBefore81thShow = _81daysAfterStart.minusDays(6)

    val availability = aDrama.availabilityWithPrice(_6daysBefore81thShow, _81daysAfterStart)

    availability should have (
      'status (openForSale), 'ticketsLeft (5), 'ticketsAvailable (5), 'price (Some(32)))
  }
}