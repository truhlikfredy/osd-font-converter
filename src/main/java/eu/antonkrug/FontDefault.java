package eu.antonkrug;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public class FontDefault extends  FontBase {

  public FontDefault(Font origin) {
    super(origin);
  }


  public FontDefault(String path) {
    super(path);
  }


  public FontDefault() {
    super();
  }


  @Override
  public boolean load() {
    System.out.println("ERROR: Load is not implemented for this format.");
    return false;
  }


  @Override
  public boolean save() {
    System.out.println("ERROR: Save is not implemented for this format.");
    return false;
  }


}
