package org.jinotaj.formatix.fop;

import io.micronaut.context.annotation.ConfigurationProperties;

import java.nio.file.Path;

/**
 * @author Filip Jirsák
 */
@ConfigurationProperties("fop")
public class FopConfig {
    private Path dir;

    public Path getDir() {
        return dir;
    }

    public void setDir(Path dir) {
        this.dir = dir;
    }
}
