package battleships.models

import battleships.utils.Utils.Rectangle
import battleships.utils._

/**
  * @param cells cell -> is alive (not sunk)
  */
case class Ship(length: Int, cells: Map[(Int, Int), Boolean], influenceRange: Rectangle) {

  val isSunk: Boolean = cells.forall(!_._2)

  def hit(cell: (Int, Int)): Ship = this.copy(cells = cells.updated(cell, false))

  def collides(other: Ship): Boolean = this.influenceRange.overlaps(other.influenceRange)

  override def toString: String = {
    val cellList = cells.keySet.toList.sorted
    "Ship(" + cellList.head + " ~ " + cellList.last + " : " + cellList.map(cells(_)).map(if (_) 'O' else 'X').mkString +  ")"
  }

}

object Ship {

  def fromCoordinates(start: (Int, Int), dir: Direction, length: Int): Ship = {
    require(length > 0)
    val end = dir.translate(start, length - 1)

//    if (!Utils.inRange(start) || !Utils.inRange(end))
//      throw new IndexOutOfBoundsException("start: " + start + " dir: " + dir + " length " + length)

    val shipCells = (0 until length) map (i => dir.translate(start, i) -> true)
    Ship(length, shipCells.toMap, Rectangle.extended(start, end))
  }

  def single(cell: (Int, Int)): Ship = {
    Ship(1, Map(cell -> true), Rectangle.extended(cell, cell))
  }

}
