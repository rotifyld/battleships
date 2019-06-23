package battleships.io

import battleships.models.{Player, Ship}
import battleships.utils.Config

import scala.collection.immutable.Seq

object GridParser {

  val columnMarks: Seq[String] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".take(Config.gridSize).map(_.toString)

  private def waterTile(visible: Boolean, hit: Boolean): String = {
    if (!visible && !hit) " "
    else Console.BLUE_B + (if (hit) "~" else " ") + Console.RESET
  }

  private def shipTile(visible: Boolean, hit: Boolean, sunk: Boolean): String = {
    if (!visible && !hit) " "
    else {
      (if (sunk) Console.MAGENTA_B else Console.RED_B) + (if (hit) "X" else "O") + Console.RESET
    }
  }

  def getGrid(player: Player, visible: Boolean): Seq[Seq[String]] = {

    val shipCells = player.ships.flatMap(_.cells).toMap
    for {
      y <- 0 until Config.gridSize
    } yield for {
      x <- 0 until Config.gridSize
    } yield {
      shipCells.get((x, y)) match {
        case Some(isAlive) =>
          val isSunk = player.ships.find(_.cells.contains((x, y))).get.isSunk
          shipTile(visible, !isAlive, isSunk)
        case None => waterTile(visible, player.receivedShots.contains((x, y)))
      }
    }
  }

  def stringify(player: Player, visible: Boolean): String = {
    val rows: Seq[String] = (columnMarks +: getGrid(player, visible) :+ columnMarks).map(_.mkString(" "))

    val lastRow = Config.gridSize + 1
    val zippedWithIdx: Seq[String] = rows.zipWithIndex.map {
      case (row, 0) => "    " + row + "    "
      case (row, `lastRow`) => "    " + row + "    "
      case (row, idx) => f" $idx%2d " + row + f" $idx%2d "
    }

    zippedWithIdx.mkString("\n")
  }

  def stringify(playerL: Player, visibleL: Boolean, playerR: Player, visibleR: Boolean): String = {
    val gridL = stringify(playerL, visibleL)
    val gridR = stringify(playerR, visibleR)
    gridL.split("\n").zip(gridR.split("\n")).map {
      case (left, right) => left + (right drop 4)
    }.mkString("\n")
  }

}
