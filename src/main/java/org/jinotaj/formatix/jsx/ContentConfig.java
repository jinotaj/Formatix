package org.jinotaj.formatix.jsx;

import io.micronaut.context.annotation.ConfigurationProperties;

import java.nio.file.Path;

/**
 * @author Filip Jirsák
 */
@ConfigurationProperties("content")
public class ContentConfig {
    private Path root;

    public Path getRoot() {
        return root;
    }

    public void setRoot(Path root) {
        this.root = root;
    }
}
