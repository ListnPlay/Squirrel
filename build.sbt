import _root_.bintray.BintrayPlugin.autoImport._

resolvers ++= Seq(
  "Feature.fm" at "http://dl.bintray.com/listnplay/maven"
)

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation")

val json4sVersion = "3.3.0"

val jacksonVersion = "2.7.2"

libraryDependencies ++= Seq(
  "com.softwaremill.quicklens"  %% "quicklens"           % "1.4.1",
  "com.github.nscala-time"      %% "nscala-time"         % "2.4.0",
  "org.scalaj"                  %% "scalaj-http"         % "1.1.4",
  "com.fasterxml.jackson.core"  % "jackson-core"         % jacksonVersion,
  "com.fasterxml.jackson.core"  % "jackson-annotations"  % jacksonVersion,
  "org.json4s"                  %% "json4s-jackson"      % json4sVersion exclude("com.fasterxml.jackson.core", "jackson-core") exclude("com.fasterxml.jackson.core", "jackson-annotations"),
  "org.json4s"                  %% "json4s-ext"          % json4sVersion,
  "com.typesafe"                 % "config"              % "1.3.0",
  "com.github.slugify"           %  "slugify"            % "2.1.4",
  "io.john-ky"                  %% "hashids-scala"       % "1.1.1-7d841a8",
//------------------------------- TEST -----------------------------------
  "org.scalacheck"	            %% "scalacheck"	         % "1.12.2"    % "test",
  "com.osinka.slugify"          %% "slugify"             % "1.2.1"     % "test",
  "org.scalatest"               %% "scalatest"           % "2.2.5"     % "test"

)

lazy val root = (project in file(".")).settings(
    name := "squirrel",
    organization := "com.featurefm",
    version := "0.2.5",
    scalaVersion := "2.11.8",
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
