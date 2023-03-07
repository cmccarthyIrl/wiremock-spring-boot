package cmccarthyirl.wiremock.utils;

import com.github.tomakehurst.wiremock.core.Admin;
import com.github.tomakehurst.wiremock.extension.PostServeAction;
import com.github.tomakehurst.wiremock.http.LoggedResponse;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

public class ResponseLoggingPostServeAction extends PostServeAction {

    private static final String APPLICATION_JSON = "application/json";

    @Override
    public void doGlobalAction(ServeEvent serveEvent, Admin admin) {
        if (serveEvent.getResponseDefinition().isProxyResponse()) {
            LoggedResponse response = serveEvent.getResponse();
            System.out.println("PROXY-RESP: Status: " + response.getStatus());
            logHeaders(response);
            String mimeType = response.getMimeType();
            System.out.println("PROXY-RESP: MimeType: " + mimeType);
            if (APPLICATION_JSON.equals(mimeType)) {
                logJsonBody(response);
            } else {
                System.out.println("PROXY-RESP: Body-Binary: " + response.getBodyAsBase64());
            }
        }
    }

    private void logHeaders(LoggedResponse response) {
        System.out.println("PROXY-RESP: Headers: " + response.getHeaders().all().toString());
    }

    private void logJsonBody(LoggedResponse response) {
        if (response.getHeaders().keys().contains("Content-Encoding") && response.getHeaders().getHeader("Content-Encoding").firstValue().equals("gzip")) {
            try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(response.getBody()))) {
                System.out.println("PROXY-RESP: Body-Text-Gzip: " + new String(gzipInputStream.readAllBytes(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                System.out.println("PROXY-RESP: Failed to decode gzip encoded content" + e);
            }
        } else {
            System.out.println("PROXY-RESP: Body-Text: " + response.getBodyAsString());
        }
    }

    @Override
    public String getName() {
        return "response-logging";
    }
}