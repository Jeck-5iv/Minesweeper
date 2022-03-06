package sweeper;

public class Game {
  private Bomb bomb;
  private Flag flag;
  private GameState state;

  public GameState getState() {
    return state;
  }

  public Game(int columns, int rows, int bombs){
    Ranges.setSize(new Coordinates(columns, rows));
    bomb = new Bomb(bombs);
    flag = new Flag();
  }

  public void start(){
    bomb.start();
    flag.start();
    state = GameState.PLAYED;
  }

  public Box getBox(Coordinates coordinates){
    if (flag.get(coordinates) == Box.OPENED){
      return bomb.get(coordinates);
    }
    return flag.get(coordinates);
  }

  public void pressLeftButton(Coordinates coordinates) {
    if (gameOver()) return;
    openBox(coordinates);
    checkWinner();
  }

  private void checkWinner() {
    if (state == GameState.PLAYED) {
      if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs()) {
        state = GameState.WINNER;
      }
    }
  }

  private void openBox(Coordinates coordinates) {
    switch (flag.get(coordinates)) {
      case OPENED : setOpenedToCloseBoxesAroundNumber(coordinates); return;
      case FLAGGED : return;
      case CLOSED :
        switch (bomb.get(coordinates)) {
          case ZERO : openBoxesAround(coordinates); return;
          case BOMB : openBombs(coordinates); return;
          default : flag.setOpenedToBox(coordinates); return;

        }
    }
  }

  private void openBombs(Coordinates boomCoordinates) {
    state = GameState.BOMBED;
    flag.setBombedToBox(boomCoordinates);
    for (Coordinates coordinates : Ranges.getAllCoordinates()) {
      if (bomb.get(coordinates) == Box.BOMB) {
        flag.setOpenedToClosedBomb(coordinates);
      } else {
        flag.setNoBombOnFlaggedBox(coordinates);
      }
    }
  }

  private void openBoxesAround(Coordinates coordinates) {
    flag.setOpenedToBox(coordinates);
    for (Coordinates around : Ranges.getCoordinatesAround(coordinates)) {
      openBox(around);
    }
  }

  public void pressRightButton(Coordinates coordinates) {
    if (gameOver()) return;
    flag.toggleFlaggedBox(coordinates);
  }

  private boolean gameOver() {
    if (state == GameState.PLAYED) return false;
    start();
    return true;
  }

  private void setOpenedToCloseBoxesAroundNumber(Coordinates coordinates) {
    if (bomb.get(coordinates) != Box.BOMB) {
      if (flag.getCountOfFlaggedBoxesAround(coordinates) == bomb.get(coordinates).getNumber()) {
        for (Coordinates around : Ranges.getCoordinatesAround(coordinates)) {
          if ((flag.get (around)) == Box.CLOSED) {
            openBox(around);
          }
        }
      }
    }
  }
}
