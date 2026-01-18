package de.octalog.lexware.java.sdk.chain;


import de.octalog.lexware.java.sdk.LexwareApi;
import de.octalog.lexware.java.sdk.model.Address;
import de.octalog.lexware.java.sdk.model.Currency;
import de.octalog.lexware.java.sdk.model.Invoice;
import de.octalog.lexware.java.sdk.model.LineItem;
import de.octalog.lexware.java.sdk.model.LineItemType;
import de.octalog.lexware.java.sdk.model.PaymentConditions;
import de.octalog.lexware.java.sdk.model.ShippingConditions;
import de.octalog.lexware.java.sdk.model.ShippingType;
import de.octalog.lexware.java.sdk.model.TaxConditions;
import de.octalog.lexware.java.sdk.model.TaxType;
import de.octalog.lexware.java.sdk.model.TotalPrice;
import de.octalog.lexware.java.sdk.model.UnitPrice;
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
        LexwareApi lexwareApi = Mockito.mock(LexwareApi.class);

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
                .taxConditions(TaxConditions.builder().taxType(TaxType.NET).build())
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

        lexwareApi.invoice().create().submit(invoice);
    }
}
