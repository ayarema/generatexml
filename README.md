## Generate XML report by [[ `easytestit` ](http://easytestit.com/)]
Let's imagine that you need to convert Cucumber's JSON BDD reports files to one aggregated JUnit's XML report.

This is the open-source library that will convert JSON reports and will generate one detailed report file in [JUnit XML format](https://www.ibm.com/support/knowledgecenter/en/SSQ2R2_14.2.0/com.ibm.rsar.analysis.codereview.cobol.doc/topics/cac_useresults_junit.html#junitschema).

`Generate-XML` library was written to be used if new separate JSON report files are created on your project for each individual feature file during the test. Or in case if new XML reports are created for each cucumber feature file but without detailed information.
You could use `Generate-XML` library if you want to create one detailed and aggregated report. 

In addition, the implementation of this `Generate-XML` library includes the ability to send a generated aggregated XML report to the server, where the results of all reports are displayed, for example, [ReportPortal.io](https://github.com/reportportal/reportportal).

## Getting Started
`Generate-XML` requires [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 8 (at least version 1.8.0_221 or greater) and then either [Maven](http://maven.apache.org), [Gradle](https://gradle.org), [Eclipse](https://www.eclipse.org/ide/) or [IntelliJ](https://www.jetbrains.com/idea/) to be installed.

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
compile group: 'com.easytestit', name: 'generate-xml', version: '1.0.0'
```

### How to use it?
Generate-XML is simple library to use and to configure:

```java
    @AfterAll
    public void afterAll() {
        var conf = new ConfigureXMLReport(new File("out/reports/"));
        conf.addConfigureXMLMode(ConfigureXMLMode.ZIP_XML_RESULT_TO_FILE);
        conf.addConfigureXMLMode(ConfigureXMLMode.SEND_RESULT_TO_RP);
        GenerateXML generateXML = new GenerateXML(conf);
        generateXML.make();
    }
```
If you don't want to create additional configuration then you could use your code like this:
```java
    @AfterAll
    public void afterAll() {
        GenerateXML generateXML = new GenerateXML();
        generateXML.make();
    }
```
In this case keep in mind that system will take default folder where locate Cucumber JSON reports after launch all tests:
`String reportDirPath = "target/surefire-reports/";`

### Additional configuration
Keep it mind that if you add `ConfigureXMLMode.SEND_RESULT_TO_RP` parameter you need to add to your `resources` a new `.properies` file with name `sender.properties` :

```groovy
rp.token=token that you could find on RP in personal page
rp.url=http://your_host:8080
rp.api.version=/api/v1
rp.project.name=/your_project_name
rp.service.url=/launch/import
```

### Results
After launching Generate-XML functionality the new aggregated JUnit XML report will be created in: `String reportResultsFolder = "out/xml-reports/";`
