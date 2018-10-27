package eu.antonkrug;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public interface Font {
  public static int MAX_CHARACTERS = 256;

  public void setPath(String path);

  public String getPath();

  public void setPathAndKeepBaseName(String path);

  public void setExtension(String path);

  public String getExtension();

  public String getPathWithExtension();

  public Character[] getCharacters();

  public void setCharacters(Character[] characters);

  public void clone(Font origin);

  public Font clone();

  public boolean load();

  public boolean save();

  public Font applyFilter(String effectName);

}
