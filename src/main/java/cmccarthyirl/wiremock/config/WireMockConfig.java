package cmccarthyirl.wiremock.config;

import cmccarthyirl.wiremock.utils.ResponseLoggingPostServeAction;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Configuration
public class WireMockConfig {

    @Bean
    public WireMockServer wireMockServer() {
        return new WireMockServer(wireMockOptions());
    }

    @Bean
    public Options wireMockOptions() {

        FileSource fileSource = new ClasspathFileSource(isRunningFromJar());
        final WireMockConfiguration options = WireMockSpring.options();
        options.fileSource(fileSource);
        options.port(80);
        options.extensions(ResponseTemplateTransformer.builder().global(true)
                .build(), new ResponseLoggingPostServeAction()).notifier(new ConsoleNotifier(true));
        return options;
    }
    public String isRunningFromJar() {
        URL path = WireMockConfig.class.getResource("WireMockConfig.class");
        if (path.toString().startsWith("jar:")) {
            return "BOOT-INF/classes/stub";
        } else {
            return "stub";
        }
    }
}
