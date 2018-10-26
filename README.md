# Overview

[![Build Status](https://travis-ci.org/truhlikfredy/osd-font-converter.svg?branch=master)](https://travis-ci.org/truhlikfredy/osd-font-converter)

# Usage

```
Batch converter for OSD fonts with "post-processing filters"(tm) :-)
Usage: osd-font-converter.jar [-abdhpv] [-f=FILTER] [-l=LOG_LEVEL] [-o=FORMAT]
                              [-u=OUTPUT_FOLDER] [FONTS...]
      [FONTS...]             one ore more files containing fonts
  -a, --all                  apply all filters from bundled resources (-f will be
                               ignored)
  -b, --backgrounds          generate previews with different backgrounds
  -d, --demo                 Use all bundled fonts
  -f, --filter=FILTER        give path to your filter.yml description (to reference
                               bundled filter use "resource:/outline-2x")
  -h, --help                 display a help message
  -l, --logLevel=LOG_LEVEL   How much of log verbosity you want INFO, SEVERE, OFF
  -o, --outputFormat=FORMAT  What format will be used for saving the results
                               (default: mcm)
  -p, --preview              generate PNG previews of the final results
  -u, --outputFolder=OUTPUT_FOLDER
                             Where the output files are stored (by default the
                               current folder is used)
  -v, --version              Print version info
```

# Dependencies

- Java 8 for compilation and for runtime
- Maven

# Bundled runtime dependencies

- Jackson
- Picocli
- Apache commons
 
# Build

Maven is outputing files the **/target/** folder.

To build jar with external dependencies copied to /target/dependency-jars/:

```
mvn clean package
```

To build jar with all bundled jars in one file run:

```
mvn clean compile assembly:single
```

Both standalone and bundled commands should produce jars /target/osd-font-converter*.jar