name := "tickets4sale"

version := "0.1"

scalaVersion := "2.12.8"

// common dependencies
libraryDependencies in ThisBuild ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.19",
  "com.typesafe.akka" %% "akka-stream" % "2.5.19",
  "com.typesafe.akka" %% "akka-http" % "10.1.3",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.5",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "com.github.tototoshi" %% "scala-csv" % "1.3.5"
)

lazy val root = (project in file(".")).aggregate(core, cli, web)

lazy val web = Project(
  id = "web",
  base = file("web")
).enablePlugins(JavaAppPackaging).dependsOn(core)


lazy val cli = Project(
  id = "cli",
  base = file("cli")
).enablePlugins(JavaAppPackaging).dependsOn(core)

lazy val core = Project(
  id = "core",
  base = file("core")
)