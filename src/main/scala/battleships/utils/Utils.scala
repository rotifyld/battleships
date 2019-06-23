package battleships.utils

import battleships.models.Ship

object Utils {

  def distance(c1: (Int, Int), c2: (Int, Int)): Int = {
    require(c1._1 == c2._1 || c1._2 == c2._2)
    (c1._1 - c2._1 + c1._2 - c2._2).abs
  }

  def inRange(cell: (Int, Int)): Boolean = {
    0 <= cell._1 && cell._1 < Config.gridSize &&
      0 <= cell._2 && cell._2 < Config.gridSize
  }

}
