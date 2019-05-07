package tickets.core.parser

import java.time.LocalDate

import com.github.tototoshi.csv.CSVReader
import tickets.core.{Genres, Show}

import scala.io.Source

object ShowParser {
  def parse(source: Source): List[Show] = {
    CSVReader
      .open(source)
      .iteratorWithHeaders
      .map(_.map(_ match { case (header, value) => (header.trim, value.trim)}))
      .map(row => {
        Show(row("title"), LocalDate.parse(row("opening_day")), Genres.withName(row("genre")))
      })
      .toList
  }
}
