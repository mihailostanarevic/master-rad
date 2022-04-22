package ftn.uns.ac.rs.masterrad.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class DownloadFileResponse {

    private String preSignedUrl;
}
