package battleships.models

import battleships.utils._
import org.scalatest.{MustMatchers, WordSpec}

class ShipTest extends WordSpec with MustMatchers {

  "Ship" can {

    "be created" should {
      "return correct ship" in {
        val s = Ship.fromCoordinates((0, 0), Right, 2)
        s.length must be(2)
        s.cells must be(Map((0, 0) -> true, (1, 0) -> true))
        s.influenceRange must be(Rectangle(-1, 2, -1, 1))
      }
    }

    "be hit" should {
      "mark shot" in {
        val s = Ship.fromCoordinates((0, 0), Right, 2).hit((0, 0))

        s.cells must be(Map((0, 0) -> false, (1, 0) -> true))
        s.isSunk must be(false)
      }

      "not change at out of bounds shots" in {
        val s = Ship.fromCoordinates((0, 0), Right, 2)

        s.hit((2, 2)) must be(s)
        s.hit((-1, -1000)) must be(s)
      }
    }

    "checked for collision" should {
      val s1 = Ship.fromCoordinates((0, 0), Right, 3)
      val s2 = Ship.fromCoordinates((1, 2), Down, 5)
      val s3 = Ship.fromCoordinates((1, 1), Left, 1)

      "return true" in {
        s1.collides(s3) must be(true)
        s2.collides(s3) must be(true)
      }

      "return false" in {
        s1.collides(s2) must be(false)
      }
    }

  }

}
