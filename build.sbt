name := "tickets4sale"

version := "0.1"

scalaVersion := "2.12.8"

// common dependencies
libraryDependencies in ThisBuild ++= Seq(
  "com.typesafe" % "config" % "1.2.0"
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
).enablePlugins(JavaAppPackaging)