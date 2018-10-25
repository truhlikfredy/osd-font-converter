package eu.antonkrug;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.io.FilenameUtils.removeExtension;

/**
 * @author Anton Krug on 25/10/18
 * @version v0.1
 */
public class ResourcesHandler {

  private static final Logger LOGGER = LoggerHandler.getLogger(ResourcesHandler.class.getName());


  static public List<String> getAllFilters() {
    List<String> list = getAllResource("filters");
    List<String> newList = new LinkedList<>();

    list.forEach(item -> newList.add("resource:/" + removeExtension(item)));

    return newList;
  }


  static public List<String> getAllResource(String folder) {
    try {
      List<String> files = IOUtils.readLines(Converter.class.getClassLoader()
        .getResourceAsStream(folder + "/"), StandardCharsets.UTF_8);

      return files;
    }
    catch (IOException e) {
      e.printStackTrace();
      LOGGER.log(Level.SEVERE, "Can't search the bundled resources");
      return new LinkedList<>();
    }
  }


}
