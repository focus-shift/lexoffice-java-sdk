package de.octalog.lexoffice.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxAmount {

    @JsonProperty("taxRatePercentage")
    private Long taxRatePercentage;

    @JsonProperty("taxAmount")
    private BigDecimal taxAmount;

    @JsonProperty("netAmount")
    private BigDecimal netAmount;

}
