name := "itRecruit"

version := "1.0-SNAPSHOT"

lazy val commonSettings = Seq(
  scalaVersion := "2.12.4",
  crossScalaVersions := Seq("2.11.12", "2.12.4")
)

lazy val server = (project in file("server")).settings(commonSettings).settings(
  scalaJSProjects := Seq(client),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  libraryDependencies ++= Seq(
    "com.vmunier" %% "scalajs-scripts" % "1.1.1",
    "com.dripower" %% "play-circe" % "2609.1",
    guice
  )
).enablePlugins(PlayScala)


lazy val client = (project in file("client")).settings(commonSettings).settings(
  crossTarget in fastOptJS := baseDirectory.value / ".." / "server" / "public" / "javascripts",
  libraryDependencies ++= Seq(
    "io.circe" %%% "circe-scalajs" % "0.8.0",
    "io.circe" %%% "circe-core" % "0.8.0",
    "io.circe" %%% "circe-generic" % "0.8.0",
    "io.circe" %%% "circe-parser" % "0.8.0",
    "be.doeraene" %%% "scalajs-jquery" % "0.9.1",
    "com.github.karasiq" %%% "scalajs-highcharts" % "1.2.1",
    "com.thoughtworks.binding" %%% "binding" % "11.0.1",
    "com.thoughtworks.binding" %%% "dom" % "11.0.1",
    "com.thoughtworks.binding" %%% "futurebinding" % "11.0.1"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSWeb)

//lazy val sharedJvm = shared.jvm

//lazy val sharedJs = shared.js

onLoad in Global := (onLoad in Global).value andThen { s: State => "project server" :: s }