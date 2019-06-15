package battleships.models

import org.scalatest.FunSuite

//import battleships.models.Ship._
import battleships.utils._

class ModelsSuite extends FunSuite {

  trait TestModels {
    val s1 = Ship.fromCoordinates((0, 0), Right, 2)
  }

  test("creation ship from direction") {
    new TestModels {
      assert(s1.cells === Map((0, 0) -> true, (1, 0) -> true))
    }
  }

  test("creating ship from direction - invalid calls") {
    intercept[Error] {
      Ship.fromCoordinates((0, 0), Up, 5)
    }
    intercept[Error] {
      Ship.fromCoordinates((0, 0), Down, -5)
    }
  }

}