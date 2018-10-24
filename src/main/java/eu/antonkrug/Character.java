package eu.antonkrug;

/**
 * @author Anton Krug on 23/10/18
 * @version v0.1
 */
public class Character {
  public static int WIDTH  = 12;
  public static int HEIGHT = 18;

  public int[][] bits;

  public Character() {
    this.bits = new int[WIDTH][HEIGHT];
  }


  public void setBit(int x, int y, int val) {
    if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
      bits[x][y] = val;
    }
  }


  public int getBit(int x, int y) {
    if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
      return bits[x][y];
    }
    else {
      return -2;
    }
  }


}
