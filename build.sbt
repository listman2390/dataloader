name := """dataloader"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  evolutions,
  "org.postgresql" % "postgresql" % "9.4-1200-jdbc41",
  "commons-dbutils" % "commons-dbutils" % "1.6",
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final"
)

PlayKeys.externalizeResources := false
