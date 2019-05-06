package tickets.cli

import java.time.LocalDate

import com.github.tototoshi.csv.CSVReader
import tickets.core.{Genres, Show}

import scala.io.Source

object ShowParser {
  def parse(source: Source): List[Show] = {
    CSVReader
      .open(source)
      .iteratorWithHeaders
      .map(row => {
        val clean = row.map(_ match { case (header, value) => (header.trim, value.trim)})

        Show(clean("title"), LocalDate.parse(clean("opening_day")), Genres.withName(clean("genre")))
      }).toList
  }
}
