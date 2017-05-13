import sbt._

object libs {

  val `scala-version` = "2.12.2"

  def webjar(name: String, version: String): ModuleID =
    "org.webjars" % name % version

  // https://github.com/melrief/pureconfig
  // MPL 2.0
  val pureconfig = "com.github.pureconfig" %% "pureconfig" % "0.7.0"

  // https://github.com/typelevel/cats
  // MIT http://opensource.org/licenses/mit-license.php
  val `cats-core` = "org.typelevel" %% "cats-core" % "0.9.0"

  // https://github.com/functional-streams-for-scala/fs2
  // MIT
  val `fs2-core` = "co.fs2" %% "fs2-core" % "0.9.5"
  val `fs2-io` = "co.fs2" %% "fs2-io" % "0.9.5"

  // https://github.com/Spinoco/fs2-http
  // MIT
  val `fs2-http` = "com.spinoco" %% "fs2-http" % "0.1.7"

  // https://github.com/scalatest/scalatest
  // ASL 2.0
  val scalatest = "org.scalatest" %% "scalatest" % "3.0.3"

  // https://github.com/rickynils/scalacheck
  // unmodified 3-clause BSD
  // val scalacheck = "org.scalacheck" %% "scalacheck" % "1.13.5"

  // https://github.com/scodec/scodec-bits
  // 3-clause BSD
  val `scodec-bits` = "org.scodec" %% "scodec-bits" % "1.1.4"

  // https://github.com/tpolecat/doobie
  // MIT
  val `doobie-core` = "org.tpolecat" %% "doobie-core-cats" % "0.4.1"
  val `doobie-hikari` = "org.tpolecat" %% "doobie-hikari-cats" % "0.4.1"

  // https://jdbc.postgresql.org/
  // BSD
  val postgres = "org.postgresql" % "postgresql" % "9.4.1212"

  // https://github.com/h2database/h2database
  // MPL 2.0 or EPL 1.0
  val h2 = "com.h2database" % "h2" % "1.4.195"

  // https://github.com/circe/circe
  // ASL 2.0
  val `circe-core` = "io.circe" %% "circe-core" % "0.7.1"
  val `circe-generic` = "io.circe" %% "circe-generic" % "0.7.1"
  val `circe-parser` = "io.circe" %% "circe-parser" % "0.7.1"

  // http://tika.apache.org
  // ASL 2.0
  val tika = "org.apache.tika" % "tika-core" % "1.14"

  // https://github.com/typesafehub/scala-logging
  // ASL 2.0
  val `scala-logging` = "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"

  // http://logback.qos.ch/
  // EPL1.0 or LGPL 2.1
  val `logback-classic` = "ch.qos.logback" % "logback-classic" % "1.2.3"

  // https://github.com/t3hnar/scala-bcrypt
  // ASL 2.0
  // using:
  // - jbcrypt: ISC/BSD
  val `scala-bcrypt` = "com.github.t3hnar" %% "scala-bcrypt" % "3.0"

  // https://github.com/Semantic-Org/Semantic-UI
  // MIT
  val `semantic-ui` = webjar("Semantic-UI", "2.2.9")

  // https://github.com/23/resumable.js
  // MIT
  val resumablejs = webjar("resumable.js", "1.0.2")

  // https://github.com/jquery/jquery
  // MIT
  val jquery = webjar("jquery", "3.2.0")

  // https://java.net/projects/javamail/pages/Home
  // CDDL 1.0, GPL 2.0
  val `javax-mail-api` = "javax.mail" % "javax.mail-api" % "1.5.6"
  val `javax-mail` = "com.sun.mail" % "javax.mail" % "1.5.6"

  // http://dnsjava.org/
  // BSD
  val dnsjava = "dnsjava" % "dnsjava" % "2.1.7" intransitive()

  // https://github.com/eikek/yamusca
  // MIT
  val yamusca = "com.github.eikek" %% "yamusca" % "0.1.0"
}