name := "tickets4sale"

version := "0.1"

scalaVersion := "2.12.8"

mainClass in Compile := (mainClass in Compile in web).value

libraryDependencies in ThisBuild ++= Seq(
  "org.slf4j" % "slf4j-nop" % "1.7.25",
  "com.typesafe.akka" %% "akka-actor" % "2.5.19",
  "com.typesafe.akka" %% "akka-stream" % "2.5.19",
  "com.typesafe.akka" %% "akka-http" % "10.1.3",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.5",
  "com.h2database" % "h2" % "1.4.197",
  "org.scala-lang.modules" %% "scala-collection-compat" % "0.1.1",
  "org.scalikejdbc" %% "scalikejdbc" % "3.3.2",
  "com.github.tototoshi" %% "scala-csv" % "1.3.5",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

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