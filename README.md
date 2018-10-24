# Dependencies

- Java 8 for runtime
- Maven
 
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