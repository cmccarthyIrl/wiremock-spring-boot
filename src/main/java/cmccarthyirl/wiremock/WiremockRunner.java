package cmccarthyirl.wiremock;

import com.atlassian.ta.wiremockpactgenerator.WireMockPactGenerator;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import java.util.logging.Logger;

@SpringBootApplication
@AutoConfigureWireMock
@ComponentScan(basePackages = {"cmccarthyirl"})
public class WiremockRunner extends SpringBootServletInitializer {

    Logger logger = Logger.getLogger(WiremockRunner.class.getName());

    @Autowired
    private WireMockServer wireMockServer;

    public static void main(String[] args) {
        SpringApplication.run(WiremockRunner.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    private void printDetails() {
        logger.info("WireMock server started on port " + wireMockServer.port());
        logger.info("WireMock URL: " + wireMockServer.baseUrl());
        wireMockServer.addMockServiceRequestListener(WireMockPactGenerator.builder("the-consumer", "the-provider").build());
        wireMockServer.start();
    }
}
