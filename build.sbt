ThisBuild / scalaVersion := "3.0.1"
ThisBuild / organization := "info.ditrapani"

lazy val root = project
  .in(file("."))
  .settings(
    name := "cli-admin",
    version := "0.1",
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.4.1",
      "com.softwaremill.sttp.client3" %% "core" % "3.3.13",
      "com.softwaremill.sttp.client3" %% "httpclient-backend" % "3.3.13",
      "dev.zio" %% "zio-json" % "0.2.0-M1",
      "org.scalatest" %% "scalatest" % "3.2.9" % "test",
    ),
  )

scalacOptions ++= Seq("-Yexplicit-nulls")
