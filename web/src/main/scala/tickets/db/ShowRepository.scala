package tickets.db

import java.time.LocalDate

import tickets.core.{Genres, Show}
import scalikejdbc._
import tickets.db.persistance.repositories.TicketsDB

object ShowRepository extends TicketsDB {
  def save(shows: List[Show]): Seq[Int] = {
    DB localTx { implicit session =>
      truncateDB

      sql"""insert into shows (title, opening_day, genre) values ({title}, {opening_day}, {genre})"""
        .batchByName(shows.map(s => Seq('title -> s.title, 'opening_day -> s.openingDay, 'genre -> s.genre.toString)):_*)
        .apply()
    }
  }

  def all: List[Show] = {
    sql"""select * from shows"""
      .map(rs =>
        Show(
          rs.string("title"),
          rs.localDate("opening_day"),
          Genres.withName(rs.string("genre")),
          Some(rs.long("id"))
        )
      ).list().apply()
  }

  private def truncateDB = {
    sql"""SET FOREIGN_KEY_CHECKS=0;TRUNCATE TABLE `shows`; TRUNCATE TABLE `orders`;SET FOREIGN_KEY_CHECKS=1;""".execute().apply()
  }
}
