package clashcode.wordguess.persistence

import play.api.db.DB
import anorm._
import anorm.SqlParser._
import play.api.Play.current

object DAO {

  implicit val gameStateLine = str("line") map {
    case line => line
  }

  def getGameState: List[String] = DB.withConnection {
    implicit c => SQL("select line from game_state").as(gameStateLine *)
  }

  implicit val gameSourceLine = str("line") map {
    case line => line
  }

  def getGameSource: List[String] = DB.withConnection {
    implicit c => SQL("select line from game_source").as(gameStateLine *)
  }

  def deleteAllGameState = DB.withConnection {
    implicit c => SQL("delete from game_state").executeUpdate()
  }

  def insertGameState(s: String) = DB.withConnection {
    implicit  c => SQL("insert into game_state(line) values({line})").on(
      'line -> s
    ).executeUpdate()
  }

  def insertPlayerState(s: String) = DB.withConnection {
    implicit c => SQL("insert into player_state(line) values({line})").on(
      'line -> s
    ).executeUpdate()
  }

  implicit val playerStateLine = str("line") map {
    case line => line
  }

  def getPlayerState: List[String] = DB.withConnection {
    implicit c => SQL("select * from player_state").as(playerStateLine *)
  }

}
