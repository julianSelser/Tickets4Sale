package tickets.web

import akka.http.scaladsl.server.HttpApp
import tickets.web.routes.{FrontendRoutes, TicketsRoutes}

object Server extends HttpApp {
  def main(args: Array[String]) = startServer("0.0.0.0", if (args.isEmpty) 9000 else args(0).toInt)

  override def routes = TicketsRoutes.routes ~ FrontendRoutes.routes
}

