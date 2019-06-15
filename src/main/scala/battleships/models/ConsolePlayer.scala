package battleships.models

case class ConsolePlayer(ships: List[Ship],
                         receivedShots: Set[(Int, Int)]) extends Player {

  override def getNextShot: (Int, Int) = ???

  override def updatePlayer(newShips: List[Ship], newReceivedShots: Set[(Int, Int)]): Player =
    new ConsolePlayer(newShips, newReceivedShots)

  override def result(cell: (Int, Int), wasHit: Boolean): Player = ???
}

object ConsolePlayer {

  def empty: ConsolePlayer = ConsolePlayer(List(), Set())

}
