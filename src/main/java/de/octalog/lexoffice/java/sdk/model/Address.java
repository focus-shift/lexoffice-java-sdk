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
public class Address {

    @JsonProperty("contactId")
    private String contactId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("supplement")
    private String supplement;

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("zip")
    private String zip;

    /**
     * Country code in the format of ISO 3166 alpha2
     */
    @JsonProperty("countryCode")
    private String countryCode;

    public Address(String contactId) {
        this.contactId = contactId;
    }
}
