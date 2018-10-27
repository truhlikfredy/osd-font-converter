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
    BufferedImage image;
    try {
      image = ImageIO.read(ResourcesHandler.getStreamFromFilename("fonts", path, extension));

    }
    catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    if (image.getWidth() != Character.WIDTH * 16 || image.getHeight() != Character.HEIGHT * 16) {
      LOGGER.log(Level.SEVERE, "Image " + getPathWithExtension() + " has wrong resolution to be font. Expected " +
                               Character.WIDTH * 16 + "x" + Character.HEIGHT * 16 + " image resolution.");
      return false;
    }

    if (image.getType() != BufferedImage.TYPE_3BYTE_BGR) {
      LOGGER.log(Level.SEVERE, "This image format (" + image.getType() +
                               ") is not supported yet. Expected 3Byte BGR format.");
      return false;
    }

    for (int charX = 0; charX < 16; charX++) {
      for (int charY = 0; charY < 16; charY++) {

        final int characterIndex = charX + charY * 16;
        for (int x = 0; x < Character.WIDTH; x++) {
          for (int y = 0; y < Character.HEIGHT; y++) {
            final int absoluteX = charX * Character.WIDTH + x;
            final int absoluteY = charY * Character.HEIGHT + y;
            final int rgb = image.getRGB(absoluteX, absoluteY) & 0x00ffffff;
            final Color value = Color.get(rgb);
            characters[characterIndex].pixels[x][y] = value;
          }
        }

      }
    }

    return true;
  }


  @Override
  public boolean save() {
    BufferedImage image = new BufferedImage(16 * Character.WIDTH, 16 * Character.HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

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

    File pngFile = new File(getPathWithExtension());

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
