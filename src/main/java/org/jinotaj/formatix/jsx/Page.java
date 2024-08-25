package org.jinotaj.formatix.jsx;

import org.graalvm.polyglot.Value;

import java.util.Map;
import java.util.Optional;

public class Page {
    private final ReactRenderer reactRenderer;
    private final Value pageModule;

    public Page(ReactRenderer reactRenderer, Value pageModule) {
        this.reactRenderer = reactRenderer;
        this.pageModule = pageModule;
    }

    public String renderFo(Object data) {
        Value pageComponent = pageModule.getMember("default");
        return reactRenderer.render(pageComponent, data);
    }

    public Optional<Map<String, ?>> getMetadata(Object data) {
        Value metadataFunction = pageModule.getMember("metadata");
        if (metadataFunction == null) {
            return Optional.empty();
        }
        return Optional.of(metadataFunction.execute(data).as(Map.class));
    }
}
