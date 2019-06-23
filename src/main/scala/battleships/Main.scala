package battleships

import battleships.io.GridParser
import battleships.models.{AIPlayer, ConsolePlayer, Player}
import battleships.utils.Config

import scala.util.Random

object Main extends App {

  Random.setSeed(Config.seed)

  private def gameLoop(attacking: Player, attacked: Player): (Player, Player) = {
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
    case (left: ConsolePlayer, right: AIPlayer) => println(GridParser.stringify(left, visibleL = true, right, visibleR = true))
    case (right: AIPlayer, left: ConsolePlayer) => println(GridParser.stringify(left, visibleL = true, right, visibleR = true))
    case _ => ()
  }

}
