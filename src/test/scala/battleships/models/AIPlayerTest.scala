package battleships.models

import battleships.utils.{Config, Rectangle, ShotResult}
import org.scalatest.{MustMatchers, WordSpec}

class AIPlayerTest extends WordSpec with MustMatchers {

  "AIPlayer" can {

    "create new player with randomly positioned ships" should {

      "position correct ships" in {
        val ai = AIPlayer.withShips
        ai.ships.size must be(Config.ships.size)
        ai.ships.map(_.length).toSet must be(Config.ships.map(_._1).toSet)
      }
    }

    "get next shot coordinates" should {

      "return a previously unhit cell each turn" in {
        for (_ <- 0 until 1000) {
          var ai: Player = AIPlayer.withShips
          var shots = Set[(Int, Int)]()
          for (_ <- 0 until 2) {
            val nextShot = ai.getNextShot
            shots must not contain nextShot
            shots += nextShot
            ai = ai.result(nextShot, ShotResult.Miss)
          }
        }
      }
    }

    "handle shot result" should {

      "on miss: remove all ships overlapping the cell" in {
        val cell = (4, 6)
        val ai = AIPlayer.withShips.result(cell, ShotResult.Miss).asInstanceOf[AIPlayer]
        val expectedShips = AIPlayer.withShips.possibleEnemyShips.ships.filterNot(_.cells.contains(cell))
        ai.possibleEnemyShips.ships must be(expectedShips)
      }

      "on sunk: remove all ships within sunk ship's neighbourhood" in {
        val ai = AIPlayer.withShips
          .result((0, 0), ShotResult.ShipHit)
          .result((1, 0), ShotResult.ShipHit)
          .result((2, 0), ShotResult.ShipSunk(3)).asInstanceOf[AIPlayer]

        val expectedShips = AIPlayer.withShips.possibleEnemyShips.ships
          .filterNot(_.influenceRange.overlaps(Rectangle(0, 3, 0, 1)))

        ai.possibleEnemyShips.ships must be(expectedShips)
      }
    }
  }
}
