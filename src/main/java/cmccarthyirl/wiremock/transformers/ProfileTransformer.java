package cmccarthyirl.wiremock.transformers;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

import java.io.File;
import java.nio.file.Files;

public class ProfileTransformer extends BaseTransformer {


    @Override
    public String getName() {
        return "profile";
    }

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource fileSource, Parameters parameters) {
        String bytes = null;
        try {

            if (request.getUrl().contains("/profile") && request.getMethod().equals(RequestMethod.GET)) {

                switch ("1") {
                    case "1" -> {
                        bytes = Files.readString(
                                new File("profile/1.json")
                                        .toPath());

                    }
                    case "2" -> {
                        bytes = Files.readString(
                                new File("profile/2.json")
                                        .toPath());
                    }
                    case "3" -> {
                        bytes = Files.readString(
                                new File("profile/3.json")
                                        .toPath());
                    }
                    default -> throw new NoSuchFieldException("Could not find a path to take using the key: ");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ResponseDefinitionBuilder()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody(bytes)
                .build();
    }
}