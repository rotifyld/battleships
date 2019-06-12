package battleships.models

trait Player {

  val ships: Seq[Ship]
  val receivedShots: Set[(Int, Int)]

  def updatePlayer(newShips: Seq[Ship], newReceivedShots: Set[(Int, Int)]): Player

  def receiveShot(cell: (Int, Int)): Player = {
    if (receivedShots contains cell) this
    else {
      ships indexWhere (_.cells contains cell) match {
        case -1 => updatePlayer(ships, receivedShots + cell)
        case i: Int => updatePlayer(ships.updated(i, ships(i).hit(cell)), receivedShots + cell)
      }
    }
  }


}
