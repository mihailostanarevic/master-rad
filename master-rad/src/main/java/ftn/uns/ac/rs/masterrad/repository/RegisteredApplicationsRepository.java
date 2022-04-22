package ftn.uns.ac.rs.masterrad.repository;

import ftn.uns.ac.rs.masterrad.exceptions.NoSuchApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisteredApplicationsRepository {

    private final PasswordEncoder passwordEncoder;
    private final RegisteredApplicationsJpaRepository repo;

    public String validateApplication(
        final String clientId,
        final String clientSecret
    ) throws NoSuchApplicationException {
        final var registeredApp = repo.findByClientId(clientId)
            .orElseThrow(NoSuchApplicationException::new);
        if (!passwordEncoder.matches(clientSecret, registeredApp.getClientSecret())) {
            throw new NoSuchApplicationException();
        }
        return registeredApp.getApplicationName();
    }
}
