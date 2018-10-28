package eu.antonkrug;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
    List<String> newList = list.stream().map(item -> RESOURCE_PREFIX + removeExtension(item))
      .collect(Collectors.toList());

    return newList;
  }


  static public List<String> getAllFonts() {
    List<String> list = getAllResource("fonts");
    List<String> newList = list.stream().map(item -> RESOURCE_PREFIX + item)
      .collect(Collectors.toList());

    return newList;
  }


  static public List<String> getAllResource(String category) {
    String protocol = ResourcesHandler.class.getResource("").getProtocol();

    if (protocol == "jar") {
      return getAllResourceJar(category);
    }
    else {
      return getAllResourceClass(category);
    }
  }


  static public List<String> getAllResourceClass(String category) {
    try {
      List<String> files = IOUtils.readLines(ResourcesHandler.class.getClassLoader()
        .getResourceAsStream(category + "/"), StandardCharsets.UTF_8);

      return files;
    }
    catch (IOException e) {
      e.printStackTrace();
      LOGGER.log(Level.SEVERE, "Can't search the bundled resources");
      return new LinkedList<>();
    }
  }


  static private List<String> getAllResourceJar(String category) {
    List<String> files = new LinkedList<>();

    CodeSource src = ResourcesHandler.class.getProtectionDomain().getCodeSource();
    if (src != null) {
      URL jar = src.getLocation();
      try {
        ZipInputStream zip = new ZipInputStream(jar.openStream());
        while(true) {
          ZipEntry e = zip.getNextEntry();
          if (e == null)
            break;
          String name = e.getName();
          if (name.startsWith(category + "/")) {
            files.add(name.substring((category + "/").length(), name.length() ));
          }
        }
      }
      catch (IOException e) {
        e.printStackTrace();
        LOGGER.log(Level.SEVERE, "Can't search the bundled JAR resources");
      }
    }
    if (files.size() > 0) {
      // if the list is not empty, then remove listing of the folder itself
      files.remove(0);
    }
    return files;
  }


  static public InputStream getStreamFromFilename(String category, String name, String extension) {
    // Depending on the name different File will be returned, either from resources or from filesystem directly

    if (name.startsWith(RESOURCE_PREFIX)) {
      String shortFilterName = category + "/" + name.substring(RESOURCE_PREFIX.length(),name.length()) + "." + extension;
      LOGGER.log(Level.INFO, "Using " + category + " from resource " + shortFilterName);
      ClassLoader classLoader = ResourcesHandler.class.getClassLoader();
      return classLoader.getResourceAsStream(shortFilterName);
    }
    else {
      try {
        return FileUtils.openInputStream(new File(name + "." + extension));
      }
      catch (IOException e) {
        e.printStackTrace();
        LOGGER.log(Level.SEVERE, "Failed to open stream " + name + "." + extension);
        return null;
      }
    }
  }


  static public InputStream getFileFromFilenameFilter(String name) {
    return getStreamFromFilename("filters", name, "yml");
  }


  static public InputStream getFileFromFilenameFonts(String name) {
    return getStreamFromFilename("fonts", name, "png");
  }


}
