import battleships.models.Ship
import battleships.utils._

Up.opposite

Ship.fromCoordinates((0, 0), Down, 3)
Ship.fromCoordinates((0, 0), Up, 3)
Ship.fromCoordinates((0, 0), Left, 3)
Ship.fromCoordinates((0, 0), Right, 3)
Ship(Map((0, 0) -> true))

val coords = List((1, 1), (1, 2), (1, 3))
coords.map(_ -> 10).toMap

Ship.empty