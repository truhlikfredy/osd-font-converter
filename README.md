# Overview

[![Build Status](https://travis-ci.org/truhlikfredy/osd-font-converter.svg?branch=master)](https://travis-ci.org/truhlikfredy/osd-font-converter)

A tool to convert OSD type of fonts between formats and apply filter/effects on them. It's batch mode friendly and can be part of other toolchain/process/automation, each change on fonts can trigger osd-font-converter and apply desired filters/effects while converting it to other formats.

# Download binary

- Download compiled **JAR** [here](https://github.com/truhlikfredy/osd-font-converter/releases/download/1.0-SNAPSHOT-20181027-223437/osd-font-converter-jar-with-dependencies.jar)
 
- Download **bundled fonts** with all filters applied [here](https://github.com/truhlikfredy/osd-font-converter/releases/download/1.0-SNAPSHOT-20181027-223437/osd-font-converter-preview.zip)

# Download sources

- **All files** (jar, fonts with all filters applied and sources) from latest release can be seen [here](https://github.com/truhlikfredy/osd-font-converter/releases/latest)

- To clone the **stable sources** from git use this command:<br/>
  `git clone --single-branch -b master https://github.com/truhlikfredy/osd-font-converter.git`
  
- To clone the **bleeding-edge sources** from git use this command:<br/>
  `git clone --single-branch -b develop https://github.com/truhlikfredy/osd-font-converter.git`

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

# Fonts

Users can use external files and convert their files, but for ease of use some fonts are bundled in. -d will use all of them or just list some with syntax **resource:/mwosd-default.png**  

Bundled fonts:

- MWOSD http://www.mwosd.com/

(fell free to make PR if you want your font to be included as well)

# Formats

- PNG (load and save)
- MCM (load and save)
- H (save C header files support will be added in the future) 

# Filters

Not just the fonts can be converted between formats, but can filters can be applied on top, either use -a to apply all filters, or specify one:

```
resource:/bold
resource:/invert
resource:/only-black
resource:/only-white
resource:/outline-1x
resource:/outline-2x-opaque
resource:/outline-2x
resource:/outline-tiny
resource:/shadow-distance-opaque-outline
resource:/shadow-distance-opaque
resource:/shadow-distance
resource:/shadow-tiny
```

If needed use regular **URI** to point to external filter.
  
# Filter syntax

To make custom filter, create a YAML file with following syntax:

```
description: Small outline with diagonals
effects:
 - trigger: WHITE
   paint: BLACK
   coordinates:
    - [ 1,  0]
    - [-1,  0]
    - [ 0, -1]
    - [ 0,  1]
    - [ 1, -1]
    - [-1,  1]
    - [-1, -1]
    - [ 1,  1]

 - trigger: BLACK
   paint: BLACK
   coordinates:
    - [ 0, 0]

 - trigger: WHITE
   paint: WHITE
   coordinates:
    - [ 0, 0]
```

Trigger specifies on what color the event will be triggered and then paint specifies which color will be used for painting for that trigger at what coordinate offsets ( 0,0 is exactly pixel where the event was triggered). The effects are applied in the order at which they are specified in the file.
(fell free to make PR if you want your filter to be included as well)

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