package eu.antonkrug;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.io.FilenameUtils.removeExtension;

/**
 * @author Anton Krug on 25/10/18
 * @version v0.1
 */
public class ResourcesHandler {

  private static final Logger LOGGER = LoggerHandler.getLogger(ResourcesHandler.class.getName());
  private static final String RESOURCE_PREFIX = "resource:/";


  static public List<String> getAllFilters() {
    List<String> list = getAllResource("filters");
    List<String> newList = new LinkedList<>();

    list.forEach(item -> newList.add("resource:/" + removeExtension(item)));

    return newList;
  }


  static public List<File> getAllFonts() {
    List<String> list = getAllResource("fonts");
    List<File> newList = new LinkedList<>();

    list.forEach(item -> newList.add(
      getFileFromFilename("fonts", RESOURCE_PREFIX + removeExtension(item), getExtension(item))));

    return newList;
  }


  static public List<String> getAllResource(String category) {
    try {
      List<String> files = IOUtils.readLines(Converter.class.getClassLoader()
        .getResourceAsStream(category + "/"), StandardCharsets.UTF_8);

      return files;
    }
    catch (IOException e) {
      e.printStackTrace();
      LOGGER.log(Level.SEVERE, "Can't search the bundled resources");
      return new LinkedList<>();
    }
  }


  static public File getFileFromFilename(String category, String name, String extension) {
    // Depending on the name different File will be returned, either from resources or from filesystem directly

    if (name.startsWith(RESOURCE_PREFIX)) {
      String shortFilterName = category + "/" + name.substring(RESOURCE_PREFIX.length(),name.length()) + "." + extension;
      LOGGER.log(Level.INFO, "Using " + category + " from resource " + shortFilterName);
      ClassLoader classLoader = ResourcesHandler.class.getClassLoader();
      return new File(classLoader.getResource(shortFilterName).getFile());
    }
    else {
      return new File(name + "." + extension);
    }
  }


  static public File getFileFromFilenameFilter(String name) {
    return getFileFromFilename("filters", name, "yml");
  }


  static public File getFileFromFilenameFonts(String name) {
    return getFileFromFilename("fonts", name, "png");
  }


}
