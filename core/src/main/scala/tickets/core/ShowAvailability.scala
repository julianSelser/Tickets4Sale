package tickets.core

case class ShowAvailability(
                             title: String,
                             ticketsLeft: Int,
                             ticketsAvailable:
                             Int, status: String,
                             id: Option[Long],
                             price: Option[Int])
