package tickets.dto

import java.time.LocalDate

case class Order(showId: Long, price: Int, showDate: LocalDate)
