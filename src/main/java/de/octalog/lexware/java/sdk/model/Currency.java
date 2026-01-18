package de.octalog.lexware.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum Currency {

    EUR("EUR");

    @Getter
    @JsonValue
    private String value;

    Currency(String value) {
        this.value = value;
    }

}
