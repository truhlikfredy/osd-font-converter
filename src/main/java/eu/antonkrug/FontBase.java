package eu.antonkrug;

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


}
