name := "fp"

version := "0.1"

scalaVersion := "2.12.8"

scalacOptions ++= Seq(
      "-Ypartial-unification",
      "-language:postfixOps",
      "-language:higherKinds",
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",      
      "-unchecked"
    )