package de.octalog.lexware.java.sdk.model;

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
public class PaymentDiscountConditions {

    @JsonProperty("discountPercentage")
    private BigDecimal discountPercentage;

    @JsonProperty("discountRange")
    private Integer discountRange;

}
