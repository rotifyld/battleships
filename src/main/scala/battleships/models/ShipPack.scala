package battleships.models

import battleships.utils._

import scala.util.Random

case class ShipPack(ships: Vector[Ship], sizes: Map[Int, Int]) {

  def filter(placed: Ship): ShipPack = {
    val ofLength = sizes(placed.length)
    ofLength match {
      case 1 => this.copy(ships.filterNot(s => s.length == placed.length || s.collides(placed)), sizes - placed.length)
      case _ => this.copy(ships.filterNot(_.collides(placed)), sizes.updated(placed.length, ofLength - 1))
    }
  }

  private def shipsHit(ships: Vector[Ship], cell: (Int, Int)): Vector[Ship] = {
    val (inRange, outOfRange) = ships.partition(_.cells.contains(cell))
    inRange.map(_.hit(cell)).filter(!_.isSunk) ++ outOfRange
  }

  def filter(cell: (Int, Int), shotResult: ShotResult): ShipPack = {

    shotResult match {
      case ShotResult.Miss =>
        this.copy(ships.filterNot(_.cells.keySet.contains(cell)))
      case ShotResult.ShipHit =>
        val asShip = Ship.single(cell)
        this.copy(shipsHit(ships, cell).filterNot(s => s.collides(asShip) && !s.cells.keySet.contains(cell)))
      case ShotResult.ShipSunk(length) =>
        val asShip = Ship.single(cell)
        val ofLength = sizes(length)
        ofLength match {
          case 1 => this.copy(shipsHit(ships, cell).filterNot(s => s.length == length || s.collides(asShip)), sizes - length)
          case _ => this.copy(shipsHit(ships, cell).filterNot(_.collides(asShip)), sizes.updated(length, ofLength - 1))
        }
    }
  }

  def randomShip: Ship = {
    ships(Random.nextInt(ships.size))
  }

  def randomShot: (Int, Int) = {
    val ship = randomShip
    val cells = ship.cells.filter(_._2).keySet
    Random.shuffle(cells).head
  }

  def randomShotContaining(cell: (Int, Int)): (Int, Int) = {
    val packContaining = this.copy(ships.filter(_.cells.contains(cell)))
    val ship = packContaining.randomShip
    val cells = ship.cells.filter(_._2).keySet

    // pick nearest of the cells
    cells.map(c => (Utils.distance(c, cell), c)).minBy(_._1)._2
  }

  override def toString: String = {
    "ShipPack(" + sizes + " | " + ships + ")"
  }

}

object ShipPack {

  val allPossible: ShipPack = {

    val allShips = (for {
      length <- Config.ships.map(_._1).distinct
      x <- 0 until Config.gridSize
      y <- 0 to Config.gridSize - length
    } yield List(Ship.fromCoordinates((x, y), Down, length), Ship.fromCoordinates((y, x), Right, length))).flatten

    val allSizes = Config.ships.map(_._1).groupBy(identity).mapValues(_.length)

    ShipPack(allShips.toVector, allSizes)
  }

}
