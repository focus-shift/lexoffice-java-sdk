package de.focus_shift.lexoffice.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum ExecutionStatus {

    ACTIVE("ACTIVE"),
    PAUSED("PAUSED"),
    ENDED("ENDED");

    @Getter
    @JsonValue
    private String value;

    ExecutionStatus(String value) {
        this.value = value;
    }
}
