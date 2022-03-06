package sweeper;

public class Flag {
  private Matrix flagMap;
  private int countOfClosedBoxes;

  void start(){
    flagMap = new Matrix(Box.CLOSED);
    countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
  }

  Box get(Coordinates coordinates) {
    return flagMap.get(coordinates);
  }

  public void setOpenedToBox(Coordinates coordinates) {
    flagMap.set(coordinates, Box.OPENED);
    countOfClosedBoxes--;
  }

  public void toggleFlaggedBox(Coordinates coordinates) {
    switch (flagMap.get(coordinates)) {
      case CLOSED -> setFlaggedToBox(coordinates);
      case FLAGGED -> setClosedToBox(coordinates);
    }
  }

  public void setFlaggedToBox(Coordinates coordinates) {
    flagMap.set(coordinates, Box.FLAGGED);
  }

  public void setClosedToBox(Coordinates coordinates) {
    flagMap.set(coordinates, Box.CLOSED);
  }

  public int getCountOfClosedBoxes() {
    return countOfClosedBoxes;
  }

  public void setBombedToBox(Coordinates coordinates) {
    flagMap.set(coordinates, Box.BOMBED);
  }

  public void setOpenedToClosedBomb(Coordinates coordinates) {
    if (flagMap.get(coordinates) == Box.CLOSED) {
      flagMap.set(coordinates, Box.OPENED);
    }
  }

  public void setNoBombOnFlaggedBox(Coordinates coordinates) {
    if (flagMap.get(coordinates) == Box.FLAGGED) {
      flagMap.set(coordinates, Box.NOBOMB);
    }
  }

   public int getCountOfFlaggedBoxesAround(Coordinates coordinates) {
    int count = 0;
    for (Coordinates around : Ranges.getCoordinatesAround(coordinates)) {
      if (flagMap.get(around) == Box.FLAGGED) {
        count++;
      }
    }
    return count;
  }
}


