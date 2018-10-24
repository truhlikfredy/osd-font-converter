package eu.antonkrug;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public class Converter {


  public static void main(String[] args) throws Exception {
    System.out.println("Converter started...");

    Font font = new FontMcm("/home/fredy/Desktop/a");
    font.load();
    font.outline();

    Font fontPng = new FontPng(font);
    fontPng.save();
  }


}
