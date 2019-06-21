package battleships.io

import battleships.models.Player

object OutputManager {

  def write(str: String): Unit = println(str)

  def promptForShip(p: Player): Unit = {
    write(GridParser.stringify(p, true))
    write("Input ship's head coordinates and direction: (i.e. A1 R(ight), 1a E(ast))")
  }

  def promptForShot(): Unit = write("Input shot coordinates: (i.e. A1, 1a, 1  a)")

  def incorrectCoordinates(): Unit = write("Incorrect coordinates.")

  def outOfBounds(): Unit = write("Ship out of bounds or overlapping another ship.")

}
