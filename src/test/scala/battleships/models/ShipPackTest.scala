package battleships.models

import battleships.utils._
import org.scalatest.{MustMatchers, WordSpec}

class ShipPackTest extends WordSpec with MustMatchers {

  "ShipPack" can {

    "create pack of all ships" should {
      "return all possible ships given by config" in {
        val pack = ShipPack.allPossible

        //there are no duplicates
        pack.ships.distinct must be(pack.ships)

        // all ships are within range
        val cellsSet = pack.ships.flatMap(_.cells.keySet).toSet
        cellsSet.forall(Utils.inRange) must be(true)

        // there are ships of every length defined in Config
        pack.ships.map(_.length).distinct must be(Config.ships.map(_._1).distinct)

        // for each length there is appropriate number of ships
        val lengthToNumber = pack.ships.groupBy(_.length).mapValues(_.size)
        lengthToNumber.forall {
          case (shipLength, shipsOfLength) => shipsOfLength == 2 * (Config.gridSize - shipLength + 1) * Config.gridSize
        } must be(true)
      }
    }

    "filter out overlapping ships" should {
      "decrease number of available ships" in {
        val pack = ShipPack.allPossible.filter(Ship.fromCoordinates((0, 0), Right, 2))
        pack.sizes must not contain 2

        val newPack = pack.filter(Ship.fromCoordinates((0, 2), Right, 3))
        newPack.sizes(3) must be(1)
      }
    }
  }

}