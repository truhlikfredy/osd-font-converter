package eu.antonkrug;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public interface Font {
  public static int MAX_CHARACTERS = 256;

  public boolean load();

  public boolean save();

  public boolean snapshotPng(String path);

  public void outline();

}
