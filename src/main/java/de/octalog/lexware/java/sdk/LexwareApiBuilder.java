package de.octalog.lexware.java.sdk;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PACKAGE)
public class LexwareApiBuilder {

    public static final String LEXWARE_API = "api.lexware.io/v1";

    private String host = LEXWARE_API;
    private String apiToken = null;
    private ThrottleProvider throttleProvider;

    public LexwareApiBuilder apiToken(String apiToken) {
        this.apiToken = apiToken;
        return this;
    }


    public LexwareApiBuilder host(String host) {
        this.host = host;
        return this;
    }

    public LexwareApiBuilder throttleProvider(ThrottleProvider throttleProvider) {
        this.throttleProvider = throttleProvider;
        return this;
    }

    public LexwareApi build() {
        RequestContext context = new RequestContext(this);
        return new LexwareApi(context);
    }

    boolean throttleProviderPresent() {
        return throttleProvider != null;
    }

    /**
     * manually handle the throttling of the api<br>
     * useful when you work with JToggle and this api together with the same api-key
     */
    public interface ThrottleProvider {

        /**
         * @return milliseconds in future when a next call is allowed
         */
        long getNextCallAllowed();

        /**
         * will get called from {@link RequestContext} immediately after api is called in order to keep lastCall somewhere outside
         */
        void apiCalled();

    }

}
