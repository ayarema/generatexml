[![Stand With Ukraine](https://raw.githubusercontent.com/vshymanskyy/StandWithUkraine/main/banner2-direct.svg)](https://stand-with-ukraine.pp.ua)
## Generate XML report by [[ `testiteasy` ](http://testiteasy.net/)]
Let's imagine that you need to convert Cucumber's JSON BDD reports files to one aggregated JUnit's XML report.

This is the open-source library that will convert JSON reports and will generate one detailed report file in [JUnit's XML format](https://www.ibm.com/support/knowledgecenter/en/SSQ2R2_14.2.0/com.ibm.rsar.analysis.codereview.cobol.doc/topics/cac_useresults_junit.html#junitschema).

`Generate-XML` library was written to be used if new separate JSON report files are created on your project for each individual feature file during the test. Or in case if new XML reports are created for each Cucumber's feature file but without detailed information.
You could use the `Generate-XML` library if you want to create one detailed and aggregate report.

Before setting up and using the library, please note that your Cucumber's reports are similar in format as in the [appendix example](#appendix example). These reports were generated, for example, using the [Karate framework](https://intuit.github.io/karate/) for testing functionality on the API layer. You can check this from the ["quick start"](https://github.com/intuit/karate#quickstart) block with the addition of the ["run in parallel"](https://github.com/intuit/karate#junit-5-parallel-execution) functionality.

In addition, the implementation of this `Generate-XML` library includes the ability to send a generated aggregated XML report to the server, where the results of all reports are displayed, for example, [ReportPortal.io](https://github.com/reportportal/reportportal).

## Getting Started
`Generate-XML` requires [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 8 (at least version 1.8.0_221 or greater) and then either [Maven](http://maven.apache.org) or [Gradle](https://gradle.org), [Eclipse](https://www.eclipse.org/ide/) or [IntelliJ](https://www.jetbrains.com/idea/) to be installed.

### Dependencies
For start with `Generate-XML` you need just to add dependency to your:
#### Maven
```xml
<dependency>
    <groupId>net.testiteasy</groupId>
    <artifactId>generate-xml</artifactId>
    <version>1.0.0</version>
</dependency>
```
#### Gradle
Or, if you use Gradle build tool you can add
```groovy
compile group: 'net.testiteasy', name: 'generate-xml', version: '1.0.0'
```

### How to use it?
`Generate-XML` is simple library to use and to configure. You could use Generate-XML with two different ways. The first way is creating functionality with predefined configuration:

```java
    @AfterAll
    public void afterAll() {
        ConfigureXMLReport conf = new ConfigureXMLReport(
                        "src/test/resources/",
                        "Test Project",
                        false,
                        false
                );
        new GenerateXML(conf).make();
    }
```
In this way you will create a object of ConfigureXMLReport() class with a configuration that takes 3 parameters:
* first String value - a path to JSON files
* second String value - a name of project (this name will use for creating aggregated XML file) 
* third parameter is boolean value which is responsible for creating a ZIP file 
* and fourth parameter with boolean value to which is responsible for a sending ZIP file to the report server.
 
And the second way, if you don't want to create additional configuration then you could use your code like this:
```java
    @AfterAll
    public void afterAll() {
        new GenerateXML().make();
    }
```
In this case keep in mind that system will take default folder where locate Cucumber JSON reports after launch all tests:
`target folder path = "target/surefire-reports/";`
If you pay attention to the path you can see that this is the default path for tests that were launched via Maven. Be careful if your project is used Gradle build tools, in this case, the path to reports will be different. So, in this case, most likely you will need to use functionality with predefined parameters.

### Additional configuration
Keep it mind that if you create Generate-XML with predefined configuration (`new ConfigureXMLReport("out/reports/cucumber-reports/", true, true);`), in this case application will need specified parameters for a sending created data to the report server. So, you need to add to your `resources` a new `.properies` file with name `gxml.properties` :

```groovy
rp.token=token that you could find on RP in personal page
rp.url=http://your_host:8080
rp.api.version=/api/v1
rp.project.name=/your_project_name
rp.project.project=/project
rp.service.url=/launch/import
```

### Results
After launching `Generate-XML` functionality the new aggregated JUnit XML report will be created in: `report.result.folder.path=out/xml-reports/`

### License
`Generate-XML` is open-source project, and distributed under the [MIT](https://choosealicense.com/licenses/mit/) license.

### Appendix example
This block presents an example of the Cucumber JSON report; in this example, all the fields that the report may contain are not completely presented, this is just an example.
```json
[
  {
    "line": 2,
    "elements": [
      {
        "line": 5,
        "name": "",
        "description": "",
        "type": "background",
        "keyword": "Background",
        "steps": [
          {
            "name": "url 'https:\/\/jsonplaceholder.typicode.com'",
            "result": {
              "duration": 182700,
              "status": "passed"
            },
            "match": {
              "location": "karate",
              "arguments": []
            },
            "keyword": "*",
            "line": 6,
            "doc_string": {
              "content_type": "",
              "value": "12:26:53.367 karate.env system property was: null",
              "line": 6
            }
          }
        ]
      }
    ],
    "name": "examples\/users\/users.feature",
    "description": "sample karate test script\nfor help, see: https:\/\/github.com\/intuit\/karate\/wiki\/IDE-Support",
    "id": "sample-karate-test-script",
    "keyword": "Feature",
    "uri": "examples\/users\/users.feature",
    "tags": [
      {
        "name": "@smoke",
        "line": 1
      }
    ]
  }
]
```
