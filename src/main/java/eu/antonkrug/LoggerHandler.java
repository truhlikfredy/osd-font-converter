package eu.antonkrug;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

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
      logger.setUseParentHandlers(false);

      ;

      ConsoleHandler handler = new ConsoleHandler();
      handler.setFormatter(new SimpleFormatter() {
        private static final String format = "%1$tT [%2$-7s] %3$-16s.%4$-25s %5$s %n";

        @Override
        public synchronized String format(LogRecord lr) {
          return String.format(format,
            new Date(lr.getMillis()),
            lr.getLevel().getLocalizedName(),
            StringUtils.substringAfterLast(lr.getSourceClassName(),"."),
            lr.getSourceMethodName(),
            lr.getMessage()
          );
        }
      });
      logger.addHandler(handler);

      map.put(className, logger);
      return logger;
    }
  }


  static public void setLevel(Level level) {
    lastLevel = level;
    map.values().forEach(logger -> logger.setLevel(level));
  }

}
