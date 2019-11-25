## XML report by [[ `easytestit` ](http://easytestit.com/)]
This is the open-source library that will generate one detailed XML report file. `Generate-XML` library was written to be used if new separate JSON report files are created on your project for each individual feature file during the test. Or in case if new XML reports are created for each cucumber feature file but without detailed information.
You could use `Generate-XML` library if you want to create one detailed and aggregated report. 

In addition, the implementation of this `Generate-XML` library includes the ability to send a generated aggregated XML report to the server, where the results of all reports are displayed, for example, [ReportPortal.io](https://github.com/reportportal/reportportal).

## Getting Started
`Generate-XML` requires [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 8 (at least version 1.8.0_221 or greater) and then either [Maven](http://maven.apache.org), [Gradle](https://gradle.org), [Eclipse](#eclipse-quickstart) or [IntelliJ](https://github.com/intuit/karate/wiki/IDE-Support#intellij-community-edition) to be installed.

### Dependencies
For start with `Generate-XML` you need just to add dependency to your:
#### Maven
```xml
<dependency>
    <groupId>com.easytestit</groupId>
    <artifactId>generate-xml</artifactId>
    <version>1.0.0</version>
</dependency>
```
#### Gradle
Or, if you use Gradle build tool you can add
```groovy
compile 
```