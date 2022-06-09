ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "AspectNim"
  )

githubTokenSource := TokenSource.Or(
  TokenSource.Environment("GITHUB_TOKEN"), // Injected during a github workflow for publishing
  TokenSource.GitConfig("github.token") // local token set in ~/.gitconfig
)

resolvers += Resolver.githubPackages("wustl-frisc", "foam")
libraryDependencies ++= Seq("edu.wustl.sbs" %% "foam" % "1.0.1")