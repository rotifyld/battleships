package battleships.models

import battleships.utils.ShotResult

trait Player {

  val ships: List[Ship]
  val receivedShots: Set[(Int, Int)]

  val isDefeated: Boolean = ships.forall(_.isSunk)

  def getNextShot: (Int, Int)

  def result(cell: (Int, Int), shotResult: ShotResult): Player

  def updatePlayer(newShips: List[Ship], newReceivedShots: Set[(Int, Int)]): Player

  def receiveShot(cell: (Int, Int)): (Player, ShotResult) = {
    ships indexWhere (_.cells contains cell) match {
      case -1 => (updatePlayer(ships, receivedShots + cell), ShotResult.Miss)
      case i: Int =>
        val newShip = ships(i).hit(cell)
        (updatePlayer(ships.updated(i, newShip), receivedShots + cell),
          if (newShip.isSunk) ShotResult.ShipSunk(newShip.length) else ShotResult.ShipHit)
    }
  }

}
