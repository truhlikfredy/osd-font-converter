package eu.antonkrug;

import picocli.CommandLine;

import java.io.File;
import java.util.logging.Level;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
@CommandLine.Command(name = "osd-font-converter.jar", header = "%n@|green Batch converter for OSD fonts with \"|@@|underline post-processing filters|@@|green \"(tm) :-)|@")
public class Converter implements Runnable {
  @CommandLine.Option(names = "-a", description = "apply all filters from bundled resources")
  boolean create;

  @CommandLine.Option(names = { "-f", "--filter" }, paramLabel = "FILTER", description = "How much of log verbosity you want INFO, SEVERE, OFF")
  String filter;

  @CommandLine.Option(names = { "-l", "--logLevel" }, paramLabel = "LOG_LEVEL", description = "give path to your filter.yml description (to reference bundled filter use \"resource:/outline-2x\") ")
  String logLevel;

  @CommandLine.Parameters(paramLabel = "FONTS", description = "one ore more files containing fonts")
  File[] fonts;

  @CommandLine.Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
  private boolean helpRequested;

  public static void main(String[] args) throws Exception {
    CommandLine.run(new Converter(), args);
  }

  @Override
  public void run() {
    Level level = Level.parse(logLevel);
    if (level != null) {
      LoggerHandler.setLevel(Level.INFO);
    }

    //    Font font = new FontMcm("/home/fredy/Desktop/a");
    //    font.load();
    //
    //    Font fontWithEffect = font.applyFilter("resource:/outline-2x");
    //
    //    Font fontPng = new FontPng(fontWithEffect);
    //    fontPng.save();
    System.out.println("Hello, ");
  }
}
