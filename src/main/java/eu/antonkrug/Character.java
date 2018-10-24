package eu.antonkrug;

/**
 * @author Anton Krug on 23/10/18
 * @version v0.1
 */
public class Character {
  public static int WIDTH  = 12;
  public static int HEIGHT = 18;

  public Color[][] pixels;


  public Character() {
    pixels = new Color[WIDTH][HEIGHT];
  }


  public Character(Character origin) {
    pixels = new Color[WIDTH][HEIGHT];
    for (int x = 0; x < WIDTH; x++) {
      for (int y = 0; y < HEIGHT; y++) {
        pixels[x][y] = origin.pixels[x][y];
      }
    }
  }


  public void setBit(int x, int y, Color val) {
    if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
      pixels[x][y] = val;
    }
  }


  public Color getBit(int x, int y) {
    if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
      return pixels[x][y];
    }
    else {
      return Color.OUT_OF_BOUNDARIES;
    }
  }


}
