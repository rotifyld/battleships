package battleships

import battleships.models.{AIPlayer, ConsolePlayer, Player}

import scala.annotation.tailrec

class Main extends App {

  @tailrec private def mainLoop(attacking: Player, attacked: Player): (Player, Player) = {
    val hitCell = attacking.getNextShot
    val (newAttacked, wasHit) = attacked.receiveShot(hitCell)
    val newAttacking = attacking.result(hitCell, wasHit)

    if (newAttacked.isDefeated) (newAttacking, newAttacked)
    else mainLoop(newAttacked, newAttacking)
  }

  val (winner, loser) = mainLoop(ConsolePlayer.empty, AIPlayer.empty)

}
