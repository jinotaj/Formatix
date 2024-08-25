package org.jinotaj.formatix.jsx;

import org.graalvm.polyglot.Value;

public class ReactRenderer {
    private final Value reactRendered;

    public ReactRenderer(Value reactRendered) {
        this.reactRendered = reactRendered;
    }

    public String render(Value pageComponent, Object data) {
        Value response = reactRendered.invokeMember("default", pageComponent, data);
        return response.asString();
    }
}
