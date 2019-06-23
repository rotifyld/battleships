package battleships.io

import battleships.models.{Ship, ShipPack}
import battleships.utils.{Direction, Down, Left, Right, Up, Utils}

object ConsoleIO {

  private val rowColRegex = """^\s*(\d+)\s*([a-z])\s*$""".r
  private val colRowRegex = """^\s*([a-z])\s*(\d+)\s*$""".r
  private val upRegex = """^\s*([un]|up|north)\s*$""".r
  private val downRegex = """^\s*([ds]|down|south)\s*$""".r
  private val leftRegex = """^\s*([lw]|left|west)\s*$""".r
  private val rightRegex = """^\s*([re]|right|east)\s*$""".r

  private def colIdToInt(str: String): Int = {
    GridParser.columnMarks.indexOf(str.toUpperCase)
  }

  private def readCoordinates: (Int, Int) = {
    io.StdIn.readLine().toLowerCase match {
      case rowColRegex(row, col) => (colIdToInt(col), row.toInt - 1)
      case colRowRegex(col, row) => (colIdToInt(col), row.toInt - 1)
      case _ =>
        write("Invalid coordinates format.")
        readCoordinates
    }
  }

  private def readDirection: Direction = {
    io.StdIn.readLine().toLowerCase match {
      case upRegex(_) => Up
      case downRegex(_) => Down
      case leftRegex(_) => Left
      case rightRegex(_) => Right
      case _ =>
        write("Invalid direction format.")
        readDirection
    }
  }

  def write(str: String): Unit = println(str)

  def writeErr(str: String): Unit = write(Console.RED + str + Console.RESET)

  def getShot: (Int, Int) = {
    write("Input shot coordinates: (i.e. A1, 1a)")
    val cell = readCoordinates
    if (!Utils.inRange(cell)) {
      writeErr("Coordinates not in range.")
      return getShot
    }
    cell
  }

  def getShip(length: Int, possibleShips: ShipPack): Ship = {

    write(s"Ship of length $length")
    write("Input ship head coordinates: (i.e. A1, 1a)")
    val head = readCoordinates
    if (!Utils.inRange(head)) {
      writeErr("Ship head not in range.")
      return getShip(length, possibleShips)
    }

    write("Input ship direction: (i.e. r, Right, E, east)")
    val dir = readDirection
    if (!Utils.inRange(dir.translate(head, length - 1))) {
      writeErr("Ship tail not in range.")
      return getShip(length, possibleShips)
    }

    val ship = Ship.fromCoordinates(head, dir, length)
    if (!possibleShips.ships.contains(ship)) {
      writeErr("Ship overlapping already existing one.")
      return getShip(length, possibleShips)
    }

    ship
  }

}
