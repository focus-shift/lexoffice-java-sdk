package de.focus_shift.lexoffice.java.sdk.chain;


import de.focus_shift.lexoffice.java.sdk.RequestContext;
import de.focus_shift.lexoffice.java.sdk.model.Contact;
import de.focus_shift.lexoffice.java.sdk.model.ItemCreatedResult;
import de.focus_shift.lexoffice.java.sdk.model.Page;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.Objects;

@RequiredArgsConstructor
public class ContactChain {

    private final RequestContext context;

    public Contact get(String id) {
        return new Get(context).get(id);
    }

    protected static class Get extends ExecutableRequestChain {
        private static final ParameterizedTypeReference<Contact> TYPE_REFERENCE = new ParameterizedTypeReference<>() {
        };

        public Get(RequestContext context) {
            super(context, "/contacts");
        }

        @SneakyThrows
        public Contact get(String id) {
            getUriBuilder().appendPath("/" + id);
            return getContext().execute(getUriBuilder(), HttpMethod.GET, TYPE_REFERENCE);
        }
    }

    public Fetch fetch() {
        return new Fetch(context);
    }

    public static class Fetch extends ExecutableRequestChain {
        private static final ParameterizedTypeReference<Page<Contact>> TYPE_REFERENCE = new ParameterizedTypeReference<>() {
        };

        public Fetch(RequestContext context) {
            super(context, "/contacts");
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
         * Default page size is set to 25 but can be increased up to 100/250 (depends on the used endpoint).
         */
        public Fetch pageSize(int pageSize) {
            super.getUriBuilder()
                    .addParameter("size", String.valueOf(pageSize));
            return this;
        }

        /**
         * filters contacts where any of their email addresses inside the emailAddresses JSON object match the given email value. At least 3 characters are necessary to successfully complete the query.
         */
        public Fetch email(String email) {
            super.getUriBuilder()
                    .addParameter("email", email);
            return this;
        }

        /**
         * filters contacts whose name matches the given name value. At least 3 characters are necessary to successfully complete the query.
         */
        public Fetch name(String name) {
            super.getUriBuilder()
                    .addParameter("name", name);
            return this;
        }

        /**
         * returns the contacts with the specified contact number. Number is either the customer number or the vendor number located in the roles object.
         */
        public Fetch number(Long number) {
            super.getUriBuilder()
                    .addParameter("number", String.valueOf(number));
            return this;
        }

        /**
         * if set to true filters contacts that have the role customer. If set to false filters contacts that do not have the customer role.
         */
        public Fetch customer(boolean customer) {
            super.getUriBuilder()
                    .addParameter("customer", String.valueOf(customer));
            return this;
        }

        /**
         * if set to true filters contacts that have the role vendor. If set to false filters contacts that do not have the vendor role.
         */
        public Fetch vendor(boolean vendor) {
            super.getUriBuilder()
                    .addParameter("vendor", String.valueOf(vendor));
            return this;
        }

        @SneakyThrows
        public Page<Contact> get() {
            return getContext().execute(getUriBuilder(), HttpMethod.GET, TYPE_REFERENCE);
        }
    }

    public ItemCreatedResult create(Contact contact) {
        return new Create(context).submit(contact);
    }

    public static class Create extends ExecutableRequestChain {
        private static final ParameterizedTypeReference<ItemCreatedResult> TYPE_REFERENCE = new ParameterizedTypeReference<>() {
        };

        public Create(RequestContext context) {
            super(context, "/contacts");
        }

        @SneakyThrows
        public ItemCreatedResult submit(Contact contact) {
            return getContext().execute(getUriBuilder(), HttpMethod.POST, contact, TYPE_REFERENCE);
        }
    }

    /**
     * if of contact needs to be not null
     *
     * @return updated entity
     */
    public Contact update(Contact contact) {
        Objects.requireNonNull(contact.getId());
        return new Update(context).update(contact);
    }

    public static class Update extends ExecutableRequestChain {
        private static final ParameterizedTypeReference<Contact> TYPE_REFERENCE = new ParameterizedTypeReference<>() {
        };

        public Update(RequestContext context) {
            super(context, "/contacts");
        }

        @SneakyThrows
        public Contact update(Contact contact) {
            return getContext().execute(getUriBuilder().appendPath(contact.getId()), HttpMethod.PUT, contact, TYPE_REFERENCE);
        }
    }

}
