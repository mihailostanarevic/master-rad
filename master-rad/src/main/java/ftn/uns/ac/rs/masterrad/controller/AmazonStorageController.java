package ftn.uns.ac.rs.masterrad.controller;

import ftn.uns.ac.rs.masterrad.dto.request.DownloadFileRequest;
import ftn.uns.ac.rs.masterrad.dto.response.DownloadFileResponse;
import ftn.uns.ac.rs.masterrad.dto.response.GenericResponse;
import ftn.uns.ac.rs.masterrad.exceptions.InvalidCredentialsException;
import ftn.uns.ac.rs.masterrad.exceptions.NoSuchApplicationException;
import ftn.uns.ac.rs.masterrad.service.AmazonStorageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/storage")
public class AmazonStorageController {

    @ApiOperation(
            value = "Upload file to S3",
            response = GenericResponse.class,
            httpMethod = "POST"
    )
    @PostMapping("/upload")
    public final ResponseEntity<GenericResponse> uploadFiles(
        @RequestPart("files")
        final List<MultipartFile> files,
        final HttpServletRequest httpServletRequest
    ) throws InvalidCredentialsException {
        return ResponseEntity.ok(amazonStorageService.storeZippedLogsOnS3(files, httpServletRequest));
    }

    @ApiOperation(
            value = "Download file from S3",
            response = GenericResponse.class,
            httpMethod = "GET"
    )
    @GetMapping("/download")
    public final ResponseEntity<DownloadFileResponse> downloadFile(
        @RequestParam(value = "fileName")
        final String fileName,
        final HttpServletRequest httpServletRequest
    ) throws InvalidCredentialsException {
        return ResponseEntity.ok(amazonStorageService.downloadFile(fileName, httpServletRequest));
    }

    private final AmazonStorageService amazonStorageService;
}
