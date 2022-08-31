package de.focus_shift.lexoffice.java.sdk.model;

import lombok.Getter;

public enum EventTypeResource {

    CONTACTS("contacts"),
    CREDIT_NOTES("credit-notes"),
    DELIVERY_NOTES("delivery notes"),
    DOWN_PAYMENT_INVOICES("down-payment-invoices"),
    DUNNINGS("dunnings"),
    INVOICES("invoices"),
    ORDER_CONFIRMATIONS("order-confirmations"),
    QUOTATIONS("quotations"),
    RECURRING_TEMPLATES("recurring-templates"),
    REVOKE("revoke"),
    VOUCHERS("vouchers");

    @Getter
    private String value;


    EventTypeResource(String value) {
        this.value = value;
    }

}
