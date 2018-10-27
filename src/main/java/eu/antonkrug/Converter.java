package eu.antonkrug;

import picocli.CommandLine;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
@CommandLine.Command(name = "osd-font-converter.jar",
                     header = "%n@|green Batch converter for OSD fonts with \"|@@|underline post-processing filters|@@|green \"(tm) :-)|@",
                     versionProvider = Converter.MavenVersionProvider.class )
public class Converter implements Runnable {

  private static final Logger LOGGER = LoggerHandler.getLogger(Converter.class.getName());


  @CommandLine.Option(names = {"-a", "--all"},
                      description = "apply all filters from bundled resources (-f will be ignored)")
  boolean filterAll;


  @CommandLine.Option(names = {"-b", "--backgrounds"},
                      description = "generate previews with different backgrounds")
  boolean background;


  @CommandLine.Option(names = { "-f", "--filter" },
                      paramLabel = "FILTER",
                      description = "give path to your filter.yml description (to reference bundled filter use \"resource:/outline-2x\")")
  String filter;


  @CommandLine.Option(names = {"-p", "--preview"},
                      paramLabel = "PREVIEW_LAYOUT",
                      description = "generate PNG preview of the final results, either use external file or \"resource:/mwosd\" syntax.")
  String previewName;


  @CommandLine.Option(names = {"-m", "--previewAll"},
                      description = "generate all PNG previews of the final results")
  boolean previewAll;


  @CommandLine.Parameters(paramLabel = "FONTS",
                          arity = "0..*",
                          description = "one ore more files containing fonts")
  File[] fonts;


  @CommandLine.Option(names = {"-d", "--demo"},
                      description = "Use all bundled fonts")
  boolean demo;


  @CommandLine.Option(names = { "-h", "--help" },
                      usageHelp = true,
                      description = "display a help message")
  private boolean helpRequested;


  @CommandLine.Option(names = {"-u", "--outputFolder"},
                      paramLabel = "OUTPUT_FOLDER",
                      description = "Where the output files are stored (by default the current folder is used)")
  String outputFolder;


  @CommandLine.Option(names = { "-o", "--outputFormat" },
                      paramLabel = "FORMAT",
                      description = "What format will be used for saving the results (default: mcm)")
  String outputFormat;


  @CommandLine.Option(names = { "-l", "--logLevel" },
                      paramLabel = "LOG_LEVEL",
                      description = "How much of log verbosity you want @|yellow INFO|@, @|red SEVERE|@, @|blue OFF|@")
  String logLevel;


  @CommandLine.Option(names = {"-v", "--version"},
                      versionHelp = true,
                      description = "Print version info")
  boolean versionRequested;


  public static void main(String[] args) throws Exception {
    // Let picoli parse arguments (display help if needed), set all arguments and then run "run()"
    CommandLine.run(new Converter(), args);
  }


  @Override
  public void run() {
    // Main business logic can change a lot depending on the arguments and settings

    if (logLevel == null || logLevel.equals("")) {
      // if nothing provided use default
      logLevel = "SEVERE";
    }
    Level level = Level.parse(logLevel);
    if (level == null) {
      LOGGER.log(Level.SEVERE, "Wrong --loglevel parameter.");
      return;
    }
    LoggerHandler.setLevel(level);

    validateFolder();

    if (demo) {
      ResourcesHandler.getAllFonts().forEach(fontName -> useFont(fontName));
    }
    else {
      if (fonts == null || fonts.length ==0) {
        LOGGER.log(Level.SEVERE, "You have to either specify font files, or use demo -d mode when the bundled fonts will used." );
        System.exit(1);
      }
      for (File fontFile: fonts) {
        useFont(fontFile.toURI().toString());
      }

    }
  }


  private void validateFolder() {
    System.out.println(outputFolder);
    if (outputFolder == null || outputFolder.equals("")) {
      // if output folder was set badly, use default
      outputFolder = "./";
    }

    if (!new File(outputFolder).exists()) {
      LOGGER.log(Level.SEVERE, "Output folder " + outputFolder + ", doesn't exist");
      System.exit(1);
    }
  }


  private void useFont(String fontName) {
    Font font = FontFactory.getInstance(fontName);

    if (!isValidSettings(font, fontName)) {
      System.exit(1);
    }

    if (filterAll) {
      //go through all filters
      ResourcesHandler.getAllFilters().forEach(item -> postProcessFont(font, item));
    }
    else {
      postProcessFont(font, filter);
    }
  }


  private boolean isValidSettings(Font font, String fontName) {
    if (font == null) {
      LOGGER.log(Level.SEVERE, "Can't load the " + fontName);
      return false;
    }

    if (!font.load()) {
      LOGGER.log(Level.SEVERE, "Can't load the " + fontName);
      return false;
    }

    return  true;
  }


  private void postProcessFont(Font font, String filter) {
    Font fontFinal;

    if (filter == null || filter.equals("")) {
      // no effect specified, do not apply any filter, just change the name slightly so the result will not override
      // the original
      fontFinal = font.clone();
      fontFinal.setPath(fontFinal.getPath() + "-out");
    }
    else {
      fontFinal = font.applyFilter(filter);

      if (fontFinal == null) {
        LOGGER.log(Level.SEVERE, "Can't apply filter " + filter + ", check if you speciefied correct filter with -a or -f");
      }
    }

    LOGGER.log(Level.INFO, "Using output folder: " + outputFolder);

    fontFinal.setPathAndKeepBaseName(outputFolder);
    fontFinal.save();

    if (previewAll) {
      Font fontPng = new FontPng(fontFinal);

      //use the name of the original font, not the post processed font, as it has own postfix
      fontPng.setPath(font.getPath() + "-preview-blank");
      fontPng.setPathAndKeepBaseName(outputFolder);
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

