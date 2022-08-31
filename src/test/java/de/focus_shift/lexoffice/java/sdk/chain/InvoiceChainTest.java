package de.focus_shift.lexoffice.java.sdk.chain;


import de.focus_shift.lexoffice.java.sdk.LexofficeApi;
import de.focus_shift.lexoffice.java.sdk.model.Address;
import de.focus_shift.lexoffice.java.sdk.model.Currency;
import de.focus_shift.lexoffice.java.sdk.model.Invoice;
import de.focus_shift.lexoffice.java.sdk.model.LineItem;
import de.focus_shift.lexoffice.java.sdk.model.LineItemType;
import de.focus_shift.lexoffice.java.sdk.model.PaymentConditions;
import de.focus_shift.lexoffice.java.sdk.model.ShippingConditions;
import de.focus_shift.lexoffice.java.sdk.model.ShippingType;
import de.focus_shift.lexoffice.java.sdk.model.TaxConditions;
import de.focus_shift.lexoffice.java.sdk.model.TaxType;
import de.focus_shift.lexoffice.java.sdk.model.TotalPrice;
import de.focus_shift.lexoffice.java.sdk.model.UnitPrice;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

class InvoiceChainTest {

    @Test
    @Disabled
    void exampleUsage() {
        LexofficeApi lexofficeApi = Mockito.mock(LexofficeApi.class);

        int daysToPay = 14;
        Invoice invoice = Invoice.builder()
                .title("Rechnung")
                .introduction("vielen Dank für Ihren Auftrag, den wir wie folgt berechnen:")
                .remark("Besten Dank und freundliche Grüße,")
                .language("de")
                .archived(false)
                .voucherDate(new Date())
                .paymentConditions(PaymentConditions.builder()
                        .paymentTermLabel(String.format("Zahlung innerhalb von %d Tagen ab Rechnungseingang ohne Abzüge.", daysToPay))
                        .paymentTermDuration(daysToPay)
                        .build())
                .address(Address.builder()
                        .contactId("contact-uuid")
                        .build())
                .totalPrice(TotalPrice.builder()
                        .currency(Currency.EUR)
                        .totalNetAmount(BigDecimal.valueOf(31429, 2))
                        .build())
                .shippingConditions(ShippingConditions.builder()
                        .shippingDate(new Date())
                        .shippingType(ShippingType.DELIVERY)
                        .build())
                .taxConditions(new TaxConditions(TaxType.NET, ""))
                .lineItems(Arrays.asList(LineItem.builder()
                                .type(LineItemType.CUSTOM)
                                .name("Name")
                                .description("Eine längere Beschreibung\nMehrzeilig funktioniert auch...")
                                .unitName("Stück")
                                .unitPrice(UnitPrice.builder()
                                        .netAmount(BigDecimal.valueOf(5055, 2))
                                        .taxRatePercentage(19)
                                        .currency(Currency.EUR)
                                        .build())
                                .lineItemAmount(BigDecimal.valueOf(30330, 2))
                                .quantity(BigDecimal.valueOf(6))
                                .build(),
                        LineItem.builder()
                                .type(LineItemType.CUSTOM)
                                .name("Second")
                                .unitName("Stück")
                                .unitPrice(UnitPrice.builder()
                                        .netAmount(BigDecimal.valueOf(1099, 2))
                                        .taxRatePercentage(19)
                                        .currency(Currency.EUR)
                                        .build())
                                .lineItemAmount(BigDecimal.valueOf(1099, 2))
                                .quantity(BigDecimal.valueOf(1))
                                .build()))
                .build();

        lexofficeApi.invoice().create().submit(invoice);
    }
}
