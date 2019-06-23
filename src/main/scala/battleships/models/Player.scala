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
    ships.find(_.cells contains cell) match {
      case None => (updatePlayer(ships, receivedShots + cell), ShotResult.Miss)
      case Some(oldShip) =>
        val newShip = oldShip.hit(cell)
        val newPlayer = this.updatePlayer(ships.map { case `oldShip` => newShip; case s => s }, receivedShots + cell)
        val shotResult = if (newShip.isSunk) ShotResult.ShipSunk(newShip.length) else ShotResult.ShipHit
        (newPlayer, shotResult)
    }
  }
}
