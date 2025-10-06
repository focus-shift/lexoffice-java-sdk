package de.focus_shift.lexoffice.java.sdk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentFile {

    private byte[] content;
    private MediaType contentType;
    private Long contentLength;
    private String filename;
}
