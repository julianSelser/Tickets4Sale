package tickets.cli

import java.io.File
import java.time.LocalDate

import spray.json._
import tickets.core.Inventory
import tickets.core.format.InventoryJsonFormat

import scala.io.Source.fromFile
import scala.util.Try

case class Input(csv: File, queryDate: LocalDate, showDate: LocalDate)

object Cli extends InventoryJsonFormat {  def main(args: Array[String]): Unit = {
    val input = validate(args) match {
      case Left(error) => println(error); System.exit(0); throw new IllegalArgumentException
      case Right(args)    => args
    }

    val shows = ShowParser.parse(fromFile(input.csv))
    val inventory = Inventory.of(shows, input.queryDate, input.showDate)

    println(inventory.toJson.prettyPrint)
  }

  def validate(args: Array[String]): Either[String, Input] = {
    if(args.length != 3) {
      Left("Usage: tickets4sale-cli <input-csv> <query-date> <show-date>")
    } else {
      val file = new File(args(0))

      if(!file.exists())
        return Left(s"File doesnt exist in [${args(0)}]")
      else if(!Try(LocalDate.parse(args(1))).isSuccess)
        return Left(s"Query date invalid: [${args(1)}]")
      else if(!Try(LocalDate.parse(args(2))).isSuccess)
        return Left(s"Show date invalid: [${args(2)}]")
      else
        Right(Input(file, LocalDate.parse(args(1)), LocalDate.parse(args(2))))
    }
  }
}
