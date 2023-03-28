package de.focus_shift.lexoffice.java.sdk;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RequestContext {

    private static long lastCall;
    private final LexofficeApiBuilder apiBuilder;
    private HttpClient httpClient;
    private ClientHttpRequestFactory requestFactory;
    private RestTemplate restTemplate;
    private long throttlePeriod = 510;

    RequestContext(LexofficeApiBuilder apiBuilder) {
        this.apiBuilder = apiBuilder;

        this.httpClient = HttpClientBuilder.create()
                .build();
        this.requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        restTemplate = new RestTemplate(requestFactory);
        restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter(getObjectMapper())));
    }


    protected ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }


    public RestUriBuilder getUriBuilder() {
        return new RestUriBuilder()
                .protocol("https")
                .host(apiBuilder.getHost());
    }

    public synchronized <R> R execute(RestUriBuilder uriBuilder, HttpMethod method, ParameterizedTypeReference<R> responseType) {
        return execute(uriBuilder, method, null, responseType);
    }

    public synchronized <R, E> R execute(RestUriBuilder uriBuilder, HttpMethod method, E body, ParameterizedTypeReference<R> responseType) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + apiBuilder.getApiToken());

        checkThrottlePeriod();

        HttpEntity<E> requestEntity = new HttpEntity<E>(body, headers);
        ResponseEntity<R> response = restTemplate.exchange(uriBuilder.build(), method, requestEntity, responseType);
        lastCall = System.currentTimeMillis();
        if (apiBuilder.throttleProviderPresent()) {
            apiBuilder.getThrottleProvider()
                    .apiCalled();
        }


        return response.getBody();
    }

    public synchronized void delete(RestUriBuilder uriBuilder) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + apiBuilder.getApiToken());

        checkThrottlePeriod();

        HttpEntity<Void> request = new HttpEntity<>(headers);
        restTemplate.exchange(uriBuilder.build(), HttpMethod.DELETE, request, Void.class);

        lastCall = System.currentTimeMillis();
        if (apiBuilder.throttleProviderPresent()) {
            apiBuilder.getThrottleProvider()
                    .apiCalled();
        }
    }

    protected synchronized void checkThrottlePeriod() {
        if (apiBuilder.throttleProviderPresent()) {
            waitFuturePassed(apiBuilder.getThrottleProvider()
                    .getNextCallAllowed());
        } else {
            waitFuturePassed(lastCall + throttlePeriod);
        }
    }

    protected synchronized void waitFuturePassed(long future) {
        while (future > System.currentTimeMillis()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // ignore, except to propagate
                Thread.currentThread()
                        .interrupt();
            }
        }
    }

}
