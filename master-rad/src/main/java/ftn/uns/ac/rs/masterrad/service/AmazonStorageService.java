package ftn.uns.ac.rs.masterrad.service;

import ftn.uns.ac.rs.masterrad.domain.RegisteredApplication;
import ftn.uns.ac.rs.masterrad.dto.response.DownloadFileResponse;
import ftn.uns.ac.rs.masterrad.dto.response.GenericResponse;
import ftn.uns.ac.rs.masterrad.exceptions.InvalidCredentialsException;
import ftn.uns.ac.rs.masterrad.exceptions.NoSuchApplicationException;
import ftn.uns.ac.rs.masterrad.service.clients.AmazonClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AmazonStorageService {

    public GenericResponse storeZippedLogsOnS3(
        final List<MultipartFile> filesToStore,
        final HttpServletRequest request
    ) throws InvalidCredentialsException {
        final var clientId = request.getHeader("X-CLIENT-ID");
        final var clientSecret = request.getHeader("X-CLIENT-SECRET");
        final var app = checkCredentials(clientId, clientSecret);
        filesToStore.stream().forEach(file -> amazonClient.uploadFile(file, app.getApplicationName()));
        return GenericResponse.builder().message("Your files have been uploaded to our cloud storage and can be downloaded at any time").build();
    }

    public DownloadFileResponse downloadFile(
        final String fileName,
        final HttpServletRequest httpServletRequest
    ) throws InvalidCredentialsException {
        final var clientId = httpServletRequest.getHeader("X-CLIENT-ID");
        final var clientSecret = httpServletRequest.getHeader("X-CLIENT-SECRET");
        final var app = checkCredentials(clientId, clientSecret);
        final var preSignedUrl = amazonClient.generatePreSignedURL(
            fileName,
            app.getApplicationName()
        );
        return DownloadFileResponse.builder().preSignedUrl(preSignedUrl).build();
    }

    private RegisteredApplication checkCredentials(String clientId, String clientSecret) throws InvalidCredentialsException {
        final var appOptional = registrationService.getApplicationByClientId(clientId);
        if (appOptional.isEmpty()) {
            throw new InvalidCredentialsException();
        }

        final var app = appOptional.get();
        if (!passwordEncoder.matches(clientSecret, app.getClientSecret())) {
            throw new InvalidCredentialsException();
        }
        return app;
    }

    private final AmazonClient amazonClient;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationService registrationService;
}
