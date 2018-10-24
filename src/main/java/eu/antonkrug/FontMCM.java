package eu.antonkrug;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public class FontMCM extends FontBase {

  private String path;

  public FontMCM(String path) {
    super();
    this.path = path;
  }

  public boolean load() {
    List<String> lines;

    try {
      lines = Files.readAllLines(Paths.get(path));
    }
    catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    if (lines.size() != 16385 || !lines.get(0).equals("MAX7456")) {
      System.out.println("Not a Max7456 MCM format!");
      return false;
    }

    System.out.println(lines.size());
    for (int character = 0; character < MAX_CHARACTERS; character++) {
      for (int height = 0; height < Character.HEIGHT; height++) {
        for (int width = 0; width < (Character.WIDTH /4); width++) {
          final int lineNumber = 1 + character * 64 + height * (Character.WIDTH / 4) + width;
          final String line = lines.get(lineNumber);
//        System.out.println(lineNumber);
          for (int pixel = 0; pixel < 4; pixel++) {
            final String value = line.substring(2*pixel, 2*pixel+2);
            final int valueInt = Integer.parseInt(value);

            int finalValue = 0;
            switch (valueInt) {
              case 00:
                finalValue = 0;
                break;

              case 10:
                finalValue = 1;
                break;

              case 11:
                finalValue = -1;
                break;

              case 01:
                finalValue = -1;
                break;
            }
            characters[character].bits[width*4 + pixel][height] = finalValue;

          }
        }
      }

    }
    return true;
  }


  public boolean save() {
    List<String> lines;

    try {
      lines = Files.readAllLines(Paths.get(path));
    }
    catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    if (lines.size() != 16385 || !lines.get(0).equals("MAX7456")) {
      System.out.println("Not a Max7456 MCM format!");
      return false;
    }

    System.out.println(lines.size());
    for (int character = 0; character < MAX_CHARACTERS; character++) {
      for (int height = 0; height < Character.HEIGHT; height++) {
        for (int width = 0; width < (Character.WIDTH /4); width++) {
          final int lineNumber = 1 + character * 64 + height * (Character.WIDTH / 4) + width;
          final String line = lines.get(lineNumber);
          //        System.out.println(lineNumber);
          for (int pixel = 0; pixel < 4; pixel++) {
            final String value = line.substring(2*pixel, 2*pixel+2);
            final int valueInt = Integer.parseInt(value);

            int finalValue = 0;
            switch (valueInt) {
              case 00:
                finalValue = 0;
                break;

              case 10:
                finalValue = 1;
                break;

              case 11:
                finalValue = -1;
                break;

              case 01:
                finalValue = -1;
                break;
            }
            characters[character].bits[width*4 + pixel][height] = finalValue;

          }
        }
      }

    }
    return true;
  }


}
