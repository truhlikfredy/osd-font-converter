package eu.antonkrug;

import eu.antonkrug.Character;
import eu.antonkrug.Font;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public class FontBase implements Font {
  protected Character[] characters;


  public FontBase() {
    characters = new Character[MAX_CHARACTERS];
    for (int i = 0 ; i < MAX_CHARACTERS; i++) {
      characters[i] = new Character();
    }
  }

  @Override
  public boolean load() {
    return false;
  }


  @Override
  public boolean save() {
    return false;
  }


  public void outline() {
    for (int i = 0; i < Font.MAX_CHARACTERS; i++) {
      for (int x = 0; x < Character.WIDTH; x++) {
        for (int y = 0; y < Character.HEIGHT; y++) {
          if (characters[i].bits[x][y] == 1) {
            for (int col = x-2; col <= x+2; col++) {
              for (int row = y -2; row <= y+2; row++) {
                if (characters[i].getBit(col, row) == -1) {
                  characters[i].setBit(col, row, 0);
                }
              }
            }
          }
        }
      }
    }
  }


  public boolean snapshotPng(String path) {
    BufferedImage image = new BufferedImage(16 * Character.WIDTH, 16 * Character.HEIGHT,BufferedImage.TYPE_3BYTE_BGR);

    for (int charX = 0; charX < 16; charX++) {
      for (int charY = 0; charY < 16; charY++) {
        for (int x = 0; x < Character.WIDTH; x++) {
          for (int y = 0; y < Character.HEIGHT; y++) {
            final int value = characters[charX + charY * 16].bits[x][y];
            int rgb = 0;
            switch (value) {
              case 0:
                rgb = 0x000000;
                break;

              case 1:
                rgb = 0xffffff;
                break;

              case -1:
                rgb = 0x999999;
                break;
            }

            image.setRGB(charX * Character.WIDTH + x, charY * Character.HEIGHT + y, rgb);
          }
        }
      }
    }

    File myNewPNGFile = new File(path);

    try {
      ImageIO.write(image, "PNG", myNewPNGFile);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Saved to " + path);

    return true;
  }


}
