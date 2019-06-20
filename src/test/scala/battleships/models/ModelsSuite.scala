package battleships.models

import org.scalatest.{MustMatchers, WordSpec}

import battleships.utils._

class ModelsSuite extends WordSpec with MustMatchers {

  val pack = ShipPack.allPossible
  val l1 = Config.ships.head._1
  val l2 = Config.ships.tail.head._1
  val lMax = Config.ships.map(_._1).max

  val s1 = Ship.fromCoordinates((0, 0), Right, 2)
  val s2 = Ship.fromCoordinates((0, 0), Right, l1)
  val s3 = Ship.fromCoordinates((0, 0), Down, l1)
  val s4 = Ship.fromCoordinates((0, 1), Right, l1)
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
      assert(pack.ships.contains(s2))
      assert(pack.ships.contains(s4))
      assert(pack.ships.contains(sLong))
    }

    "exclude ships with overlapping influence zones" in {
      val filteredPack = pack.filter(Ship.fromCoordinates((0, 0), Right, l1))
      assert(pack !== filteredPack)
      assert(!filteredPack.ships.contains(s4))
    }

    "exclude ships via shot" should {
      "missed" in {
        val filteredPack = pack.filter((0, 0), ShotResult.Miss)
        assert(!filteredPack.ships.contains(s2))
        assert(!filteredPack.ships.contains(s3))
        assert(filteredPack.ships.contains(s4))
      }

      "hit" in {
        val filteredPack = pack.filter((1, 0), ShotResult.ShipHit)
        assert(filteredPack.ships.contains(s2))
        assert(!filteredPack.ships.contains(s3))
        assert(!filteredPack.ships.contains(s4))
      }

      "destroy" in {
        val filteredPack = pack.filter((Config.gridSize - 1, Config.gridSize - 1), ShotResult.ShipSunk(l2))
        assert(filteredPack.ships.contains(s2))
        assert(filteredPack.ships.contains(s3))
        assert(filteredPack.ships.contains(s4))
        assert(filteredPack.ships.forall(_.length != l2))
      }
    }
  }

}