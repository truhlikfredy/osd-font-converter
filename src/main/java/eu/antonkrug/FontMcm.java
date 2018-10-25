package eu.antonkrug;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public class FontMcm extends FontBase {

  private static final Logger LOGGER = LoggerHandler.getLogger(FontMcm.class.getName());


  public FontMcm(Font origin) {
    super(origin);
  }


  public FontMcm(String path) {
    super(path);
  }


  public FontMcm() {
    super();
  }


  @Override
  public boolean load() {
    List<String> lines;
    Path         file = Paths.get(this.path + ".mcm");


    try {
      lines = Files.readAllLines(file);
    }
    catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    if (lines.size() != 16385 || !lines.get(0).equals("MAX7456")) {
      LOGGER.log(Level.SEVERE, "Error: Can't load " + file.toString() + " because not a Max7456 MCM format!");
      return false;
    }

    for (int character = 0; character < MAX_CHARACTERS; character++) {
      for (int height = 0; height < Character.HEIGHT; height++) {
        for (int width = 0; width < (Character.WIDTH /4); width++) {
          final int lineNumber = 1 + character * 64 + height * (Character.WIDTH / 4) + width;
          final String line = lines.get(lineNumber);
          for (int pixel = 0; pixel < 4; pixel++) {
            final String value = line.substring(2*pixel, 2*pixel+2);
            characters[character].pixels[width * 4 + pixel][height] = Color.get(value);
          }
        }
      }

    }

    LOGGER.log(Level.INFO, "Loaded from " + file.toString());

    return true;
  }


  @Override
  public boolean save() {
    return true;
  }


}
