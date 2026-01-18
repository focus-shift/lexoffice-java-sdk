package de.octalog.lexware.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum ExecutionInterval {

    WEEKLY("WEEKLY"),
    MONTHLY("MONTHLY"),
    QUARTERLY("QUARTERLY"),
    ANNUALLY("ANNUALLY");

    @Getter
    @JsonValue
    private String value;

    ExecutionInterval(String value) {
        this.value = value;
    }
}
