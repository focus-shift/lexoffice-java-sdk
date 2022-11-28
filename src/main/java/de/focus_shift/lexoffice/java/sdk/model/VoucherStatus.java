package de.focus_shift.lexoffice.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum VoucherStatus {

    /**
     * Voucher is created but not yet final. It is still editable in lexoffice.
     */
    DRAFT("draft"),

    /**
     * Voucher is finalized in lexoffice and no longer editable but yet unpaid or only partially paid.
     */
    OPEN("open"),

    /**
     * Voucher is marked as fully paid in lexoffice.
     */
    PAID("paid"),

    /**
     * Only used for credit notes. Credit note is marked as paidoff in lexoffice.
     */
    PAIDOFF("paidoff"),

    /**
     * Voucher is cancelled.
     */
    VOIDED("voided"),

    /**
     * Voucher is transferred via the lexoffice online banking connector. When the payment is handled by the bank this status changes to paid.
     */
    TRANSFERRED("transferred"),

    /**
     * The payment has already been authorized or the amount will be collected by direct debit (direct withdrawal). When the payment is handled by the bank this status changes to paid.
     */
    SEPADEBIT("sepadebit"),

    /**
     * Voucher is open and dueDate is in the past.
     */
    OVERDUE("overdue", true),

    /**
     * Only used for quotations. This status is set when a quotation was marked as accepted in lexoffice.
     */
    ACCEPTED("accepted"),

    /**
     * Only used for quotations. This status is set when a quotation was marked as rejected in lexoffice.
     */
    REJECTED("rejected");

    @Getter
    @JsonValue
    private String value;

    @Getter
    private boolean exclusive;

    VoucherStatus(String value) {
        this(value, false);
    }

    VoucherStatus(String value, boolean exclusive) {
        this.value = value;
        this.exclusive = exclusive;
    }

    public static VoucherStatus[] valuesNonExclusive() {
        return Arrays.asList(values()).stream().filter(v -> !v.isExclusive()).collect(Collectors.toList()).toArray(new VoucherStatus[]{});
    }

}
