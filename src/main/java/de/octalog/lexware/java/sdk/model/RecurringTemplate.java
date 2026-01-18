package de.octalog.lexware.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecurringTemplate {

    @JsonProperty("id")
    private String id;

    @JsonProperty("organizationId")
    private String organizationId;

    @JsonProperty("createdDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date createdDate;

    @JsonProperty("updatedDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date updatedDate;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("language")
    private String language;

    @JsonProperty("archived")
    private boolean archived;

    @JsonProperty("address")
    private Address address;

    @Singular
    @JsonProperty("lineItems")
    private List<LineItem> lineItems;

    @JsonProperty("totalPrice")
    private TotalPrice totalPrice;

    @Singular
    @JsonProperty("taxAmounts")
    private List<TaxAmount> taxAmounts;

    @JsonProperty("taxConditions")
    private TaxConditions taxConditions;

    @JsonProperty("paymentConditions")
    private PaymentConditions paymentConditions;

    @JsonProperty("title")
    private String title;

    @JsonProperty("introduction")
    private String introduction;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("recurringTemplateSettings")
    private RecurringTemplateSettings recurringTemplateSettings;
}
