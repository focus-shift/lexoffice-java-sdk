package de.focus_shift.lexoffice.java.sdk;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PACKAGE)
public class LexofficeApiBuilder {

    public static final String LEXOFFICE_API = "api.lexware.io/v1";
    public static final String LEXOFFICE_SANDBOX_API = "api.lexware-sandbox.io/v1";

    private String host = LEXOFFICE_API;
    private String apiToken = null;
    private ThrottleProvider throttleProvider;

    public LexofficeApiBuilder apiToken(String apiToken) {
        this.apiToken = apiToken;
        return this;
    }


    public LexofficeApiBuilder sandboxed(boolean sandboxed) {
        this.host = sandboxed ? LEXOFFICE_SANDBOX_API : LEXOFFICE_API;
        return this;
    }

    public LexofficeApiBuilder throttleProvider(ThrottleProvider throttleProvider) {
        this.throttleProvider = throttleProvider;
        return this;
    }

    public LexofficeApi build() {
        RequestContext context = new RequestContext(this);
        return new LexofficeApi(context);
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
