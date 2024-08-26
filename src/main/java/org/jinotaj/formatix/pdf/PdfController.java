package org.jinotaj.formatix.pdf;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Controller
public class PdfController {
    private final PdfService pdfService;
    private final ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @Post("/{+path}")
    @Status(HttpStatus.CREATED)
    @Produces(MediaType.APPLICATION_PDF)
    @Consumes(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public byte[] renderPDF(@PathVariable String path, @Body Map<String, Object> data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        pdfService.render(path, data, outputStream);
        return outputStream.toByteArray();
    }

    @Post("/{+path}")
    @Status(HttpStatus.CREATED)
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_JSON)
    public String renderFO(@PathVariable String path, @Body Map<String, Object> data) throws IOException {
        return pdfService.renderFO(path, data);
    }
}
