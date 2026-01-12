package de.octalog.lexoffice.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sort {

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("property")
    private String property;

    @JsonProperty("ignoreCase")
    private Boolean ignoreCase;

    @JsonProperty("nullHandling")
    private String nullHandling;

    @JsonProperty("ascending")
    private Boolean ascending;

}
