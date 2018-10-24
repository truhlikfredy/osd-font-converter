package eu.antonkrug;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public interface Font {
  public static int MAX_CHARACTERS = 256;

  public void setPath(String path);

  public String getPath();

  public Character[] getCharacters();

  public void setCharacters(Character[] characters);

  public void clone(Font origin);

  public boolean load();

  public boolean save();

  public void outline();

}
