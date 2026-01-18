package de.octalog.lexware.java.sdk.chain;

import de.octalog.lexware.java.sdk.RequestContext;
import de.octalog.lexware.java.sdk.model.Page;
import de.octalog.lexware.java.sdk.model.RecurringTemplate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
public class RecurringTemplateChain {

    private final RequestContext context;

    /**
     * Retrieve a single recurring template by its ID.
     *
     * @param id the UUID of the recurring template
     * @return the RecurringTemplate
     */
    public RecurringTemplate get(String id) {
        return new Get(context).get(id);
    }

    /**
     * Returns a Fetch chain for retrieving a paginated list of recurring templates.
     *
     * @return Fetch chain for method chaining
     */
    public Fetch fetch() {
        return new Fetch(context);
    }

    protected static class Get extends ExecutableRequestChain {
        private static final ParameterizedTypeReference<RecurringTemplate> TYPE_REFERENCE =
                new ParameterizedTypeReference<>() {};

        public Get(RequestContext context) {
            super(context, "/recurring-templates");
        }

        @SneakyThrows
        public RecurringTemplate get(String id) {
            getUriBuilder().appendPath("/" + id);
            return getContext().execute(getUriBuilder(), HttpMethod.GET, TYPE_REFERENCE);
        }
    }

    public static class Fetch extends ExecutableRequestChain {
        private static final ParameterizedTypeReference<Page<RecurringTemplate>> TYPE_REFERENCE =
                new ParameterizedTypeReference<>() {};

        public Fetch(RequestContext context) {
            super(context, "/recurring-templates");
        }

        /**
         * Pages are zero indexed, thus providing 0 for page will return the first page.
         */
        public Fetch page(int page) {
            super.getUriBuilder()
                    .addParameter("page", String.valueOf(page));
            return this;
        }

        /**
         * Default page size is set to 25 but can be increased up to 250.
         */
        public Fetch pageSize(int pageSize) {
            super.getUriBuilder()
                    .addParameter("size", String.valueOf(pageSize));
            return this;
        }

        /**
         * Sort by createdDate in ascending or descending order.
         */
        public Fetch sortByCreatedDate(boolean asc) {
            super.getUriBuilder()
                    .addParameter("sort", String.format("createdDate,%s", asc ? "ASC" : "DESC"));
            return this;
        }

        /**
         * Sort by updatedDate in ascending or descending order.
         */
        public Fetch sortByUpdatedDate(boolean asc) {
            super.getUriBuilder()
                    .addParameter("sort", String.format("updatedDate,%s", asc ? "ASC" : "DESC"));
            return this;
        }

        /**
         * Sort by lastExecutionDate in ascending or descending order.
         */
        public Fetch sortByLastExecutionDate(boolean asc) {
            super.getUriBuilder()
                    .addParameter("sort", String.format("lastExecutionDate,%s", asc ? "ASC" : "DESC"));
            return this;
        }

        /**
         * Sort by nextExecutionDate in ascending or descending order.
         */
        public Fetch sortByNextExecutionDate(boolean asc) {
            super.getUriBuilder()
                    .addParameter("sort", String.format("nextExecutionDate,%s", asc ? "ASC" : "DESC"));
            return this;
        }

        /**
         * Generic sort method for custom sort parameters.
         * Format: "property,direction" e.g., "createdDate,ASC"
         */
        public Fetch sort(String sort) {
            super.getUriBuilder()
                    .addParameter("sort", sort);
            return this;
        }

        @SneakyThrows
        public Page<RecurringTemplate> get() {
            return getContext().execute(getUriBuilder(), HttpMethod.GET, TYPE_REFERENCE);
        }
    }
}
