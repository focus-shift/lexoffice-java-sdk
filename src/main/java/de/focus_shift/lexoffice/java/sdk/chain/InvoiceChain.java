package de.focus_shift.lexoffice.java.sdk.chain;

import de.focus_shift.lexoffice.java.sdk.RequestContext;
import com.google.common.base.Preconditions;
import de.focus_shift.lexoffice.java.sdk.model.DocumentFile;
import de.focus_shift.lexoffice.java.sdk.model.Invoice;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Set;

@RequiredArgsConstructor
public class InvoiceChain {

    private final RequestContext context;

    public Invoice get(String id) {
        return new Get(context).get(id);
    }

    public Create create() {
        return new Create(context);
    }

    public FileDownload file(String id) {
        return new FileDownload(context, id);
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

    public static class FileDownload extends ExecutableRequestChain {
        private static final Set<MediaType> SUPPORTED_MEDIA_TYPES = Set.of(MediaType.APPLICATION_PDF, MediaType.APPLICATION_XML, MediaType.ALL);
        private final String invoiceId;
        private MediaType accept = MediaType.APPLICATION_PDF;

        public FileDownload(RequestContext context, String invoiceId) {
            super(context, "/invoices");
            this.invoiceId = Preconditions.checkNotNull(invoiceId, "invoiceId must not be null");
            getUriBuilder().appendPath("/" + this.invoiceId + "/file");
        }

        public FileDownload asPdf() {
            return accept(MediaType.APPLICATION_PDF);
        }

        public FileDownload asXml() {
            return accept(MediaType.APPLICATION_XML);
        }

        public FileDownload anyRepresentation() {
            return accept(MediaType.ALL);
        }

        public FileDownload accept(MediaType mediaType) {
            Preconditions.checkNotNull(mediaType, "mediaType must not be null");
            Preconditions.checkArgument(SUPPORTED_MEDIA_TYPES.contains(mediaType), "Unsupported media type %s", mediaType);
            this.accept = mediaType;
            return this;
        }

        @SneakyThrows
        public DocumentFile download() {
            return getContext().execute(getUriBuilder(), HttpMethod.GET, accept);
        }
    }

}
