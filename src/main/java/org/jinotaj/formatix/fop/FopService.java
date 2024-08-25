package org.jinotaj.formatix.fop;

import jakarta.inject.Singleton;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

@Singleton
public class FopService {
    private final Logger logger = LoggerFactory.getLogger(FopService.class);

    private final FopFactory fopFactory;
    private final TransformerFactory transformerFactory;

    public FopService(FopConfig config) throws IOException, SAXException {
//        FopFactoryBuilder fopFactoryBuilder = new FopFactoryBuilder();
//        this.fopFactory = fopFactoryBuilder.build();
        fopFactory = FopFactory.newInstance(config.getDir().resolve("fop.xconf").toFile());
        transformerFactory = TransformerFactory.newInstance();
    }

    public void render(String fo, OutputStream outputStream) throws IOException {
        try {
            Fop fop = createFop(outputStream);

            final Transformer transformer = transformerFactory.newTransformer();
            final Source source = new StreamSource(new StringReader(fo));
            final Result result = new SAXResult(fop.getDefaultHandler());

            transformer.transform(source, result);
        } catch (FOPException | TransformerException e) {
            throw new IOException(e);
        }
    }

    private Fop createFop(OutputStream out) throws FOPException {
        return fopFactory.newFop(MimeConstants.MIME_PDF, out);
    }
}
