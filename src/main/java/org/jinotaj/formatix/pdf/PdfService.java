package org.jinotaj.formatix.pdf;

import jakarta.inject.Singleton;
import org.jinotaj.formatix.fop.FopService;
import org.jinotaj.formatix.jsx.JsxService;
import org.jinotaj.formatix.jsx.Page;

import java.io.IOException;
import java.io.OutputStream;

@Singleton
public class PdfService {
    private final FopService fopService;
    private final JsxService jsxService;

    public PdfService(FopService fopService, JsxService jsxService) {
        this.fopService = fopService;
        this.jsxService = jsxService;
    }

    public void render(String path, Object data, OutputStream outputStream) throws IOException {
        Page page = jsxService.load(path);
        String fo = page.renderFo(data);
        fopService.render(fo, outputStream);
    }

    public String renderFO(String path, Object data) throws IOException {
        Page page = jsxService.load(path);
        return page.renderFo(data);
    }
}
