package eu.antonkrug;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.io.FilenameUtils.removeExtension;

/**
 * @author Anton Krug on 25/10/18
 * @version v0.1
 */
public class FontFactory {

  private static final Logger LOGGER = LoggerHandler.getLogger(FontFactory.class.getName());


  public static Font getInstance(String fullPath) {
    final String path      = removeExtension(fullPath);
    final String extension = getExtension(fullPath);

    LOGGER.log(Level.FINEST, "Fullpath=" + fullPath + " path=" + path + " extension=" + extension);
    switch (extension) {
      case "mcm":
        return new FontMcm(fullPath);

      case "png":
        return new FontPng(fullPath);

      default:
        LOGGER.log(Level.SEVERE, "Extension " + extension + " (taken from " + fullPath + ") is not supported as font format.");
        return null;
    }
  }


}
