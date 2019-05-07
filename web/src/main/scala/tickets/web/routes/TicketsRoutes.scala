package tickets.web.routes

import java.io.File
import java.nio.file.Files

import akka.http.scaladsl.model.StatusCodes
import tickets.web.Server.{complete, pathPrefix, storeUploadedFile}

object TicketsRoutes {
  def routes =
    pathPrefix("inventory") {
      storeUploadedFile("csv", fileInfo => new File(fileInfo.fileName)) {
        case (_, file) =>

          file.delete()
          complete(StatusCodes.OK)
      }
    }
}
