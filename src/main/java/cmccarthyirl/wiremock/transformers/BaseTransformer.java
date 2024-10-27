package cmccarthyirl.wiremock.transformers;

import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;

public abstract class BaseTransformer extends ResponseDefinitionTransformer {

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
