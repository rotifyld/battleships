package battleships.models

trait Player {

  val ships: List[Ship]
  val receivedShots: Set[(Int, Int)]

  val isDefeated: Boolean = ships.forall(_.isSunk)

  def getNextShot: (Int, Int)

  def updatePlayer(newShips: List[Ship], newReceivedShots: Set[(Int, Int)]): Player

  def receiveShot(cell: (Int, Int)): (Player, Boolean) = {
    if (receivedShots contains cell) (this, false)
    else {
      ships indexWhere (_.cells contains cell) match {
        case -1 => (updatePlayer(ships, receivedShots + cell), false)
        case i: Int => (updatePlayer(ships.updated(i, ships(i).hit(cell)), receivedShots + cell), true)
      }
    }
  }

  def result(cell: (Int, Int), wasHit: Boolean): Player

}
