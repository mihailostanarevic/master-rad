package ftn.uns.ac.rs.masterrad.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "registered_applications")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegisteredApplication {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column(unique = true, name = "application_name")
    private String applicationName;

    @Column(unique = true, name = "client_id")
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;
}
