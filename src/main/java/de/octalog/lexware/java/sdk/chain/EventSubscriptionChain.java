package de.octalog.lexware.java.sdk.chain;

import de.octalog.lexware.java.sdk.RequestContext;
import de.octalog.lexware.java.sdk.model.EventSubscription;
import de.octalog.lexware.java.sdk.model.ItemCreatedResult;
import de.octalog.lexware.java.sdk.model.Page;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

@RequiredArgsConstructor
public class EventSubscriptionChain {

    private final RequestContext context;

    public EventSubscription get(String subscriptionId) {
        return new Get(context).get(subscriptionId);
    }

    protected static class Get extends ExecutableRequestChain {
        private static final ParameterizedTypeReference<EventSubscription> TYPE_REFERENCE = new ParameterizedTypeReference<>() {
        };

        public Get(RequestContext context) {
            super(context, "/event-subscriptions");
        }

        @SneakyThrows
        public EventSubscription get(String subscriptionId) {
            getUriBuilder().appendPath("/" + subscriptionId);
            return getContext().execute(getUriBuilder(), HttpMethod.GET, TYPE_REFERENCE);
        }
    }

    public List<EventSubscription> getAll() {
        return new GetAll(context).getAll();
    }

    protected static class GetAll extends ExecutableRequestChain {
        private static final ParameterizedTypeReference<Page<EventSubscription>> TYPE_REFERENCE = new ParameterizedTypeReference<>() {
        };

        public GetAll(RequestContext context) {
            super(context, "/event-subscriptions");
        }

        @SneakyThrows
        public List<EventSubscription> getAll() {
            return getContext().execute(getUriBuilder(), HttpMethod.GET, TYPE_REFERENCE).getContent();
        }
    }

    public ItemCreatedResult create(EventSubscription eventSubscription) {
        return new Create(context).submit(eventSubscription);
    }

    public static class Create extends ExecutableRequestChain {
        private static final ParameterizedTypeReference<ItemCreatedResult> TYPE_REFERENCE = new ParameterizedTypeReference<>() {
        };

        public Create(RequestContext context) {
            super(context, "/event-subscriptions");
        }

        @SneakyThrows
        public ItemCreatedResult submit(EventSubscription eventSubscription) {
            return getContext().execute(getUriBuilder(), HttpMethod.POST, eventSubscription, TYPE_REFERENCE);
        }
    }
    public void delete(String subscriptionId) {
        new Delete(context).delete(subscriptionId);
    }

    protected static class Delete extends ExecutableRequestChain {
        public Delete(RequestContext context) {
            super(context, "/event-subscriptions");
        }

        @SneakyThrows
        public void delete(String subscriptionId) {
            getUriBuilder().appendPath("/" + subscriptionId);
            getContext().delete(getUriBuilder());
        }
    }

}
