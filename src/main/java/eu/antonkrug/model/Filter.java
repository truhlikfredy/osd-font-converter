package eu.antonkrug.model;

import eu.antonkrug.model.Effect;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public class Filter {
  private String   name;
  private String   description;
  private Effect[] effects;

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

  public Effect[] getEffects() {
    return effects;
  }

  public void setEffects(Effect[] effects) {
    this.effects = effects;
  }
}
