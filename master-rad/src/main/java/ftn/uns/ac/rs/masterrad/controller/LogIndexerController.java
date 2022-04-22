package ftn.uns.ac.rs.masterrad.controller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import ftn.uns.ac.rs.masterrad.dto.response.GenericResponse;
import ftn.uns.ac.rs.masterrad.exceptions.InvalidCredentialsException;
import ftn.uns.ac.rs.masterrad.service.LogIndexerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logs")
public class LogIndexerController {

    @ApiOperation(
        value = "Index logs from client application",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        response = GenericResponse.class
    )
    @PostMapping("/index")
    public final ResponseEntity<GenericResponse> indexClientLogs(
        @ApiParam(value = "Log file")
        @RequestParam("file")
        final MultipartFile clientLogFile,
        final HttpServletRequest httpServletRequest
    ) throws IOException, InvalidCredentialsException {
        return ResponseEntity.ok(logIndexerService.indexClientLogs(clientLogFile, httpServletRequest));
    }

    private final LogIndexerService logIndexerService;
}
