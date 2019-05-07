name := "tickets4sale"

version := "0.1"

scalaVersion := "2.12.8"

mainClass in Compile := (mainClass in Compile in web).value

libraryDependencies in ThisBuild ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.19",
  "com.typesafe.akka" %% "akka-stream" % "2.5.19",
  "com.typesafe.akka" %% "akka-http" % "10.1.3",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.5",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "com.github.tototoshi" %% "scala-csv" % "1.3.5"
)


// this is the root project, aggregating all sub projects
//lazy val root = Project(
//  id = "root",
//  base = file("."),
//  // configure your native packaging settings here
//  settings = Seq(
//    maintainer := "John Smith <john.smith@example.com>",
//    packageDescription := "Fullstack Application",
//    packageSummary := "Fullstack Application",
//    // entrypoint
//    mainClass in Compile := Some("de.mukis.frontend.ProductionServer")
//  ),
//  // always run all commands on each sub project
//  aggregate = Seq(frontend, backend, api)
//).enablePlugins(JavaServerAppPackaging) // enable app packaging on this project
//  .dependsOn(frontend, backend, api) // this does the actual aggregation


lazy val root = (project in file("."))
  .aggregate(core, cli, web)
  .dependsOn(core, cli, web)

lazy val web = Project(
    id = "web",
    base = file("web")
  ).settings(Seq(
    maintainer := "Julian Selser<julian.selser.utn@egmail.com>",
    packageDescription := "Tickets4Sale",
    packageSummary := "Tickets4Sale",
    mainClass in Compile := Some("tickets.web.Server")
  ))
  .enablePlugins(JavaServerAppPackaging)
  .dependsOn(core)

lazy val cli = Project(
  id = "cli",
  base = file("cli")
).enablePlugins(JavaAppPackaging).dependsOn(core)

lazy val core = Project(
  id = "core",
  base = file("core")
)