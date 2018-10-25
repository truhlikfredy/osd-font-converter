package eu.antonkrug;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public class FontPng extends FontBase {

  private static final Logger LOGGER = LoggerHandler.getLogger( FontPng.class.getName() );


  public FontPng(Font origin) {
    super(origin);
  }


  public FontPng(String path) {
    super(path);
  }


  public FontPng() {
    super();
  }


  @Override
  public boolean load() {
    return false;
  }


  @Override
  public boolean save() {
    BufferedImage image = new BufferedImage(16 * Character.WIDTH, 16 * Character.HEIGHT,BufferedImage.TYPE_3BYTE_BGR);

    for (int charX = 0; charX < 16; charX++) {
      for (int charY = 0; charY < 16; charY++) {
        for (int x = 0; x < Character.WIDTH; x++) {
          for (int y = 0; y < Character.HEIGHT; y++) {
            final Color value = characters[charX + charY * 16].pixels[x][y];
            image.setRGB(charX * Character.WIDTH + x, charY * Character.HEIGHT + y, value.getRgb());
          }
        }
      }
    }

    File pngFile = new File(path + ".png");

    try {
      ImageIO.write(image, "PNG", pngFile);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    LOGGER.log(Level.INFO, "Saved to " + pngFile.toString());

    return true;
  }


}
