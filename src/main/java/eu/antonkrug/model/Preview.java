package eu.antonkrug.model;

/**
 * @author Anton Krug on 26/10/18
 * @version v0.1
 */
public class Preview {
  private String  description;
  private int[][] symbols;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int[][] getSymbols() {
    return symbols;
  }

  public void setSymbols(int[][] symbols) {
    this.symbols = symbols;
  }
}
