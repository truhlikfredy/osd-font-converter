package eu.antonkrug.model;

import eu.antonkrug.model.Effect;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public class Filter {
  private String   name;
  private String   description;
  private Effect[] effect;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Effect[] getEffect() {
    return effect;
  }

  public void setEffect(Effect[] effect) {
    this.effect = effect;
  }
}
