package tickets.web.routes

import tickets.web.Server._

object FrontendRoutes {
  def routes =
    pathEndOrSingleSlash {
      pathEnd {
        getFromResource("index.html")
      }
    } ~
    pathPrefix("site") {
      getFromResourceDirectory("site")
    }

}
