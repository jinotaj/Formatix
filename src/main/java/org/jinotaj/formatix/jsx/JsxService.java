package org.jinotaj.formatix.jsx;

import io.micronaut.runtime.context.scope.ThreadLocal;
import jakarta.annotation.PreDestroy;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.io.IOException;

@ThreadLocal
public class JsxService {
    private final Context context;
    private final ReactRenderer reactRenderer;
    private final File root;

    public JsxService(ContentConfig config, ContextService contextService) throws IOException {
        root = config.getRoot().toFile();
        context = contextService.createContext();
        reactRenderer = new ReactRenderer(loadModule("index.js"));
    }

    public Page load(String path) throws IOException {
        Value pageModule = loadModule(String.format("/pages/%s.js", path));
        return new Page(reactRenderer, pageModule);
    }

    private Value loadModule(String path) throws IOException {
        Source source = Source.newBuilder("js", new File(root, path))
                .mimeType("application/javascript+module")
                .build();
        return context.eval(source);
    }

    @PreDestroy
    public void close() {
        context.close();
    }
}
