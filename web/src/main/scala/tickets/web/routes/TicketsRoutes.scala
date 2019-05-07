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
import tickets.dto.Query

import scala.io.Source._

object TicketsRoutes extends InventoryJsonFormat {
  implicit val queryFormat = jsonFormat1(Query)

  def routes =
    pathPrefix("inventory") {
      post {
        entity(as[Query]) { query =>
          val shows = ShowRepository.all

          complete(Inventory.withPrices(shows, LocalDate.now(), query.requestDate))
        }
      } ~
      storeUploadedFile("csv", readFile) {
        case (_, file) =>
          val shows = ShowParser.parseWithIndex(fromFile(file))

          ShowRepository.save(shows)

          complete(StatusCodes.OK)
      }
    }

  def readFile(fileInfo: FileInfo) = createTempFile(fileInfo.fileName, System.currentTimeMillis().toString)
}
