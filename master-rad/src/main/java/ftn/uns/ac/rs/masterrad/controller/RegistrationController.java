package ftn.uns.ac.rs.masterrad.controller;

import ftn.uns.ac.rs.masterrad.dto.request.RegistrationRequestDto;
import ftn.uns.ac.rs.masterrad.dto.response.RegistrationResponseDto;
import ftn.uns.ac.rs.masterrad.exceptions.ApplicationNameUniquenessException;
import ftn.uns.ac.rs.masterrad.service.RegistrationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/register")
public class RegistrationController {

    @ApiOperation(
            value = "Registration",
            response = RegistrationResponseDto.class,
            httpMethod = "POST"
    )
    @PostMapping
    public final ResponseEntity<RegistrationResponseDto> register(
        @RequestBody final RegistrationRequestDto request
    ) throws ApplicationNameUniquenessException {
        return ResponseEntity.ok(registrationService.register(request));
    }

    private final RegistrationService registrationService;
}
