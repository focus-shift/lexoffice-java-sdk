package de.octalog.lexoffice.java.sdk.chain;

import de.octalog.lexoffice.java.sdk.RequestContext;
import de.octalog.lexoffice.java.sdk.model.Invoice;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
public class InvoiceChain {

    private final RequestContext context;

    public Invoice get(String id) {
        return new Get(context).get(id);
    }

    public Create create() {
        return new Create(context);
    }

    protected static class Get extends ExecutableRequestChain {
        private static final ParameterizedTypeReference<Invoice> TYPE_REFERENCE = new ParameterizedTypeReference<Invoice>() {
        };

        public Get(RequestContext context) {
            super(context, "/invoices");
        }

        @SneakyThrows
        public Invoice get(String id) {
            getUriBuilder().appendPath("/" + id);
            return getContext().execute(getUriBuilder(), HttpMethod.GET, TYPE_REFERENCE);
        }
    }

    public static class Create extends ExecutableRequestChain {
        private static final ParameterizedTypeReference<Invoice> TYPE_REFERENCE = new ParameterizedTypeReference<Invoice>() {
        };

        public Create(RequestContext context) {
            super(context, "/invoices");
        }

        public Create finalize(Boolean finalize) {
            super.getUriBuilder()
                    .addParameter("finalize", finalize);
            return this;
        }

        @SneakyThrows
        public Invoice submit(Invoice invoice) {
            return getContext().execute(getUriBuilder(), HttpMethod.POST, invoice, TYPE_REFERENCE);
        }
    }

}
