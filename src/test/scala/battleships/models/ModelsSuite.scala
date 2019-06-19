package battleships.models

import org.scalatest.{MustMatchers, WordSpec}

//import battleships.models.Ship._
import battleships.utils._

class ModelsSuite extends WordSpec with MustMatchers {

  "Ship" can {
    "create object from direction" should {
      "throw exception for out of bound calls" in {
        intercept[IndexOutOfBoundsException] { Ship.fromCoordinates((0, 0), Up, 3) }
        intercept[IndexOutOfBoundsException] { Ship.fromCoordinates((0, Config.gridSize - 1), Up, Config.gridSize) }
      }

      "return correct ship" in {
        Ship.fromCoordinates((0, 0), Right, 2).cells mustBe Map((0, 0) -> true, (1, 0) -> true)
      }
    }

  }

}