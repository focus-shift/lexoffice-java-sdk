package de.octalog.lexware.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailAddresses {

    @Singular
    @JsonProperty("business")
    private List<String> businesses;

    @Singular
    @JsonProperty("office")
    private List<String> offices;

    @Singular
    @JsonProperty("private")
    private List<String> privates;

    @Singular
    @JsonProperty("other")
    private List<String> others;
}
