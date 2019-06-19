package battleships.utils

object Utils {

  def inRange(cell: (Int, Int)): Boolean = {
    0 <= cell._1 && cell._1 < Config.gridSize &&
    0 <= cell._2 && cell._2 < Config.gridSize
  }

}
