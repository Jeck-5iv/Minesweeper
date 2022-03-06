import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import sweeper.Box;
import sweeper.Coordinates;
import sweeper.Game;
import sweeper.GameState;
import sweeper.Ranges;

public class Minesweeper extends JFrame {
  private Game game;
  private JPanel panel;
  private JLabel label;
  private final int IMAGE_SIZE = 50;
  private final int COLUMNS = 10;
  private final int ROWS = 10;
  private final int BOMBS = 10;
  
  public static void main(String[] args) {
    new Minesweeper();
  }

  private Minesweeper(){
    game = new Game(COLUMNS, ROWS, BOMBS);
    game.start();
    setImages();
    initLabel();
    initPanel();
    initFrame();
  }

  private void initFrame() {
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Minesweeper");
    setVisible(true);
    setResizable(false);
    pack();
    setLocationRelativeTo(null);
  }

  private void initLabel() {
    label = new JLabel("Welcome !");
    add(label, BorderLayout.SOUTH);
  }

  private void initPanel() {
    panel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        for (Coordinates coordinates : Ranges.getAllCoordinates()){
          g.drawImage((Image) game.getBox(coordinates).image, coordinates.x * IMAGE_SIZE, coordinates.y * IMAGE_SIZE, this);
        };
      }
    };


    panel.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        int x = e.getX() / IMAGE_SIZE;
        int y = e.getY() / IMAGE_SIZE;
        Coordinates coordinates = new Coordinates(x, y);
        if (e.getButton() == MouseEvent.BUTTON1) {
          game.pressLeftButton(coordinates);
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
          game.pressRightButton(coordinates);
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
          game.start();
        }
        label.setText(getMessage());
        panel.repaint();
      }
    });
    panel.setPreferredSize(new Dimension(IMAGE_SIZE * Ranges.getSize().x, IMAGE_SIZE * Ranges.getSize().y));
    add(panel);
  }

  private String getMessage() {
    switch (game.getState()) {
      case PLAYED : return "Think hard - there are " + game.getBombsLeft() + " mines in game.";
      case BOMBED : return "BOOOOOOOOOOOM. You lost.";
      case WINNER : return "CONGRATULATIONS, YOU WON !!!";
      default: return "Good luck.";
    }
  }

  private void setImages() {
    for (Box box : Box.values()) {
      box.image = getImage(box.name().toLowerCase());
    }
  }

  private Image getImage(String name){
    String filename = "img/" + name + ".png";
    ImageIcon icon = new ImageIcon(getClass().getResource(filename));
    return icon.getImage();
  }
}
