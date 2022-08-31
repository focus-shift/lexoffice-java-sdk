package de.focus_shift.lexoffice.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Addresses {

    @Singular
    @JsonProperty("billing")
    private List<Address> billings;

    @Singular
    @JsonProperty("shipping")
    private List<Address> shippings;
}
