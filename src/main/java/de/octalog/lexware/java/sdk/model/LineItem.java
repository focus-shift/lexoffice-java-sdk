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
public class LineItem {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private LineItemType type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("quantity")
    private BigDecimal quantity;

    @JsonProperty("unitName")
    private String unitName;

    @JsonProperty("unitPrice")
    private UnitPrice unitPrice;

    @JsonProperty("discountPercentage")
    private Long discountPercentage;

    @JsonProperty("lineItemAmount")
    private BigDecimal lineItemAmount;

}
