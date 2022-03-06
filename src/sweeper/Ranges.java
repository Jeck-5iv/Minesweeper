package sweeper;

import java.util.ArrayList;
import java.util.Random;

public class Ranges {

  private static Coordinates size;
  private static ArrayList<Coordinates> allCoordinates;
  private static Random random = new Random();

  static void setSize(Coordinates _size){
    size = _size;
    allCoordinates = new ArrayList<Coordinates>();
    for (int x = 0; x < size.x; x++){
      for (int y = 0; y < size.y; y++){
        allCoordinates.add(new Coordinates(x, y));
      }
    }
  }

  public static Coordinates getSize() {
    return size;
  }

  public static ArrayList<Coordinates> getAllCoordinates() {
    return allCoordinates;
  }

  static boolean inRange(Coordinates coordinates){
    return 0 <= coordinates.x && coordinates.x < size.x &&
        0 <= coordinates.y && coordinates.y < size.y;
  }

  static Coordinates getRandomCoordinate() {
    return new Coordinates(random.nextInt(size.x), random.nextInt(size.y));
  }

  static ArrayList<Coordinates> getCoordinatesAround(Coordinates center) {
    Coordinates around;
    ArrayList<Coordinates> list = new ArrayList<Coordinates>();
    for (int x = center.x - 1; x <= center.x + 1; x++) {
      for (int y = center.y - 1; y <= center.y + 1; y++) {
        if (inRange(around = new Coordinates(x, y)) && !around.equals(center)){
          list.add(around);
        }
      }
    }
    return list;
  }
}
