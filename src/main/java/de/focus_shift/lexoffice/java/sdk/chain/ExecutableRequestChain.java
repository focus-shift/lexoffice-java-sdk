package de.focus_shift.lexoffice.java.sdk.chain;

import de.focus_shift.lexoffice.java.sdk.RequestContext;
import de.focus_shift.lexoffice.java.sdk.RestUriBuilder;

class ExecutableRequestChain extends RequestChain {

    private final RestUriBuilder uriBuilder;

    public ExecutableRequestChain(RequestChain parent, String path) {
        super(parent, path);
        this.uriBuilder = getContext().getUriBuilder();
        this.uriBuilder.path(resolvePath());
    }

    public ExecutableRequestChain(RequestContext context, String path) {
        super(context, path);
        this.uriBuilder = getContext().getUriBuilder();
        this.uriBuilder.path(resolvePath());
    }

    protected RestUriBuilder getUriBuilder() {
        return this.uriBuilder;
    }

}
