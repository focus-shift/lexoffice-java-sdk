package de.focus_shift.lexoffice.java.sdk.model;

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
public class UnitPrice {

    @JsonProperty("currency")
    private Currency currency;

    @JsonProperty("netAmount")
    private BigDecimal netAmount;

    @JsonProperty("grossAmount")
    private BigDecimal grossAmount;

    @JsonProperty("taxRatePercentage")
    private Integer taxRatePercentage;
}
