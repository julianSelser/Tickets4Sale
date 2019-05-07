package tickets.db

import java.time.LocalDate

import tickets.core.{Genres, Show}
import scalikejdbc._
import tickets.db.persistance.repositories.TicketsDB
import tickets.dto.Order

import scala.util.Try

object ShowRepository extends TicketsDB {

  def save(order: Order): Either[String, String] = {
    val show = getShowById(order.showId).getOrElse(return Left("show doesnt exist"))
    val showOrdersForDate = countShowOrdersForDate(order.showId, order.showDate)

    val availability = show
      .copy(ticketsSoldForDay = showOrdersForDate)
      .availability(LocalDate.now, order.showDate)

    if(availability.ticketsAvailable > 0)
      doSave(order)
    else
      Left("No tickets available for date")
  }

  def allFor(showDate: LocalDate): List[Show] = {
    sql"""
          select s.title, s.opening_day, s.genre, s.id, count(o.id) as orders_num_for_date
          from shows s
          left join orders o on o.show_id = s.id and o.order_date=${showDate.toString}
          group by s.id
      """.map(toShow).list().apply()
  }

  def saveInventory(shows: List[Show]): Seq[Int] = {
    DB localTx { implicit session =>
      truncateDB

      sql"""insert into shows (title, opening_day, genre) values ({title}, {opening_day}, {genre})"""
        .batchByName(shows.map(s => Seq('title -> s.title, 'opening_day -> s.openingDay, 'genre -> s.genre.toString)):_*)
        .apply()
    }
  }

  private def doSave(order: Order): Either[String, String] = {
    Try(
      sql"""
          insert into orders (show_id, order_date, price)
          values (${order.showId}, ${order.showDate.toString}, ${order.price})
      """
      .executeUpdate()
      .apply())
      .toEither match {
        case Right(_) => Right("Order saved successfully")
        case Left(_) => Left("Couldn't save order, something went wrong")
      }
  }

  private def countShowOrdersForDate(showId: Long, showDate: LocalDate): Option[Int] = {
    sql"""select count(*) as orders_num_for_date from orders where show_id=${showId} and order_date=${showDate.toString}"""
      .map(_.int("orders_num_for_date"))
      .single()
      .apply()
  }

  private def getShowById(showId: Long): Option[Show] = {
    sql"""select *, 0 as orders_num_for_date from shows where id=${showId}""".map(toShow).single().apply()
  }

  private def truncateDB: Unit = {
    sql"""SET FOREIGN_KEY_CHECKS=0;TRUNCATE TABLE `shows`; TRUNCATE TABLE `orders`;SET FOREIGN_KEY_CHECKS=1;""".execute().apply()
  }

  private def toShow(rs: WrappedResultSet) = {
    Show(
      rs.string("title"),
      rs.localDate("opening_day"),
      Genres.withName(rs.string("genre")),
      rs.longOpt("id"),
      rs.intOpt("orders_num_for_date")
    )
  }
}
