package de.octalog.lexware.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum EventType {

    CONTACT_CREATED( "contact.created"),
    CONTACT_CHANGED( "contact.changed"),
    CONTACT_DELETED( "contact.deleted"),

    CREDIT_NOTE_CREATED("credit-note.created"),
    CREDIT_NOTE_CHANGED("credit-note.changed"),
    CREDIT_NOTE_DELETED("credit-note.deleted"),
    CREDIT_NOTE_STATUS_CHANGED("credit-note.status.changed"),

    DELIVERY_NOTE_CREATED("delivery-note.created"),
    DELIVERY_NOTE_CHANGED("delivery-note.changed"),
    DELIVERY_NOTE_DELETED("delivery-note.deleted"),

    DOWN_PAYMENT_INVOICE_CREATED("down-payment-invoice.created"),
    DOWN_PAYMENT_INVOICE_CHANGED("down-payment-invoice.changed"),
    DOWN_PAYMENT_INVOICE_DELETED("down-payment-invoice.deleted"),
    DOWN_PAYMENT_INVOICE_STATUS_CHANGED("down-payment-invoice.status.changed"),

    DUNNING_CREATED("dunning.created"),
    DUNNING_CHANGED("dunning.changed"),
    DUNNING_DELETED("dunning.deleted"),

    INVOICE_CREATED("invoice.created"),
    INVOICE_CHANGED("invoice.changed"),
    INVOICE_DELETED("invoice.deleted"),
    INVOICE_STATUS_CHANGED("invoice.status.changed"),

    ORDER_CONFIRMATION_CREATED("order-confirmation.created"),
    ORDER_CONFIRMATION_CHANGED("order-confirmation.changed"),
    ORDER_CONFIRMATION_DELETED("order-confirmation.deleted"),
    ORDER_CONFIRMATION_STATUS_CHANGED("order-confirmation.status.changed"),

    PAYMENT_CHANGED("payment.changed"),

    QUOTATION_CREATED("quotation.created"),
    QUOTATION_CHANGED("quotation.changed"),
    QUOTATION_DELETED("quotation.deleted"),
    QUOTATION_STATUS_CHANGED("quotation.status.changed"),

    RECURRING_TEMPLATE_CREATED("recurring-template.created"),
    RECURRING_TEMPLATE_CHANGED("recurring-template.changed"),
    RECURRING_TEMPLATE_DELETED("recurring-template.deleted"),

    TOKEN_REVOKED("token.revoked"),

    VOUCHER_CREATED("voucher.created"),
    VOUCHER_CHANGED("voucher.changed"),
    VOUCHER_DELETED("voucher.deleted"),
    VOUCHER_STATUS_CHANGED("voucher.status.changed");

    @Getter
    @JsonValue
    private String value;

    EventType(String value) {
        this.value = value;
    }

}
