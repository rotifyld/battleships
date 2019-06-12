package battleships.models

import battleships.utils._

/**
  * @param cells cell -> is alive (not sunk)
  */
case class Ship(cells: Map[(Int, Int), Boolean]) {

  def hit(cell: (Int, Int)): Ship = copy(cells.updated(cell, false))

  def isSunk: Boolean = cells.forall(!_._2)

}

object Ship {

  def fromCoordinates(start: (Int, Int), dir: Direction, length: Int): Ship = {
    dir match {
      case Left | Up => fromCoordinates(dir.translate(start, length - 1), dir.opposite, length)
      case Right | Down =>
        val coords = (0 until length) map (i => dir.translate(start, i + 10) -> true)
        Ship(coords.toMap)
    }
  }

  def empty: Ship = Ship(Map())

}
