package tickets.dto

import java.time.LocalDate

case class Query(showDate: String) {
  def requestDate = LocalDate.parse(showDate)
}
