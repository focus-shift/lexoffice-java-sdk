package de.octalog.lexware.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum ShippingType {

    /**
     * a service is supplied on shippingDate
     */
    SERVICE("service"),
    /**
     * a service is supplied within the period [shippingDate,shippingEndDate]
     */
    SERVICEPERIOD("serviceperiod"),
    /**
     * a product is delivered
     */
    DELIVERY("delivery"),
    /**
     * a product is delivered within the period [shippingDate,shippingEndDate]
     */
    DELIVERYPERIOD("deliveryperiod"),
    /**
     * no shipping date has to be provided
     */
    NONE("none");

    @Getter
    @JsonValue
    private String value;

    ShippingType(String value) {
        this.value = value;
    }
}
