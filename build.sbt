import sbt.dsl._

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation")

val json4sVersion = "3.3.0"

val jacksonVersion = "2.6.3"

libraryDependencies ++= Seq(
  "com.softwaremill.quicklens"  %% "quicklens"           % "1.4.1",
  "com.github.nscala-time"      %% "nscala-time"         % "2.4.0",
  "org.scalaj"                  %% "scalaj-http"         % "1.1.4",
  "com.gilt"                    %% "handlebars-scala"    % "2.0.1",
  "com.fasterxml.jackson.core"  % "jackson-core"         % jacksonVersion,
  "com.fasterxml.jackson.core"  % "jackson-annotations"  % jacksonVersion,
  "org.json4s"                  %% "json4s-jackson"      % json4sVersion exclude("com.fasterxml.jackson.core", "jackson-core") exclude("com.fasterxml.jackson.core", "jackson-annotations"),
  "org.json4s"                  %% "json4s-ext"          % json4sVersion,
  "com.typesafe"                 % "config"              % "1.3.0",
//------------------------------- TEST -----------------------------------
  "org.scalatest"               %% "scalatest"           % "2.2.5"     % "test"

)

lazy val root = (project in file(".")).settings(
    name := "squirrel",
    organization := "com.featurefm",
    version := "0.1",
    scalaVersion := "2.11.7")