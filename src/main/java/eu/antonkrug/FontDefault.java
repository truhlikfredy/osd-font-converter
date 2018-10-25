package eu.antonkrug;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public class FontDefault extends  FontBase {

  private static final Logger LOGGER = LoggerHandler.getLogger( FontDefault.class.getName() );

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
    LOGGER.log(Level.SEVERE, "ERROR: Load is not implemented for this format.");
    return false;
  }


  @Override
  public boolean save() {
    LOGGER.log(Level.SEVERE, "ERROR: Save is not implemented for this format.");
    return false;
  }


}
