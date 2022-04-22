package ftn.uns.ac.rs.masterrad.utils;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private Date timestamp;
    private String message;

    public ErrorResponse(final Date timestamp, final String message) {
        super();
        this.timestamp = timestamp;
        this.message = message;
    }

}