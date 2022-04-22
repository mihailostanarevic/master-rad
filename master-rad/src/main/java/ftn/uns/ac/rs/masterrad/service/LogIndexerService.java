package ftn.uns.ac.rs.masterrad.service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import ftn.uns.ac.rs.masterrad.domain.RegisteredApplication;
import ftn.uns.ac.rs.masterrad.dto.response.GenericResponse;
import ftn.uns.ac.rs.masterrad.exceptions.InvalidCredentialsException;
import ftn.uns.ac.rs.masterrad.exceptions.NoSuchApplicationException;
import ftn.uns.ac.rs.masterrad.repository.RegisteredApplicationsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class LogIndexerService {

    public GenericResponse indexClientLogs(
        final MultipartFile clientLogFile,
        final HttpServletRequest httpServletRequest
        ) throws IOException, InvalidCredentialsException {
        final var clientId = httpServletRequest.getHeader("X-CLIENT-ID");
        final var clientSecret = httpServletRequest.getHeader("X-CLIENT-SECRET");
        final var app = checkCredentials(clientId, clientSecret);
        handleLogFile(clientLogFile, app.getApplicationName());
        return GenericResponse.builder()
            .message("Your apps logs have been indexed and are now available on Kibana on this link: http://localhost:5601/app/discover")
            .build();
    }

    private void handleLogFile(final MultipartFile logFile, final String applicationName) throws IOException {
        var path = Path.of(desktopPath, "Logs", applicationName, logFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(logFile.getInputStream(), path.toFile());
    }

    private RegisteredApplication checkCredentials(String clientId, String clientSecret) throws
        InvalidCredentialsException {
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

    private final RegisteredApplicationsRepository registeredApplicationsRepository;
    private final RegistrationService registrationService;
    private final PasswordEncoder passwordEncoder;
    private final String desktopPath = String.format("%s/%s", System.getProperty("user.home"), "Desktop");
}
