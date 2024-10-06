lazy val root = file("..").toURI
lazy val `migrate-interface` = ProjectRef(root, "migrate-interface")
//lazy val `compiler-interface` = ProjectRef(root, "compiler-interface")
//lazy val migrate = ProjectRef(root, "migrate")
//lazy val `scalafix-rules` = ProjectRef(root, "scalafix-rules")

evictionErrorLevel := Level.Warn

lazy val `sbt-plugin` = project
  .in(file("."))
//  .enablePlugins(SbtPlugin)
  .settings(
    scalaVersion := V.scala213,
    name         := "sbt-scala3-migrate",
    scalacOptions += "-Ytasty-reader",
    scalacOptions += "-Xsource:3",
    semanticdbVersion := "4.10.1",
    libraryDependencies ++= Seq(
      ("org.scala-sbt" % "sbt" % "2.0.0-M2")
        .exclude("org.scala-lang.modules", "scala-collection-compat_3")
        .exclude("org.scala-lang.modules", "scala-xml_3"),
      ("org.scala-sbt" %% "main-settings" % "2.0.0-M2").cross(CrossVersion.for2_13Use3)
        .exclude("org.scala-lang.modules", "scala-collection-compat_3")
        .exclude("org.scala-lang.modules", "scala-xml_3"),
      ("io.get-coursier" %% "coursier"                      % V.coursierApi).cross(CrossVersion.for3Use2_13),
      ("io.get-coursier" %% "coursier-sbt-maven-repository" % V.coursierApi).cross(CrossVersion.for3Use2_13),
      "org.scalatest"   %% "scalatest"                     % V.scalatest % Test,
      ("org.scala-lang.modules" %% "scala-collection-compat" % "2.11.0").cross(CrossVersion.for3Use2_13),
      ("org.scala-lang.modules" %% "scala-xml" % "2.3.0").cross(CrossVersion.for3Use2_13),
    ),
//    scriptedLaunchOpts ++= Seq(s"-Dplugin.version=${version.value}"),
//    scriptedDependencies := scriptedDependencies
//      .dependsOn(
//        `migrate-interface` / publishLocal,
//        `compiler-interface` / publishLocal,
//        migrate / publishLocal,
//        `scalafix-rules` / publishLocal
//      )
//      .value,
    buildInfoPackage  := "migrate",
//    scriptedBufferLog := false,
    buildInfoKeys := Seq[BuildInfoKey](
      name,
      "scala3Version"      -> V.scala3,
      "scalaBinaryVersion" -> V.scala213BinaryVersion,
      "scalametaVersion"   -> V.scalameta,
      version
    )
  )
  .dependsOn(`migrate-interface`)
  .enablePlugins(BuildInfoPlugin)

lazy val V = new {
  val scala213              = "2.13.15"
  val scala213BinaryVersion = "2.13"
  val scala212              = "2.12.20"
  val scalatest             = "3.2.19"
  val scala3                = "3.3.4"
  val scalafix              = "0.13.0"
  val catsCore              = "2.10.0"
  val kindProjector         = "0.13.3"
  val coursierApi           = "2.1.13"
  val coursierInterface     = "1.0.21"
  val scalameta             = "4.10.1"
  val localSnapshotVersion  = "0.7.0-SNAPSHOT"
  // scala-steward:off
  val zio = "1.0.18"
  // scala-steward:on
}
