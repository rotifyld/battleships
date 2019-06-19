package battleships.models

import org.scalatest.{MustMatchers, WordSpec}

import battleships.utils._

class ModelsSuite extends WordSpec with MustMatchers {

  val pack = ShipPack.allPossible
  val l = Config.ships.head._1
  val lMax = Config.ships.map(_._1).max

  val s1 = Ship.fromCoordinates((0, 0), Right, 2)
  val s2 = Ship.fromCoordinates((0, 0), Right, l)
  val s3 = Ship.fromCoordinates((0, 0), Down, l)
  val s4 = Ship.fromCoordinates((0, 1), Right, l)
  val sLong = Ship.fromCoordinates((lMax - 1, 0), Left, lMax)

  "Ship" can {
    "create object from direction" should {
      "throw exception for out of bound calls" in {
        intercept[IndexOutOfBoundsException] { Ship.fromCoordinates((0, 0), Up, 3) }
      }

      "return correct ship" in {
        s1.cells mustBe Map((0, 0) -> true, (1, 0) -> true)
        s1.influenceRange mustBe Utils.Rectangle(-1, 2, -1, 1)
      }
    }

  }

  "ShipPack" can {
    "create list of all possible ships" in {
      assert(pack.allShips.contains(s2))
      assert(pack.allShips.contains(s4))
      assert(pack.allShips.contains(sLong))
    }

    "exclude ships with overlapping influence zones" in {
      val filteredPack = pack.filter(Ship.fromCoordinates((0, 0), Right, 1))
      assert(pack !== filteredPack)
      assert(!filteredPack.allShips.contains(s4))
    }

    "exclude ships via hit" in {
      val filteredPack = pack.filter((0, 0))
      assert(pack !== filteredPack)
      assert(filteredPack.allShips.contains(s1))
      assert(filteredPack.allShips.contains(s2))
      assert(filteredPack.allShips.contains(s3))
      assert(!filteredPack.allShips.contains(s4))
      val doublyFilteredPack = pack.filter((1, 0))
      assert(doublyFilteredPack !== filteredPack)
      assert(doublyFilteredPack.allShips.contains(s1))
      assert(doublyFilteredPack.allShips.contains(s2))
      assert(!doublyFilteredPack.allShips.contains(s3))
      assert(!doublyFilteredPack.allShips.contains(s4))
    }
  }

}