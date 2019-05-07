package tickets.core.parser

import java.time.LocalDate

import com.github.tototoshi.csv.CSVReader
import tickets.core.{Genres, Show}

import scala.io.Source

object ShowParser {
  def parse(source: Source): List[Show] = parseWithIndex(source).map(_.copy(id = None))

  def parseWithIndex(source: Source) = parse(source, {
    case (row, i) =>
      val show = Show(row("title"), LocalDate.parse(row("opening_day")), Genres.withName(row("genre")))

      show.copy(id = Some(i + 1))
  })

  private def parse(source: Source, toShow: ((Map[String, String], Int)) => Show): List[Show] = {
    CSVReader
      .open(source)
      .iteratorWithHeaders
      .map(_.map(_ match { case (header, value) => (header.trim, value.trim)}))
      .zipWithIndex
      .map(toShow)
      .toList
  }
}
