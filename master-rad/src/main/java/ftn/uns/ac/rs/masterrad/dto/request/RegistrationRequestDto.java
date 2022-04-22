package ftn.uns.ac.rs.masterrad.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDto {

    @NotBlank(message = "Application name is required.")
    @Size(min = 5, max = 50, message = "Application name must be between 5 and 50 characters")
    private String applicationName;
}
