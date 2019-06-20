package battleships.models

import battleships.io.GridParser
import battleships.utils.Config
import org.scalatest.{MustMatchers, WordSpec}

class AIPlayerSuite extends WordSpec with MustMatchers {

  "AIPlayer" can {

    "place ships randomly" in {
      val ai = AIPlayer.withShips
      assert(ai.ships.size === Config.ships.size)
      assert(ai.ships.map(_.length).toSet === Config.ships.map(_._1).toSet)
    }

    "prints situation including hit water/ships" in {
      val ai = (for {
        i <- 0 until Config.gridSize
      } yield List((i, 7), (4, i))).flatten.foldRight[Player](AIPlayer.withShips) {
        case (cell, p) => p.receiveShot(cell)._1
      }

      println(GridParser.stringify(ai, true, ai, false))
    }
  }

}
