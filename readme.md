# Wiremock

This project provides an implementation of a WireMock server that is automatically configured to generate Pacts, using Spring Boot to automatically configure WireMock.

# Installation 
To use this project, follow these steps:

1. Clone the repository: `git clone https://github.com/cmccarthyIrl/wiremock-spring-boot.git` <br>
2. Open the project in your preferred IDE. <br>
3. Build the project: `./gradlew build` or `mvnw package` from the root directory<br>
4. Run the WireMock server by executing the following command:

```bash
java -jar wiremock-0.0.1-SNAPSHOT.jar
```

This will start the server on port 80. Once the server is running, you can access the WireMock UI by navigating to http://localhost:80

# Configuration
This project uses Spring Boot to automatically configure WireMock. The following configuration options are available:

| Property Name                  | Description                                        | Default Value        |
|:-------------------------------|:---------------------------------------------------|:---------------------|
| wiremock.server.port           | The port that the WireMock server should listen on | 80                   |
| wiremock.server.files.stubs    | The classpath for Wiremock files                   | classpath*:/__files  |
| wiremock.server.files.mappings | The classpath for Wiremock stubs                   | classpath*:/mappings |

# License
This project is licensed under the MIT License - see the LICENSE file for details.
