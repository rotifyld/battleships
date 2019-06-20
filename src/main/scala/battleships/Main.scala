package battleships

import battleships.models.{AIPlayer, ConsolePlayer, Player}

import scala.annotation.tailrec
import scala.util.Random

class Main extends App {

  @tailrec private def mainLoop(attacking: Player, attacked: Player): (Player, Player) = {
    val hitCell = attacking.getNextShot
    val (newAttacked, shotResult) = attacked.receiveShot(hitCell)
    val newAttacking = attacking.result(hitCell, shotResult)

    if (newAttacked.isDefeated) (newAttacking, newAttacked)
    else mainLoop(newAttacked, newAttacking)
  }

  val (winner, loser) = Random.nextBoolean() match { // randomizing starting player
    case true => mainLoop(AIPlayer.empty, ConsolePlayer.empty)
    case false => mainLoop(ConsolePlayer.empty, AIPlayer.empty)
  }
  

}
