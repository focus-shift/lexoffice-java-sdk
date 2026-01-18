package de.octalog.lexware.java.sdk.model;

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

    @JsonProperty("paymentTermLabelTemplate")
    private String paymentTermLabelTemplate;

    @JsonProperty("paymentTermDuration")
    private Integer paymentTermDuration;

    @JsonProperty("paymentDiscountConditions")
    private PaymentDiscountConditions paymentDiscountConditions;

}
