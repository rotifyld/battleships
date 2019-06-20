package battleships.models

//import battleships.utils.Config.{gridSize, ships}
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

  def filter(cell: (Int, Int), shotResult: ShotResult): ShipPack = {

    shotResult match {
      case ShotResult.Miss =>
        this.copy(ships.filterNot(_.cells.keySet.contains(cell)))
      case ShotResult.ShipHit =>
        val asShip = Ship.single(cell)
        this.copy(ships.filterNot(s => s.collides(asShip) && !s.cells.keySet.contains(cell)))
      case ShotResult.ShipSunk(length) =>
        val asShip = Ship.single(cell)
        val ofLength = sizes(length)
        ofLength match {
          case 1 => this.copy(ships.filterNot(s => s.length == length || s.collides(asShip)), sizes - length)
          case _ => this.copy(ships.filterNot(_.collides(asShip)), sizes.updated(length, ofLength - 1))
        }
    }
  }

  def randomShot: (Int, Int) = {
    val ship = ships(Random.nextInt(ships.size))
    val cells = ship.cells.keySet
    Random.shuffle(cells).head
  }

  def randomShotContaining(cell: (Int, Int)): (Int, Int) = {
    this.copy(ships.filter(_.cells.contains(cell))).randomShot
  }

  override def toString: String = {
    "ShipPack(" + sizes + " | " + ships + ")"
  }

}

object ShipPack {

  val allPossible: ShipPack = {

    // todo czy to jakoś ładniej (yield po dwa elementy | do wektora)
    val allShips = (for {
      length <- Config.ships.map(_._1).distinct
      x <- 0 until Config.gridSize
      y <- 0 to Config.gridSize - length
    } yield List(Ship.fromCoordinates((x, y), Down, length), Ship.fromCoordinates((y, x), Right, length))).flatten

    val allSizes = Config.ships.map(_._1).groupBy(identity).mapValues(_.length)

    ShipPack(allShips.toVector, allSizes)
  }

}
