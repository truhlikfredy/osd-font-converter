package eu.antonkrug;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */

public enum Color {
  BLACK(             "00", 0x000000),
  WHITE(             "10", 0xffffff),
  TRANSPARENT(       "01", 0x999999),
  OUT_OF_BOUNDARIES( "xx", 0xff9999);

  private String bytecode;
  private int    rgb;

  private static final Map<String, Color> ENUM_MAP;


  Color(String bytecode, int rgb) {
    this.bytecode = bytecode;
    this.rgb      = rgb;
  }


  public String getBytecode() {
    return this.bytecode;
  }


  public int getRgb() {
    return this.rgb;
  }


  static {
    Map<String,Color> map = new ConcurrentHashMap<String,Color>();
    for (Color color: Color.values()) {
      map.put(color.getBytecode(), color);
    }

    // Hard code the multiple possible values for transparent
    map.put("11", TRANSPARENT);
    map.put("x1", TRANSPARENT);

    // Making the map unmodifiable
    ENUM_MAP = Collections.unmodifiableMap(map);
  }


  public static Color get(String bytecode) {
    return ENUM_MAP.get(bytecode);
  }

}
