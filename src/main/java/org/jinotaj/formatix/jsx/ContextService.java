package org.jinotaj.formatix.jsx;

import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

import java.util.Map;

@Singleton
public class ContextService {
    private final Context.Builder contextBuilder;
    private final Engine engine;


    public ContextService() {
        engine = Engine.newBuilder("js")
                .allowExperimentalOptions(true)
                .option("js.esm-eval-returns-exports", "true")
                .option("js.intl-402", "true")
                .out(System.out)
                .build();
        contextBuilder = Context.newBuilder("js")
                .engine(engine)
                .allowAllAccess(true);
    }

    public Context createContext() {
        Context context = contextBuilder.build();
        Value bindings = context.getBindings("js");
        bindings.putMember("process", Map.of("env", Map.of("NODE_ENV", "production")));
        bindings.putMember("TextEncoder", TextEncoder.class);
        return context;
    }

    @PreDestroy
    public void close() {
        engine.close();
    }

    public static class TextEncoder {
        public String encode(String text) {
            return text;
        }
    }
}
