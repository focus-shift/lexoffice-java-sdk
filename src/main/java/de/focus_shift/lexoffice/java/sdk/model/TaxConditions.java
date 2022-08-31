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
public class TaxConditions {

    @JsonProperty("taxType")
    private TaxType taxType;

    @JsonProperty("taxTypeNote")
    private String taxTypeNote;

}
