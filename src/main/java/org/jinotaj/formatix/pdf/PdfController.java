package org.jinotaj.formatix.pdf;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;
import java.util.Map;

@Controller
public class PdfController {
    private final PdfService pdfService;
    private final ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @Status(HttpStatus.CREATED)
    @Post(
            uri = "/{+path}",
            produces = MediaType.APPLICATION_PDF,
            consumes = MediaType.APPLICATION_JSON
    )
    @ExecuteOn(TaskExecutors.IO)
    public ByteBuf renderPDF(@PathVariable String path, @Body Map<String, Object> data) throws IOException {
        ByteBuf byteBuf = allocator.ioBuffer(128);
        ByteBufOutputStream outputStream = new ByteBufOutputStream(byteBuf);
        pdfService.render(path, data, outputStream);
        return byteBuf;
    }

    @Status(HttpStatus.CREATED)
    @Post(
            uri = "/{+path}",
            produces = MediaType.APPLICATION_XML,
            consumes = MediaType.APPLICATION_JSON
    )
    public String renderFO(@PathVariable String path, @Body Map<String, Object> data) throws IOException {
        return pdfService.renderFO(path, data);
    }
}
