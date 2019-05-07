package tickets.core

import java.time.LocalDate.parse

import org.scalatest.{FlatSpec, Matchers}
import tickets.core.Genres.{comedy, drama, musical}
import tickets.core.Status._

class InventorySpec extends FlatSpec with Matchers {
  "Inventory" should "be built exactly as the first example of the given excercise" in {
    val queryDate = parse("2018-01-01")
    val showDate = parse("2018-07-01")

    val shows = List(
      Show("Cats", parse("2018-06-01"), musical),
      Show("Comedy of Errors", parse("2018-07-01"), comedy),
      Show("Everyman", parse("2018-08-01"), drama))

    Inventory.of(shows, queryDate, showDate) shouldBe
      Inventory(List(
        Genre(comedy, List(ShowAvailability("Comedy of Errors", 200, 0, saleNotStarted))),
        Genre(musical, List(ShowAvailability("Cats", 200, 0, saleNotStarted))))
      )
  }

  it should "be built exactly as the second example of the given excercise" in {
    val queryDate = parse("2018-08-01")
    val showDate = parse("2018-08-15")

    val shows = List(
      Show("Cats", parse("2018-06-01"), musical),
      Show("Comedy of Errors", parse("2018-07-01"), comedy),
      Show("Everyman", parse("2018-08-01"), drama))

    Inventory.of(shows, queryDate, showDate) shouldBe
      Inventory(List(
        Genre(drama, List(ShowAvailability("Everyman", 100, 10, openForSale))),
        Genre(comedy, List(ShowAvailability("Comedy of Errors", 100, 10, openForSale))),
        Genre(musical, List(ShowAvailability("Cats", 50, 5, openForSale)))))
  }

  it should "be able to build it with prices" in {
    val queryDate = parse("2018-08-01")
    val showDate = parse("2018-08-15")

    val shows = List(
      Show("Cats", parse("2018-06-01"), musical),
      Show("Comedy of Errors", parse("2018-07-01"), comedy),
      Show("Everyman", parse("2018-08-01"), drama))

    Inventory.withPrices(shows, queryDate, showDate) shouldBe
      Inventory(List(
        Genre(drama, List(ShowAvailability("Everyman", 100, 10, openForSale, price = Some(40)))),
        Genre(comedy, List(ShowAvailability("Comedy of Errors", 100, 10, openForSale, price = Some(50)))),
        Genre(musical, List(ShowAvailability("Cats", 50, 5, openForSale, price = Some(70))))))
  }
}
