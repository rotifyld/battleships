package battleships.utils

case class Rectangle(left: Int, right: Int, up: Int, down: Int) {
  def overlaps(other: Rectangle): Boolean = {
    this.left < other.right && this.right > other.left &&
      this.up < other.down && this.down > other.up
  }
}

object Rectangle {
  def single(cell: (Int, Int)) = Rectangle(cell._1, cell._1, cell._2, cell._2)

  def extended(start: (Int, Int), end: (Int, Int)): Rectangle = {
    if (start._1 > end._1 || start._2 > end._2)
      extended(end, start)
    else
      Rectangle(start._1 - 1, end._1 + 1, start._2 - 1, end._2 + 1)
  }
}
