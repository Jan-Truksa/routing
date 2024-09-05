# Instructions

### Prerequisites

The application was written and build using:

Java   - version 17.0.11

Maven  - version 3.9.7


### Build

Use the standard Maven command to build or generate jar e.g.

```
mvn clean compile
```

or

```
mvn clean install
```

### Run

You can choose many ways to run the application.

#### Use Maven

Run the command bellow.

```
mvn spring-boot:run
```

#### IntelliJ Configuration

Please follow the official documentation: https://www.jetbrains.com/help/idea/run-debug-configuration-spring-boot.html

#### Run a JAR File

Run the generated JAR file.

```
java -jar target/route-0.0.1-SNAPSHOT.jar
```

### Usage

If the application is running you can use the URI mentioned 
in the assignment in a browser or application like Insomnia.
See the example bellow.

```
http://localhost:8080/routing/CZE/ITA
```