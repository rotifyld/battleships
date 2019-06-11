package battleships.models

/**
  * @param cells cell -> is alive (not sunk)
  */
case class Ship(cells: Map[(Int, Int), Boolean]) {

  def hit(cell: (Int, Int)): Ship = copy(cells.updated(cell, false))

  def isSunk: Boolean = cells.forall(!_._2)

}
