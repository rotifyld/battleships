package battleships.models

import battleships.utils._

/**
  * @param cells cell -> is alive (not sunk)
  */
case class Ship(cells: Map[(Int, Int), Boolean]) {

  val isSunk: Boolean = cells.forall(!_._2)

  def hit(cell: (Int, Int)): Ship = this.copy(cells.updated(cell, false))
}

object Ship {

  def fromCoordinates(start: (Int, Int), dir: Direction, length: Int): Ship = {
    require(length > 0)
    if (!Utils.inRange(start) || !Utils.inRange(dir.translate(start, length))) throw new IndexOutOfBoundsException

    def fromCoordinatesRec(startCell: (Int, Int), dir: Direction): Ship = dir match {
      case Left | Up => fromCoordinates(dir.translate(startCell, length - 1), dir.opposite, length)
      case Right | Down =>
        val shipCells = (0 until length) map (i => dir.translate(startCell, i) -> true)
        Ship(shipCells.toMap)
    }

    fromCoordinatesRec(start, dir)
  }

  def empty: Ship = Ship(Map())

}
