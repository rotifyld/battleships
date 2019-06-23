package battleships.models

import battleships.io.{ConsoleIO, GridParser}
import battleships.utils._

import scala.annotation.tailrec
import scala.collection.immutable.{List, Set}

case class ConsolePlayer(ships: List[Ship], receivedShots: Set[(Int, Int)]) extends Player {

  override def getNextShot: (Int, Int) = ConsoleIO.getShot

  override def result(cell: (Int, Int), shotResult: ShotResult): Player = this

  override def updatePlayer(newShips: List[Ship], newReceivedShots: Set[(Int, Int)]): Player =
    new ConsolePlayer(newShips, newReceivedShots)
}

object ConsolePlayer {

  @tailrec def getShips(player: ConsolePlayer, possibleShips: ShipPack, lengthsLeft: List[Int]): ConsolePlayer = {
    if (lengthsLeft == Nil) return player

    ConsoleIO.write(GridParser.stringify(player, visible = true))
    val ship = ConsoleIO.getShip(lengthsLeft.head, possibleShips)
    getShips(player.copy(ships = player.ships :+ ship), possibleShips.filter(ship), lengthsLeft.tail)
  }

  def withShips: ConsolePlayer = {
    val initPlayer = ConsolePlayer(List(), Set())
    getShips(initPlayer, ShipPack.allPossible, Config.ships.map(_._1))
  }
}
