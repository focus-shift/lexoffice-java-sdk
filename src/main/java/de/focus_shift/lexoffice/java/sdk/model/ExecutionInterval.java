package de.focus_shift.lexoffice.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum ExecutionInterval {

    WEEKLY("WEEKLY"),
    BIWEEKLY("BIWEEKLY"),
    MONTHLY("MONTHLY"),
    QUARTERLY("QUARTERLY"),
    BIANNUALLY("BIANNUALLY"),
    ANNUALLY("ANNUALLY");

    @Getter
    @JsonValue
    private String value;

    ExecutionInterval(String value) {
        this.value = value;
    }
}
