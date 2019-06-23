package battleships.utils


sealed abstract class Direction(val dx: Int, val dy: Int) {
  def translate(cell: (Int, Int), dist: Int): (Int, Int) = cell match {
    case (x, y) => (x + (dx * dist), y + (dy * dist))
  }

  def opposite: Direction = this match {
    case Left => Right
    case Right => Left
    case Up => Down
    case Down => Up
  }
}

case object Left extends Direction(-1, 0)

case object Right extends Direction(1, 0)

case object Up extends Direction(0, -1)

case object Down extends Direction(0, 1)