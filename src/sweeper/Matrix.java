package sweeper;

class Matrix {
  private Box[][] matrix;

  Matrix(Box defaultValue) {
    matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
    for (Coordinates coordinates : Ranges.getAllCoordinates()) {
      matrix[coordinates.x][coordinates.y] = defaultValue;
    }
  }

  Box get(Coordinates coordinates) {
    if (Ranges.inRange(coordinates)) {
      return matrix[coordinates.x][coordinates.y];
    } else {
      return null;
    }
  }

  void set(Coordinates coordinates, Box box) {
    if (Ranges.inRange(coordinates)) {
      matrix[coordinates.x][coordinates.y] = box;
    }
  }
}
