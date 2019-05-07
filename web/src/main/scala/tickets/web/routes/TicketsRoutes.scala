package tickets.web.routes

import java.io.File
import java.io.File.createTempFile
import java.nio.file.Files
import java.time.LocalDate

import spray.json._
import DefaultJsonProtocol._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.FileInfo
import tickets.core.Inventory
import tickets.core.format.InventoryJsonFormat
import tickets.core.parser.ShowParser
import tickets.db.ShowRepository
import tickets.dto.{Order, ShowDateQuery}

import scala.io.Source._

object TicketsRoutes extends InventoryJsonFormat {
  implicit val orderFormat = jsonFormat3(Order)
  implicit val queryFormat = jsonFormat1(ShowDateQuery)

  def routes =
    pathPrefix("orders") {
      post {
        entity(as[Order]) { order =>
          val result = ShowRepository.save(order)

          if(result.isRight)
            complete(StatusCodes.OK, result.right.get)
          else
            complete(StatusCodes.BadRequest, result.left.get)
        }
      }
    } ~
    pathPrefix("inventory") {
      post {
        entity(as[ShowDateQuery]) { query =>
          val shows = ShowRepository.allFor(query.showDate)

          complete(Inventory.withPrices(shows, LocalDate.now(), query.showDate))
        }
      } ~
      storeUploadedFile("csv", readFile) {
        case (_, file) =>
          val shows = ShowParser.parse(fromFile(file))

          ShowRepository.saveInventory(shows)

          complete(StatusCodes.OK)
      }
    }

  private def readFile(fileInfo: FileInfo) = createTempFile(fileInfo.fileName, System.currentTimeMillis().toString)
}
