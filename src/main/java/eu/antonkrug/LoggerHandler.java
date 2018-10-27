package eu.antonkrug;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Anton Krug on 25/10/18
 * @version v0.1
 */
public class LoggerHandler {
  static public  Map<String, Logger> map       = new HashMap<>();
  static private Level               lastLevel = Level.SEVERE;


  static public Logger getLogger(String className) {
    if (map.containsKey(className)) {
      return map.get(className);
    }
    else {
      Logger logger = Logger.getLogger(className);
      logger.setLevel(lastLevel);
      map.put(className, logger);
      return logger;
    }
  }


  static public void setLevel(Level level) {
    lastLevel = level;
    map.values().forEach(logger -> logger.setLevel(level));
  }

}
