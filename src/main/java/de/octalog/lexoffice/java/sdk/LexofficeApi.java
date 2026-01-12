package de.octalog.lexoffice.java.sdk;

import de.octalog.lexoffice.java.sdk.chain.ContactChain;
import de.octalog.lexoffice.java.sdk.chain.EventSubscriptionChain;
import de.octalog.lexoffice.java.sdk.chain.InvoiceChain;
import de.octalog.lexoffice.java.sdk.chain.QuotationChain;
import de.octalog.lexoffice.java.sdk.chain.VoucherListChain;
import de.octalog.lexoffice.java.sdk.chain.RecurringTemplateChain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LexofficeApi {

    private final RequestContext context;

    public static DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    LexofficeApi(RequestContext context) {
        this.context = context;
    }

    public VoucherListChain voucherList() {
        return new VoucherListChain(context);
    }

    public ContactChain contact() {
        return new ContactChain(context);
    }

    public InvoiceChain invoice() {
        return new InvoiceChain(context);
    }

    public QuotationChain quotation() {
        return new QuotationChain(context);
    }


    public EventSubscriptionChain eventSubscriptions() {
        return new EventSubscriptionChain(context);
    }

    public RecurringTemplateChain recurringTemplates() {
        return new RecurringTemplateChain(context);
    }

}
