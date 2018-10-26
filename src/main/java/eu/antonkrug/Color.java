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

  private static final Map<String, Color>  ENUM_MAP_BYTECODE;
  private static final Map<Integer, Color> ENUM_MAP_RGB;


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
    Map<String,Color>  mapBytecode = new ConcurrentHashMap<>();
    Map<Integer,Color> mapRgb      = new ConcurrentHashMap<>();
    for (Color color: Color.values()) {
      mapBytecode.put(color.getBytecode(), color);
      mapRgb.put(color.getRgb(), color);
    }

    // Hard code the multiple possible bytecode values for transparent
    mapBytecode.put("11", TRANSPARENT);
    mapBytecode.put("x1", TRANSPARENT);

    // Making the map unmodifiable
    ENUM_MAP_BYTECODE = Collections.unmodifiableMap(mapBytecode);
    ENUM_MAP_RGB      = Collections.unmodifiableMap(mapRgb);
  }


  public static Color get(String bytecode) {
    if (!ENUM_MAP_BYTECODE.containsKey(bytecode)) {
      // if no match was found, return a transparent color
      return TRANSPARENT;
    }
    return ENUM_MAP_BYTECODE.get(bytecode);
  }


  public static Color get(Integer rgb) {
    if (!ENUM_MAP_RGB.containsKey(rgb)) {
      // if no match was found, return a transparent color
      return TRANSPARENT;
    }
    return ENUM_MAP_RGB.get(rgb);
  }


}
