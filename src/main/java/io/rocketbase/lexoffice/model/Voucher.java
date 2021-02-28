package io.rocketbase.lexoffice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Voucher {

    @JsonProperty("id")
    private String id;

    @JsonProperty("voucherType")
    private VoucherType voucherType;

    @JsonProperty("voucherStatus")
    private VoucherStatus voucherStatus;

    @JsonProperty("voucherNumber")
    private String voucherNumber;

    @JsonProperty("voucherDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date voucherDate;

    @JsonProperty("updatedDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date updatedDate;

    @JsonProperty("dueDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date dueDate;

    @JsonProperty("contactName")
    private String contactName;

    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;

    @JsonProperty("currency")
    private Currency currency;

    @JsonProperty("archived")
    private boolean archived;
}
