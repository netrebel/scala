import com.typesafe.sbt.SbtAspectj._


name := "akka-rest-mongodb"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0-M2",
  "org.reactivemongo" %% "reactivemongo" % "0.11.9",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.9",
  "com.typesafe.play" % "play-json_2.11" % "2.5.0-M2",
  "ch.qos.logback" % "logback-classic" % "1.1.3"
)


resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Typesafe" at "https://repo.typesafe.com/typesafe/releases/"

mainClass in (Compile, run) := Some("Boot")