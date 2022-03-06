package sweeper;

class Bomb {
  private Matrix bombMap;
  private int totalBombs;

  Bomb(int _totalBombs) {
    this.totalBombs = _totalBombs;
    fixMaxBombCount();
  }

  void start() {
    bombMap = new Matrix(Box.ZERO);
    for (int i = 0; i < totalBombs; i++){
      placeBomb();
    }
  }

  Box get(Coordinates coordinates) {
    return bombMap.get(coordinates);
  }

  private void fixMaxBombCount() {
    int maxBombCount = Ranges.getSize().x * Ranges.getSize().y;
    if (totalBombs > maxBombCount) {
      totalBombs = maxBombCount;
    }
  }

  private void placeBomb() {
    while (true) {
      Coordinates bombCoordinates = Ranges.getRandomCoordinate();
      if (bombMap.get(bombCoordinates) == Box.BOMB) {
        continue;
      }
      bombMap.set(bombCoordinates, Box.BOMB);
      increaseNumbersAroundBomb(bombCoordinates);
      break;
    }
  }

  private void increaseNumbersAroundBomb(Coordinates bombCoordinates) {
    for (Coordinates around : Ranges.getCoordinatesAround(bombCoordinates)){
      if (bombMap.get(around) != Box.BOMB){
        bombMap.set(around, bombMap.get(around).getNextNumberBox());
      }
    }
  }

  public int getTotalBombs() {
    return totalBombs;
  }
}
