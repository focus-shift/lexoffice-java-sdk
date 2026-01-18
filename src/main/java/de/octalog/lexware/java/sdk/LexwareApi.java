package de.octalog.lexware.java.sdk;

import de.octalog.lexware.java.sdk.chain.ContactChain;
import de.octalog.lexware.java.sdk.chain.EventSubscriptionChain;
import de.octalog.lexware.java.sdk.chain.InvoiceChain;
import de.octalog.lexware.java.sdk.chain.QuotationChain;
import de.octalog.lexware.java.sdk.chain.VoucherListChain;
import de.octalog.lexware.java.sdk.chain.RecurringTemplateChain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LexwareApi {

    private final RequestContext context;

    public static DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    LexwareApi(RequestContext context) {
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
