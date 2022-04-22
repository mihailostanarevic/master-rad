package ftn.uns.ac.rs.masterrad.repository;

import ftn.uns.ac.rs.masterrad.domain.RegisteredApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegisteredApplicationsJpaRepository extends JpaRepository<RegisteredApplication, UUID> {

    RegisteredApplication findByApplicationName(final String applicationName);

    Optional<RegisteredApplication> findByClientId(final String clientId);
}
