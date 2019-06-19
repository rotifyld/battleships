package battleships.utils

import battleships.utils.Utils.Rectangle
import org.scalatest.{MustMatchers, WordSpec}

class UtilsSuite extends WordSpec with MustMatchers {

  "Rectangle" can {
    val r1 = Rectangle(0, 0, 0, 5)
    val r2 = Rectangle(1, 1, 0, 2)
    val r3 = Rectangle(-2, 2, 3, 3)

    "be tested for overlap" should {
      "return true if overlaps" in {
        r1 overlaps r3 mustBe true
      }

      "return false if does not overlap" in {
        r1 overlaps r2 mustBe false
        r3 overlaps r2 mustBe false
      }
    }
  }

}
