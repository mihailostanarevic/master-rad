package ftn.uns.ac.rs.masterrad.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDto {

    private String clientId;

    private String clientSecret;
}
