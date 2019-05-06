package tickets.core

import java.time.LocalDate.parse

import org.scalatest.{FlatSpec, Matchers}
import tickets.core.Genres.{comedy, drama, musical}

class ShowSpec extends FlatSpec with Matchers {
  val aMusical = Show("Cats", parse("2018-06-01"), musical)
  val aComedy = Show("Comedy of errors", parse("2018-07-01"), comedy)
  val aDrama = Show("Everyman", parse("2018-08-01"), drama)

  "Show" should "know its price for a query and show date" in {
    val showDate = aDrama.openingDay

    aDrama.priceFor(showDate) shouldBe 40
    aDrama.priceFor(showDate.plusDays(80)) shouldBe 32
  }
}