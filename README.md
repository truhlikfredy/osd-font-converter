# Overview

[![Build Status](https://travis-ci.org/truhlikfredy/osd-font-converter.svg?branch=master)](https://travis-ci.org/truhlikfredy/osd-font-converter)

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