name := """tp6"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies += "org.jsoup" % "jsoup" % "1.7.2"

libraryDependencies += "org.json" % "json" % "20160212"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

lazy val myProject = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

routesGenerator := StaticRoutesGenerator