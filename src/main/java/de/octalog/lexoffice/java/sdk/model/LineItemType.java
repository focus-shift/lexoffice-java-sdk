package de.octalog.lexoffice.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum LineItemType {
    /**
     * the line item is related to a supply of services
     */
    SERVICE("service"),
    /**
     * the line item is related to a physical product
     */
    MATERIAL("material"),
    /**
     * an item without reference in lexoffice and has no id
     */
    CUSTOM("custom"),
    /**
     * contains only a name and/or a description for informative purposes
     */
    TEXT("text");

    @Getter
    @JsonValue
    private String value;

    LineItemType(String value) {
        this.value = value;
    }
}
