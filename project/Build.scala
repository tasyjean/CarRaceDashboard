import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "CarRaceDashboard"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      "reactivemongo" %% "reactivemongo" % "0.1-SNAPSHOT" cross CrossVersion.full,
      "play.modules.reactivemongo" %% "play2-reactivemongo" % "0.1-SNAPSHOT" cross CrossVersion.full
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      resolvers += "sgodbillon" at "https://bitbucket.org/sgodbillon/repository/raw/master/snapshots/"
    )

}
