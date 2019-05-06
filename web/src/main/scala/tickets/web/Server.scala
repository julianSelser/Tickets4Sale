package tickets.web

import akka.http.scaladsl.server.HttpApp

object Server extends HttpApp {
  def main(args: Array[String]) = startServer("0.0.0.0", 9000)

  override def routes =
    pathEndOrSingleSlash {
      pathEnd {
        getFromResource("index.html")
      }
    } ~
      pathPrefix("site") {
        getFromResourceDirectory("site")
      }
}
