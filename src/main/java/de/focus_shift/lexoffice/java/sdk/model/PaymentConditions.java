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
public class PaymentConditions {

    @JsonProperty("paymentTermLabel")
    private String paymentTermLabel;

    @JsonProperty("paymentTermDuration")
    private Integer paymentTermDuration;

    @JsonProperty("paymentDiscountConditions")
    private PaymentDiscountConditions paymentDiscountConditions;

}
