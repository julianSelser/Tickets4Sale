package tickets.db
package persistance.repositories

import scalikejdbc._

trait TicketsDB {

  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:mem:tickets;DB_CLOSE_DELAY=-1", "user", "pass")

  implicit val session = AutoSession
}

object TicketsDB extends TicketsDB {
  def init = {
    sql"""
      create table if not exists shows (
        id serial not null primary key,
        title varchar not null unique,
        genre varchar not null,
        opening_day date not null
      )
      """.execute.apply()

    sql"""
      create table if not exists orders (
        id serial not null primary key,
        order_date date not null,
        show_id int not null,
        price int not null,
        foreign key (show_id) references shows(id)
      )
      """.execute.apply()
  }
}

