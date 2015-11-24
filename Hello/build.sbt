name := "Hello"

version := "1.0"

scalaVersion := "2.11.7"

lazy val akkaVersion = "2.4.0"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.5" % "test",
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-experimental" % "2.0-M1",
  "com.typesafe.play" %% "play-json" % "2.4.2"
)