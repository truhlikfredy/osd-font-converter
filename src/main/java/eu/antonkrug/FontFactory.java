package eu.antonkrug;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
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

  private static final Map<String, Function<String, Font>> EXTENSION_MAP;

  static {
    Map<String, Function<String, Font>> map = new ConcurrentHashMap<>();

    // return is implied in this case, and function is consumer and supplier in one
    // https://stackoverflow.com/questions/38540481/return-value-by-lambda-in-java
    // https://stackoverflow.com/questions/31251629/java-8-supplier-with-arguments-in-the-constructor
    map.put("mcm", path -> new FontMcm(path));
    map.put("png", path -> new FontPng(path));

    // similar topics:
    // https://dzone.com/articles/4-modules-of-spring-architecture
    // https://stackoverflow.com/questions/28417262/java-8-supplier-consumer-explanation-for-the-layperson

    // Making the map unmodifiable when assigning to a "final" variable
    // https://stackoverflow.com/questions/3999086/when-is-the-unmodifiablemap-really-necessary
    EXTENSION_MAP = Collections.unmodifiableMap(map);
  }


  public static Font getInstance(String fullPath) {
    final String path      = removeExtension(fullPath);
    final String extension = getExtension(fullPath).toLowerCase();

    LOGGER.log(Level.FINEST, "Fullpath=" + fullPath + " path=" + path + " extension=" + extension);

    if (EXTENSION_MAP.containsKey(extension)) {
      return EXTENSION_MAP.get(extension).apply(fullPath);
    }
    else{
      LOGGER.log(Level.SEVERE, "Extension " + extension + " (taken from " + fullPath + ") is not supported as font format.");
      return null;
    }
  }


}
