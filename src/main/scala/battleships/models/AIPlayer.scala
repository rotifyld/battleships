package battleships.models

case class AIPlayer(ships: List[Ship],
                    receivedShots: Set[(Int, Int)]) extends Player {

  override def getNextShot: (Int, Int) = ???

  override def updatePlayer(newShips: List[Ship], newReceivedShots: Set[(Int, Int)]): Player =
    new AIPlayer(newShips, newReceivedShots)

  override def result(cell: (Int, Int), wasHit: Boolean): Player = ???
}

object AIPlayer {

  def empty: AIPlayer = AIPlayer(List(), Set())

}

