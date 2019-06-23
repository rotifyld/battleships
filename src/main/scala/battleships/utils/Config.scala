package battleships.utils

import scala.util.Random

object Config {

  val gridSize = 10

  val ships = List(
    (5, "Carrier"),
    (4, "Battleship"),
    (3, "Cruiser"),
    (3, "Submarine"),
    (2, "Destroyer")
  )

  val seed: Long = Random.nextLong
}
