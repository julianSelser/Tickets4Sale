import java.time.LocalDate.parse

import org.scalatest.{FlatSpec, Matchers}
import tickets.cli.ShowParser
import tickets.core.Genres.{comedy, drama, musical}
import tickets.core.Show

import scala.io.Source

class ShowParserSpec extends FlatSpec with Matchers {
  "Show parser" should "read csv a parse it into a show list" in {
    val shows = ShowParser.parse(Source.fromResource("input.csv"))

    shows shouldBe List(
      Show("Cats", parse("2018-06-01"), musical),
      Show("Comedy of Errors", parse("2018-07-01"), comedy),
      Show("Everyman", parse("2018-08-01"), drama))
  }
}
