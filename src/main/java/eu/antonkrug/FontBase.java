package eu.antonkrug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import eu.antonkrug.model.Effect;
import eu.antonkrug.model.Filter;
import javafx.util.Pair;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public abstract class FontBase implements Font {
  private   final static String RESOURCE_PREFIX = "resource:/";
  protected Character[] characters;
  protected String      path;
  protected String      extension;

  private static final Logger LOGGER = LoggerHandler.getLogger( FontBase.class.getName() );

  public FontBase(Font origin) {
    clone(origin);
  }


  public FontBase(String path) {
    initStructures();
    this.path = FilenameUtils.removeExtension(path);
    this.extension = FilenameUtils.getExtension(path);
  }


  public FontBase() {
    initStructures();
    this.path = "";
  }


  private void initStructures() {
    characters = new Character[MAX_CHARACTERS];
    for (int i = 0; i < MAX_CHARACTERS; i++) {
      characters[i] = new Character();
    }
  }


  @Override
  public void clone(Font origin) {
    path      = origin.getPath();
    extension = origin.getExtension();

    characters = new Character[MAX_CHARACTERS];
    for (int i = 0; i < MAX_CHARACTERS; i++) {
      characters[i] = new Character(origin.getCharacters()[i]);
    }
  }


  @Override
  public Font clone() {
    Font copy = FontFactory.getInstance(this.getPathWithExtension());
    copy.setPath(this.getPath());
    copy.setExtension(this.getExtension());

    for (int i = 0; i < MAX_CHARACTERS; i++) {
      copy.getCharacters()[i] = new Character(this.getCharacters()[i]);
    }
    return copy;
  }


  @Override
  public void setPath(String path) {
    this.path = path;
  }


  @Override
  public void setPathAndKeepBaseName(String path) {
    final String fileName = FilenameUtils.getBaseName(this.path);
    this.path = path + "/" + fileName;
  }


  @Override
  public String getPath() {
    return this.path;
  }


  @Override
  public Character[] getCharacters() {
    return characters;
  }


  @Override
  public void setCharacters(Character[] characters) {
    this.characters = characters;
  }

  @Override
  public abstract boolean load();


  @Override
  public abstract boolean save();


  @Override
  public String getExtension() {
    return extension;
  }


  @Override
  public void setExtension(String extension) {
    this.extension = extension;
  }


  @Override
  public String getPathWithExtension() {
    return path + "." + extension;
  }


  private void applyFilterOnCharacter(Character origin, Character destination, Filter filter) {
    for (Effect effect:filter.getEffects()) {
      for (int x = 0; x < Character.WIDTH; x++) {
        for (int y = 0; y < Character.HEIGHT; y++) {
          // For each effect check each pixel
          if ( origin.getPixel(x,y) == effect.getTrigger()) {
            // Effect was triggered, apply all coordinates
            for (Pair<Integer, Integer> cords: effect.getCoordinatesPairs()) {
              destination.setPixel(x + cords.getKey(), y + cords.getValue(), effect.getPaint());
            }
          }
        }
      }
    }
  }


  private File getFileFromFilename(String filterName) {
    if (filterName.startsWith(RESOURCE_PREFIX)) {
      String shortFilterName = "filters/" + filterName.substring(RESOURCE_PREFIX.length(),filterName.length()) + ".yml";
      LOGGER.log(Level.INFO, "Loading filter from resources " + shortFilterName);
      ClassLoader classLoader = getClass().getClassLoader();
      return new File(classLoader.getResource(shortFilterName).getFile());
    }
    else {
      return new File(filterName + ".yml");
    }
  }


  public Font applyFilter(String filterName) {
    Font destination = FontFactory.getInstance(path + "." + extension);

    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    Filter filter = new Filter();
    try {
      File file = getFileFromFilename(filterName);
      LOGGER.log(Level.FINEST, "Filename: " + file.toString());
      destination.setPath(this.path + "-" + FilenameUtils.removeExtension(file.getName()));

      filter = mapper.readValue(file, Filter.class);
      LOGGER.log(Level.FINEST, "Filter loaded: " + ReflectionToStringBuilder.toString(filter, ToStringStyle.MULTI_LINE_STYLE));
      for (Effect effect: filter.getEffects()) {
        if (!effect.isValid()) {
          LOGGER.log(Level.SEVERE, "ERROR: Effect in filter " + filterName + " is not valid");
          return null;
        }
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }

    for (int i = 0; i < Font.MAX_CHARACTERS; i++) {
      applyFilterOnCharacter(this.characters[i], destination.getCharacters()[i], filter);
    }

    return destination;
  }


}
