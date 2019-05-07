package tickets.web

import java.io.File
import java.nio.file.Files

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.HttpApp
import akka.http.scaladsl.server.directives.FileInfo
import tickets.web.routes.{FrontendRoutes, TicketsRoutes}

import scala.io.Source

object Server extends HttpApp {
  def main(args: Array[String]) = startServer("0.0.0.0", 9000)

  override def routes = TicketsRoutes.routes ~ FrontendRoutes.routes
}

