package battleships.utils

import org.scalatest.{MustMatchers, WordSpec}

class UtilsTest extends WordSpec with MustMatchers {

  "Utils" can {
    "measure distance between two cells" should {
      "return distance if cells in same row or column" in {
        Utils.distance((0, 0), (5, 0)) must be(5)
        Utils.distance((0, 0), (0, 5)) must be(5)
      }

      "throw exception if cells not in same row or column" in {
        an[Exception] should be thrownBy { Utils.distance((0, 1), (2, 3)) }
      }
    }

    "test if cell is in range" should {
      "return true" in {
        Utils.inRange((0, 0)) must be(true)
        Utils.inRange((0, Config.gridSize - 1)) must be(true)
      }

      "return false" in {
        Utils.inRange((0, Config.gridSize)) must be(false)
        Utils.inRange((-1, 0)) must be(false)
      }
    }
  }

  "Rectangle" can {
    val r1 = Rectangle(0, 0, 0, 5)
    val r2 = Rectangle(1, 1, 0, 2)
    val r3 = Rectangle(-2, 2, 3, 3)

    "be tested for overlap" should {
      "return true" in {
        r1 overlaps r3 must be(true)
      }

      "return false" in {
        r1 overlaps r2 must be(false)
        r3 overlaps r2 must be(false)
      }
    }
  }

  "Direction" can {
    "translate cell over given distance" should {
      "return translated cell" in {
        Up.translate((0, 0), 0) must be ((0, 0))
        Down.translate((0, 0), 2) must be ((0, 2))
        Right.translate((0, -2), -4) must be ((-4, -2))
      }
    }
  }

}
