package de.focus_shift.lexoffice.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum VoucherType {

    /**
     * Details see Vouchers Endpoint
     */
    SALESINVOICE("salesinvoice"),

    /**
     * Details see Vouchers Endpoint
     */
    SALESCREDITNOTE("salescreditnote"),

    /**
     * Details see Vouchers Endpoint
     */
    PURCHASEINVOICE("purchaseinvoice"),

    /**
     * Details see Vouchers Endpoint
     */
    PURCHASECREDITNOTE("purchasecreditnote"),

    /**
     * Rechnung
     */
    INVOICE("invoice"),

    /**
     * Rechnungskorrektur<br>
     * Details see Invoices endpoint
     */
    CREDITNOTE("creditnote"),

    /**
     * Auftragsbestätigung<br>
     * Details see Order Confirmations Endpoint
     */
    ORDERCONFIRMATION("orderconfirmation"),

    /**
     * Angebote<br>
     * Details see Quotations Endpoint
     */
    QUOTATION("quotation"),

    /**
     * Details see Down Payment Invoice Endpoint
     */
    DOWNPAYMENTINVOICE("downpaymentinvoice");

    @Getter
    @JsonValue
    private String value;

    VoucherType(String value) {
        this.value = value;
    }

}
