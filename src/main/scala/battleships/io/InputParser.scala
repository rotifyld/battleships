package battleships.io

import battleships.models.Ship
import battleships.utils.{Direction, Up, Down, Right, Left}

object InputParser {

  private val rowColRegex = """^\s*(\d+)\s*([a-z])\s*$""".r
  private val colRowRegex = """^\s*([a-z])\s*(\d+)\s*$""".r
  private val upRegex = """^\s*([un]|up|north)\s*$""".r
  private val downRegex = """^\s*([ds]|down|south)\s*$""".r
  private val leftRegex = """^\s*([lw]|left|west)\s*$""".r
  private val rightRegex = """^\s*([re]|right|east)\s*$""".r

  private def colIdToInt(str: String): Int = {
    GridParser.columnMarks.indexOf(str.toUpperCase)
  }

  def getCoordinates(): Option[(Int, Int)] = {
    io.StdIn.readLine().toLowerCase match {
      case rowColRegex(row, col) => Some(colIdToInt(col), row.toInt - 1)
      case colRowRegex(col, row) => Some(colIdToInt(col), row.toInt - 1)
      case _ => None
    }
  }

  def getDirection(): Option[Direction] = {
    io.StdIn.readLine().toLowerCase match {
      case upRegex(_) => Some(Up)
      case downRegex(_) => Some(Down)
      case leftRegex(_) => Some(Left)
      case rightRegex(_) => Some(Right)
      case _ => None
    }
  }

  def getShip(length: Int): Option[Ship] = {
    for {
      coords <- getCoordinates()
      dir <- getDirection()
    } yield Ship.fromCoordinates(coords, dir, length)
  }


}
