package de.focus_shift.lexoffice.java.sdk.chain;

import de.focus_shift.lexoffice.java.sdk.RequestContext;
import de.focus_shift.lexoffice.java.sdk.model.Invoice;
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

    public byte[] downloadFile(String id) {
        return new DownloadFile(context).download(id);
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

    protected static class DownloadFile extends ExecutableRequestChain {

        public DownloadFile(RequestContext context) {
            super(context, "/invoices");
        }

        @SneakyThrows
        public byte[] download(String id) {
            getUriBuilder().appendPath("/" + id + "/file");
            return getContext().downloadFile(getUriBuilder());
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
