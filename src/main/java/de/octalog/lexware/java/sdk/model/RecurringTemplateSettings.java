package de.octalog.lexware.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecurringTemplateSettings {

    @JsonProperty("id")
    private String id;

    @JsonProperty("startDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonProperty("endDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    @JsonProperty("finalize")
    private Boolean finalize;

    @JsonProperty("shippingType")
    private ShippingType shippingType;

    @JsonProperty("retroactiveInvoice")
    private Boolean retroactiveInvoice;

    @JsonProperty("executionInterval")
    private ExecutionInterval executionInterval;

    @JsonProperty("lastExecutionFailed")
    private Boolean lastExecutionFailed;

    @JsonProperty("lastExecutionErrorMessage")
    private String lastExecutionErrorMessage;

    @JsonProperty("executionStatus")
    private ExecutionStatus executionStatus;

    @JsonProperty("lastExecutionDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date lastExecutionDate;

    @JsonProperty("nextExecutionDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date nextExecutionDate;
}
