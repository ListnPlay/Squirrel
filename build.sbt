import _root_.bintray.BintrayPlugin.autoImport._

resolvers ++= Seq(
  "Feature.fm" at "http://dl.bintray.com/listnplay/maven"
)

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation")

val json4sVersion   = "3.5.3"

val jacksonVersion  = "2.8.5"

libraryDependencies ++= Seq(
  "com.softwaremill.quicklens"  %% "quicklens"           % "1.6.1",
  "com.github.nscala-time"      %% "nscala-time"         % "2.18.0",
  "org.scalaj"                  %% "scalaj-http"         % "2.3.0",
  "com.fasterxml.jackson.core"   % "jackson-core"        % jacksonVersion,
  "com.fasterxml.jackson.core"   % "jackson-annotations" % jacksonVersion,
  "org.json4s"                  %% "json4s-jackson"      % json4sVersion exclude("com.fasterxml.jackson.core", "jackson-core") exclude("com.fasterxml.jackson.core", "jackson-annotations"),
  "org.json4s"                  %% "json4s-ext"          % json4sVersion,
  "com.typesafe"                 % "config"              % "1.3.0",
  "com.github.slugify"           % "slugify"             % "2.1.4",
  "com.github.ancane"           %% "hashids-scala"       % "1.3",
//------------------------------- TEST -----------------------------------
  "org.scalacheck"	            %% "scalacheck"	         % "1.14.0"        % Test,
  "org.scalatest"               %% "scalatest"           % "3.0.5"         % Test

)

lazy val scala212 = "2.12.9"
lazy val scala211 = "2.11.12"
lazy val supportedScalaVersions = List(scala211, scala212)

lazy val root = (project in file(".")).settings(
    name := "squirrel",
    organization := "com.featurefm",
    version := "0.3.2",
    crossScalaVersions := supportedScalaVersions,
    bintrayOrganization := Some("listnplay"),
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    publishMavenStyle := true,
    pomAllRepositories := true,
    pomExtra := <scm>
                  <url>https://github.com/ListnPlay/Squirrel</url>
                  <connection>git@github.com:ListnPlay/Squirrel.git</connection>
                </scm>
                <developers>
                  <developer>
                    <id>ymeymann</id>
                    <name>Yardena Meymann</name>
                    <url>https://github.com/ymeymann</url>
                  </developer>
                </developers>
)
