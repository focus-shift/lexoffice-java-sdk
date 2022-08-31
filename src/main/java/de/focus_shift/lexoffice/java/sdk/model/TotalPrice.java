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
public class TotalPrice {

    @JsonProperty("currency")
    private Currency currency;

    @JsonProperty("totalNetAmount")
    private BigDecimal totalNetAmount;

    @JsonProperty("totalGrossAmount")
    private BigDecimal totalGrossAmount;

    @JsonProperty("totalTaxAmount")
    private BigDecimal totalTaxAmount;

    @JsonProperty("totalDiscountAbsolute")
    private BigDecimal totalDiscountAbsolute;

    @JsonProperty("totalDiscountPercentage")
    private BigDecimal totalDiscountPercentage;
}
