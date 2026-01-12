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
public class Roles {

    @JsonProperty("customer")
    private Role customer;

    @JsonProperty("vendor")
    private Role vendor;
}
