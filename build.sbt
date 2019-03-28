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

/*
resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.9")

// if your project uses multiple Scala versions, use this for cross building
addCompilerPlugin("org.spire-math" % "kind-projector" % "0.9.9" cross CrossVersion.binary)

// if your project uses both 2.10 and polymorphic lambdas
libraryDependencies ++= (scalaBinaryVersion.value match {
  case "2.10" =>
    compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full) :: Nil
  case _ =>
    Nil
})
 */