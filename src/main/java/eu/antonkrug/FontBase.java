package eu.antonkrug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import eu.antonkrug.model.Effect;
import eu.antonkrug.model.Filter;
import javafx.util.Pair;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.File;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public abstract class FontBase implements Font {
  protected Character[] characters;
  protected String      path;


  public FontBase(Font origin) {
    clone(origin);
  }


  public FontBase(String path) {
    initStructures();
    this.path = path;
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
    path = origin.getPath();

    characters = new Character[MAX_CHARACTERS];
    for (int i = 0; i < MAX_CHARACTERS; i++) {
      characters[i] = new Character(origin.getCharacters()[i]);
    }
  }


  @Override
  public void setPath(String path) {
    this.path = path;
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


  public void outline() {
    for (int i = 0; i < Font.MAX_CHARACTERS; i++) {
      for (int x = 0; x < Character.WIDTH; x++) {
        for (int y = 0; y < Character.HEIGHT; y++) {
          if ( characters[i].pixels[x][y] == Color.WHITE) {
            for (int col = x-2; col <= x+2; col++) {
              for (int row = y -2; row <= y+2; row++) {
                if (characters[i].getBit(col, row) == Color.TRANSPARENT) {
                  characters[i].setBit(col, row, Color.BLACK);
                }
              }
            }
          }
        }
      }
    }
  }


  private void applyFilterOnCharacter(Character origin, Character destination, Filter filter) {
    for (Effect effect:filter.getEffects()) {
      for (int x = 0; x < Character.WIDTH; x++) {
        for (int y = 0; y < Character.HEIGHT; y++) {
          // For each effect check each pixel
          if (origin.getBit(x,y) == effect.getTrigger()) {
            // Effect was triggered, apply all coordinates
            for (Pair<Integer, Integer> cords: effect.getCoordinatesPairs()) {
              destination.setBit(x + cords.getKey(), y+cords.getValue(), effect.getPaint());
            }
          }
        }
      }
    }
  }


  public Font applyFilter(String effectName) {
    Font destination = new FontDefault();
    destination.setPath(this.path + "-" + effectName);

    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    Filter filter = new Filter();
    try {
      filter = mapper.readValue(new File("./src/main/resources/filters/" + effectName + ".yml"), Filter.class);
      System.out.println(ReflectionToStringBuilder.toString(filter, ToStringStyle.MULTI_LINE_STYLE));
      for (Effect effect: filter.getEffects()) {
        if (!effect.isValid()) {
          System.out.println("ERROR: Effect in filter is not valid");
          return null;
        }
      }
      //      System.out.println(effect.getEffect().getPaint().getBytecode());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    for (int i = 0; i < Font.MAX_CHARACTERS; i++) {
      applyFilterOnCharacter(this.characters[i], destination.getCharacters()[i], filter);
    }

    return destination;
  }

}
