package eu.antonkrug;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public class Converter {
  public static void main(String[] args) {
    System.out.println("Started");

    Font font = new FontMCM("/home/fredy/Desktop/a.mcm");
    font.load();
    font.outline();
    font.snapshotPng("/home/fredy/Desktop/zzzzzzz.png");
  }
}
