package ftn.uns.ac.rs.masterrad.service;

import ftn.uns.ac.rs.masterrad.domain.RegisteredApplication;
import ftn.uns.ac.rs.masterrad.dto.request.RegistrationRequestDto;
import ftn.uns.ac.rs.masterrad.dto.response.RegistrationResponseDto;
import ftn.uns.ac.rs.masterrad.exceptions.ApplicationNameUniquenessException;
import ftn.uns.ac.rs.masterrad.repository.RegisteredApplicationsJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegisteredApplicationsJpaRepository registeredApplicationsJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    public RegistrationResponseDto register(final RegistrationRequestDto registrationRequestDto)
        throws ApplicationNameUniquenessException {
        final var app = RegisteredApplication.builder()
            .applicationName(registrationRequestDto.getApplicationName())
            .build();
        final var id = UUID.randomUUID();
        final var clientId = new StringBuilder("APP-").append(id.toString().split("-")[0]);
        final var response =
            restTemplate.getForEntity("https://www.passwordrandom.com/query?command=password", String.class);
        final var encodedSecret = passwordEncoder.encode(response.getBody());
        final var appToSave = app.toBuilder()
            .clientId(clientId.toString())
            .clientSecret(encodedSecret)
            .build();
        try {
            registeredApplicationsJpaRepository.save(appToSave);
        } catch (final Exception e){
            throw new ApplicationNameUniquenessException(registrationRequestDto.getApplicationName());
        }
        return new RegistrationResponseDto(clientId.toString(), response.getBody());
    }

    public Optional<String> getApplicationName(final String clientId) {
        return registeredApplicationsJpaRepository.findByClientId(clientId).map(RegisteredApplication::getApplicationName);
    }

    public Optional<RegisteredApplication> getApplicationByClientId(final String clientId) {
        return registeredApplicationsJpaRepository.findByClientId(clientId);
    }
}
