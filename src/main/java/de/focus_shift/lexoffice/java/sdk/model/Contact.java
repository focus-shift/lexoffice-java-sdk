package de.focus_shift.lexoffice.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact {

    @JsonProperty("id")
    private String id;

    @JsonProperty("organizationId")
    private String organizationId;

    @JsonProperty("version")
    private Long version;

    @JsonProperty("roles")
    private Roles roles;

    @JsonProperty("person")
    private ContactPerson person;

    @JsonProperty("company")
    private Company company;

    @JsonProperty("addresses")
    private Addresses addresses;

    @JsonProperty("emailAddresses")
    private EmailAddresses emailAddresses;

    @JsonProperty("phoneNumbers")
    private PhoneNumbers phoneNumbers;

    @JsonProperty("note")
    private String note;

    @JsonProperty("archived")
    private boolean archived;
}
