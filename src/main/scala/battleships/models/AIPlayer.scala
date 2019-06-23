package battleships.models

import battleships.utils._

case class AIPlayer(ships: List[Ship],
                    receivedShots: Set[(Int, Int)],
                    possibleEnemyShips: ShipPack,
                    lastHitFatal: Boolean,
                    lastHitCell: (Int, Int)) extends Player {

  override def getNextShot: (Int, Int) = if (lastHitFatal) {
    possibleEnemyShips.randomShot
  } else {
    possibleEnemyShips.randomShotContaining(lastHitCell)
  }

  override def result(cell: (Int, Int), shotResult: ShotResult): Player = {
    val newPossibleEnemyShips = possibleEnemyShips.filter(cell, shotResult)
    shotResult match {
      case ShotResult.Miss =>
        this.copy(possibleEnemyShips = newPossibleEnemyShips)
      case ShotResult.ShipHit =>
        this.copy(possibleEnemyShips = newPossibleEnemyShips, lastHitFatal = false, lastHitCell = cell)
      case ShotResult.ShipSunk(_) =>
        this.copy(possibleEnemyShips = newPossibleEnemyShips, lastHitFatal = true)
    }
  }

  override def updatePlayer(newShips: List[Ship], newReceivedShots: Set[(Int, Int)]): Player =
    AIPlayer(newShips, newReceivedShots, possibleEnemyShips, lastHitFatal, lastHitCell)

}

object AIPlayer {

  def withShips: AIPlayer = {
    val ships = Config.ships.foldRight((ShipPack.allPossible, List[Ship]())) {
      case (_, (pack, list)) =>
        val nextShip = pack.randomShip
        (pack.filter(nextShip), list :+ nextShip)
    }
    assert(ships._1.ships.isEmpty)

    AIPlayer(ships._2, Set(), ShipPack.allPossible, lastHitFatal = true, (0, 0))
  }

}

