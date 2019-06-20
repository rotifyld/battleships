package battleships

import battleships.io.GridParser
import battleships.models.{AIPlayer, ConsolePlayer, Player}

import scala.annotation.tailrec
import scala.util.Random

object Main extends App {

  @tailrec private def gameLoop(attacking: Player, attacked: Player): (Player, Player) = {
    attacking match {
      case _: ConsolePlayer => println(GridParser.stringify(attacking, visibleL = true, attacked, visibleR = false))
      case _ => ()
    }
    val hitCell = attacking.getNextShot
    val (newAttacked, shotResult) = attacked.receiveShot(hitCell)
    val newAttacking = attacking.result(hitCell, shotResult)

    if (newAttacked.isDefeated) (newAttacking, newAttacked)
    else gameLoop(newAttacked, newAttacking)
  }

  // randomizing starting player
  val (winner, loser) = if (Random.nextBoolean()) {
    gameLoop(AIPlayer.withShips, ConsolePlayer.withShips)
  } else {
    gameLoop(ConsolePlayer.withShips, AIPlayer.withShips)
  }

  (winner, loser) match {
    case (winner: ConsolePlayer, loser: AIPlayer) => println(GridParser.stringify(winner, visibleL = true, loser, visibleR = true))
    case (winner: AIPlayer, loser: ConsolePlayer) => println(GridParser.stringify(loser, visibleL = true, winner, visibleR = true))
  }

}
