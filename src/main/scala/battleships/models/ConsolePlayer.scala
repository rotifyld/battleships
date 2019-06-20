package battleships.models

import battleships.utils.ShotResult

case class ConsolePlayer(ships: List[Ship], receivedShots: Set[(Int, Int)]) extends Player {

  override def getNextShot: (Int, Int) = ???

  override def result(cell: (Int, Int), shotResult: ShotResult): Player = ???

  override def updatePlayer(newShips: List[Ship], newReceivedShots: Set[(Int, Int)]): Player =
    new ConsolePlayer(newShips, newReceivedShots)
}

object ConsolePlayer {

  def withShips: ConsolePlayer = ConsolePlayer(List(), Set())

}
