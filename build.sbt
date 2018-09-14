name := "adserver"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka"          %% "akka-http"         % "10.1.5",
  "com.typesafe.akka"          %% "akka-stream"       % "2.5.12",
  "com.beachape"               %% "enumeratum"        % "1.5.13",
  "ch.qos.logback"              % "logback-classic"   % "1.2.3",
  "io.circe"                   %% "circe-core"        % "0.9.3",
  "io.circe"                   %% "circe-generic"     % "0.9.3",
  "com.typesafe.scala-logging" %% "scala-logging"     % "3.7.2",
  "io.rest-assured"             % "scala-support"     % "3.1.1"  % "test",
  "org.scalatest"              %% "scalatest"         % "3.0.5"  % "test"
)