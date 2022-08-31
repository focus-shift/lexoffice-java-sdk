package de.focus_shift.lexoffice.java.sdk.model;

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
public class Company {

    @JsonProperty("name")
    private String name;

    @JsonProperty("taxNumber")
    private String taxNumber;

    @JsonProperty("vatRegistrationId")
    private String vatRegistrationId;

    @JsonProperty("allowTaxFreeInvoices")
    private boolean allowTaxFreeInvoices;

    @Singular
    @JsonProperty("contactPersons")
    private List<ContactPerson> contactPersons;
}
