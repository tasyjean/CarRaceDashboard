package models

import akka.actor._
import models.DB._
import com.mongodb.casbah.Imports._
import models.Streams._
import play.api.Logger
import play.api.libs.concurrent.Akka
import play.api.Play.current

object StorageActor{
	
  val logger=Logger("actor-storage")

  val actor =  Akka.system.actorOf(Props(new Actor {

    def receive = {
      case e:Event => 
        logger.debug("New event received : "+e)
        Akka.system.eventStream.publish(e)
        connection("events").insert(  e match {
            case SpeedEvent(car,speed) =>
              MongoDBObject (
                "type" -> "speed",
                "car" -> car,
                "speed" -> speed
              )
            case DistEvent(car,dist) =>
              MongoDBObject (
                "type" -> "dist",
                "car" -> car,
                "dist" -> dist
              )
            case PositionEvent(car,latitude,longitude) =>
              MongoDBObject (
                "type" -> "pos",
                "car" -> car,
                "lat" -> latitude,
                "long" -> longitude
              )
          }
        )
    }

    override def preStart={
      // Clean collection
      connection("events").dropCollection
    }

  }), name = "storage")

}