package de.focus_shift.lexoffice.java.sdk;

import com.google.common.base.Preconditions;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

public class RestUriBuilder {

    private String protocol = "https";
    private String host;
    private String path = "";
    private final UriComponentsBuilder queryParamsBuilder = UriComponentsBuilder.newInstance();

    public RestUriBuilder host(String host) {
        Preconditions.checkNotNull(host);
        this.host = host;
        return this;
    }

    public RestUriBuilder protocol(String protocol) {
        Preconditions.checkNotNull(protocol);
        this.protocol = protocol;
        return this;
    }

    public RestUriBuilder path(String path) {
        Preconditions.checkNotNull(path);
        this.path = path;
        return this;
    }

    public RestUriBuilder appendPath(String path) {
        Preconditions.checkNotNull(path);
        this.path = this.path + path;
        return this;
    }

    public RestUriBuilder addParameter(String key, Object value) {
        return addParameters(key, Collections.singletonList(value));
    }

    public RestUriBuilder addParameters(String key, Collection<?> values) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(values);
        for (Object value : values) {
            queryParamsBuilder.queryParam(key, value);
        }
        return this;
    }

    public URI build() {
        Preconditions.checkNotNull(host);

        String baseUrl = protocol + "://" + host + path;

        return UriComponentsBuilder.fromUriString(baseUrl)
                .queryParams(queryParamsBuilder.build().getQueryParams())
                .build()
                .encode()
                .toUri();
    }
}
