package battleships.utils

sealed trait ShotResult

object ShotResult {

  case object Miss extends ShotResult

  case object ShipHit extends ShotResult

  case class ShipSunk(length: Int) extends ShotResult

}
