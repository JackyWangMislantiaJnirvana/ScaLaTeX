name := "ScalaTeX RC3"

version := "0.1"

scalaVersion := "2.13.6"

lazy val root = (project in file(".")).enablePlugins(SbtTwirl)

libraryDependencies += "com.github.scopt" %% "scopt" % "4.0.1"
