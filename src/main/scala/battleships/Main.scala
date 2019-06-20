package battleships

import battleships.io.GridParser
import battleships.models.{AIPlayer, ConsolePlayer, Player}

import scala.annotation.tailrec
import scala.util.Random

class Main extends App {

  @tailrec private def mainLoop(attacking: Player, attacked: Player): (Player, Player) = {
    attacking match {
      case _: ConsolePlayer => println(GridParser.stringify(attacking, visibleL = true, attacked, visibleR = false))
      case _ => ()
    }
    val hitCell = attacking.getNextShot
    val (newAttacked, shotResult) = attacked.receiveShot(hitCell)
    val newAttacking = attacking.result(hitCell, shotResult)

    if (newAttacked.isDefeated) (newAttacking, newAttacked)
    else mainLoop(newAttacked, newAttacking)
  }

  // randomizing starting player
  val (winner, loser) = if (Random.nextBoolean()) {
    mainLoop(AIPlayer.withShips, ConsolePlayer.withShips)
  } else {
    mainLoop(ConsolePlayer.withShips, AIPlayer.withShips)
  }

}
