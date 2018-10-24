package eu.antonkrug.model;

import eu.antonkrug.Color;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public class Effect {
  private Color   trigger;
  private Color   paint;
  private int[][] coordinates;

  public Color getTrigger() {
    return trigger;
  }

  public void setTrigger(Color trigger) {
    this.trigger = trigger;
  }

  public Color getPaint() {
    return paint;
  }

  public void setPaint(Color paint) {
    this.paint = paint;
  }

  public int[][] getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(int[][] coordinates) {
    this.coordinates = coordinates;
  }

  public boolean isValid() {
    for (int i = 0; i < coordinates.length; i++) {
      if (coordinates[i].length != 2) return false;
    }
    return true;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Effect{");
    sb.append("trigger=").append(trigger);
    sb.append(", paint=").append(paint);

    String cords = Arrays
      .stream(coordinates)
      .map(Arrays::toString)
      .collect(Collectors.joining(","));

    sb.append(", coordinates=").append(coordinates == null ? "null" : "[" + cords + "]");
    sb.append('}');
    return sb.toString();
  }


}
