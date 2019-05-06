package tickets.core

case class ShowAvailability(
                             title: String,
                             ticketsLeft: Int,
                             ticketsAvailable: Int,
                             status: String,
                             id: Option[Long] = None,
                             price: Option[Int] = None)
