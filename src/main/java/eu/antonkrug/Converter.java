package eu.antonkrug;

import picocli.CommandLine;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
@CommandLine.Command(name = "osd-font-converter.jar", header = "%n@|green Batch converter for OSD fonts with \"|@@|underline post-processing filters|@@|green \"(tm) :-)|@", versionProvider = Converter.MavenVersionProvider.class )
public class Converter implements Runnable {

  private static final Logger LOGGER = LoggerHandler.getLogger(Converter.class.getName());

  @CommandLine.Option(names = {"-a", "--all"}, description = "apply all filters from bundled resources (-f will be ignored)")
  boolean filterAll;

  @CommandLine.Option(names = {"-b", "--backgrounds"}, description = "generate previews with different backgrounds")
  boolean background;

  @CommandLine.Option(names = { "-f", "--filter" }, paramLabel = "FILTER", description = "give path to your filter.yml description (to reference bundled filter use \"resource:/outline-2x\")")
  String filter;

  @CommandLine.Option(names = {"-p", "--preview"}, description = "generate PNG previews of the final results")
  boolean preview;

  @CommandLine.Parameters(paramLabel = "FONTS", arity = "0..*", description = "one ore more files containing fonts")
  File[] fonts;
  // demo for bundled fonts
  // output folder

  @CommandLine.Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
  private boolean helpRequested;

  @CommandLine.Option(names = { "-o", "--outputFormat" }, paramLabel = "FORMAT", description = "What format will be used for saving the results (default: mcm)")
  String outputFormat;

  @CommandLine.Option(names = { "-l", "--logLevel" }, paramLabel = "LOG_LEVEL", description = "How much of log verbosity you want @|yellow INFO|@, @|red SEVERE|@, @|blue OFF|@")
  String logLevel;

  @CommandLine.Option(names = {"-v", "--version"}, versionHelp = true, description = "Print version info")
  boolean versionRequested;


  public static void main(String[] args) throws Exception {
    CommandLine.run(new Converter(), args);
  }


  @Override
  public void run() {
    Level level = Level.parse(logLevel);
    if (level == null) {
      LOGGER.log(Level.SEVERE, "Wrong --loglevel parameter.");
      return;
    }
    LoggerHandler.setLevel(level);

    for (File fontFile: fonts) {
      Font font = FontFactory.getInstance(fontFile.getPath());

      if (font == null) {
        LOGGER.log(Level.SEVERE, "Can't load the " + fontFile.getPath());
        System.exit(-1);
      }

      if (!font.load()) {
        LOGGER.log(Level.SEVERE, "Can't load the " + fontFile.getPath());
        System.exit(-1);
      }

      if (filterAll) {
        //go through all filters
        ResourcesHandler.getAllFilters().forEach(item -> postProcessFont(font, item));
      }
      else {
        postProcessFont(font, filter);
      }

    }
  }


  private void postProcessFont(Font font, String filter) {
    Font fontFinal;

    if (filter == null || filter.equals("")) {
      // no effect specified, do not apply any filter, just change the name slightly so the result will not override
      // the original
      fontFinal = font;
      fontFinal.setPath(fontFinal.getPath() + "-out");
    }
    else {
      fontFinal = font.applyFilter(filter);

      if (fontFinal == null) {
        LOGGER.log(Level.SEVERE, "Can't apply filter " + filter + ", check if you speciefied correct filter with -a or -f");
      }
    }

    fontFinal.save();

    if (preview) {
      Font fontPng = new FontPng(fontFinal);
      fontPng.setPath(fontPng.getPath() + "-preview-blank");
      fontPng.save();

      if ( background) {
        //TODO: go through all backgrounds
      }
    }
  }


  /**
   * {@link CommandLine.IVersionProvider}
   */
  static class MavenVersionProvider implements CommandLine.IVersionProvider {
    public String[] getVersion() throws Exception {
      if (getClass().getPackage().getImplementationVersion() != null) {
        return new String[] { getClass().getPackage().getImplementationVersion().toString() };
      }
      else {
        return new String[] { "Can't get runtime version info" };
      }

    }
  }


}

