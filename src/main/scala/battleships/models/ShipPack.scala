package battleships.models

//import battleships.utils.Config.{gridSize, ships}
import battleships.utils._

case class ShipPack(allShips: Vector[Ship]) {

  def filter(placed: Ship): ShipPack = {
    ShipPack(allShips.filterNot(_.collides(placed)))
  }

  def filter(cell: (Int, Int)): ShipPack = {
    val placed = Ship.single(cell)
    ShipPack(allShips.filterNot {
      (s: Ship) => s.collides(placed) && !s.cells.keySet.contains(cell)
    })
  }


}

object ShipPack {

  val allPossible: ShipPack = {
    val allShips = (for {
      length <- Config.ships map (_._1)
      x <- 0 until Config.gridSize
      y <- 0 to Config.gridSize - length
    } yield List(Ship.fromCoordinates((x, y), Down, length), Ship.fromCoordinates((y, x), Right, length))).flatten
    ShipPack(allShips.toVector)
  }

}
