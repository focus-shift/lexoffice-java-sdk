package de.focus_shift.lexoffice.java.sdk.chain;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.focus_shift.lexoffice.java.sdk.LexofficeApi;
import de.focus_shift.lexoffice.java.sdk.LexofficeApiBuilder;
import de.focus_shift.lexoffice.java.sdk.model.EventSubscription;
import de.focus_shift.lexoffice.java.sdk.model.EventType;
import de.focus_shift.lexoffice.java.sdk.model.ItemCreatedResult;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class EventSubscriptionChainIntegrationTest {

  private LexofficeApi lexofficeApi;

  @BeforeEach
  void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
    String wiremockHost = wmRuntimeInfo.getHttpBaseUrl().replace("http://", "") + "/v1";

    lexofficeApi =
        new LexofficeApiBuilder().apiToken("test-api-key").protocol("http").host(wiremockHost).build();
  }

  @Test
  void getEventSubscription() {
    String subscriptionId = "6a5c555e-060c-4fde-9ac3-4c7a396e5e1e";

    stubFor(
        get(urlPathEqualTo("/v1/event-subscriptions/" + subscriptionId))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "subscriptionId": "6a5c555e-060c-4fde-9ac3-4c7a396e5e1e",
                          "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                          "createdDate": "2023-06-10T12:30:45.123+02:00",
                          "eventType": "invoice.created",
                          "callbackUrl": "https://example.com/webhook/invoice"
                        }
                        """)));

    EventSubscription subscription = lexofficeApi.eventSubscriptions().get(subscriptionId);

    assertThat(subscription).isNotNull();
    assertThat(subscription.getSubscriptionId()).isEqualTo(subscriptionId);
    assertThat(subscription.getEventType()).isEqualTo(EventType.INVOICE_CREATED);
    assertThat(subscription.getCallbackUrl()).isEqualTo("https://example.com/webhook/invoice");
  }

  @Test
  void getAllEventSubscriptions() {
    stubFor(
        get(urlPathEqualTo("/v1/event-subscriptions"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "content": [
                            {
                              "subscriptionId": "6a5c555e-060c-4fde-9ac3-4c7a396e5e1e",
                              "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                              "createdDate": "2023-06-10T12:30:45.123+02:00",
                              "eventType": "invoice.created",
                              "callbackUrl": "https://example.com/webhook/invoice"
                            },
                            {
                              "subscriptionId": "7b6d666f-171d-5gef-0bd4-5d8b407f6f2f",
                              "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                              "createdDate": "2023-06-11T09:15:30.456+02:00",
                              "eventType": "contact.created",
                              "callbackUrl": "https://example.com/webhook/contact"
                            },
                            {
                              "subscriptionId": "8c7e777g-282e-6hfg-1ce5-6e9c518g7g3g",
                              "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                              "createdDate": "2023-06-12T14:45:00.789+02:00",
                              "eventType": "quotation.status.changed",
                              "callbackUrl": "https://example.com/webhook/quotation"
                            }
                          ],
                          "first": true,
                          "last": true,
                          "totalPages": 1,
                          "totalElements": 3,
                          "numberOfElements": 3,
                          "size": 25,
                          "number": 0
                        }
                        """)));

    List<EventSubscription> subscriptions = lexofficeApi.eventSubscriptions().getAll();

    assertThat(subscriptions).hasSize(3);
    assertThat(subscriptions.get(0).getEventType()).isEqualTo(EventType.INVOICE_CREATED);
    assertThat(subscriptions.get(1).getEventType()).isEqualTo(EventType.CONTACT_CREATED);
    assertThat(subscriptions.get(2).getEventType()).isEqualTo(EventType.QUOTATION_STATUS_CHANGED);
  }

  @Test
  void createEventSubscription() {
    stubFor(
        post(urlPathEqualTo("/v1/event-subscriptions"))
            .willReturn(
                aResponse()
                    .withStatus(201)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "6a5c555e-060c-4fde-9ac3-4c7a396e5e1e",
                          "resourceUri": "https://api.lexoffice.io/v1/event-subscriptions/6a5c555e-060c-4fde-9ac3-4c7a396e5e1e",
                          "createdDate": "2023-06-10T12:30:45.123+02:00",
                          "updatedDate": "2023-06-10T12:30:45.123+02:00",
                          "version": 0
                        }
                        """)));

    EventSubscription newSubscription =
        EventSubscription.builder()
            .eventType(EventType.INVOICE_CREATED)
            .callbackUrl("https://example.com/webhook/invoice")
            .build();

    ItemCreatedResult result = lexofficeApi.eventSubscriptions().create(newSubscription);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("6a5c555e-060c-4fde-9ac3-4c7a396e5e1e");
    assertThat(result.getResourceUri()).contains("/v1/event-subscriptions/");
    assertThat(result.getVersion()).isEqualTo(0L);

    verify(
        postRequestedFor(urlPathEqualTo("/v1/event-subscriptions"))
            .withRequestBody(
                equalToJson(
                    """
                    {
                      "eventType": "invoice.created",
                      "callbackUrl": "https://example.com/webhook/invoice"
                    }
                    """,
                    true,
                    true)));
  }

  @Test
  void deleteEventSubscription() {
    String subscriptionId = "6a5c555e-060c-4fde-9ac3-4c7a396e5e1e";

    stubFor(
        delete(urlPathEqualTo("/v1/event-subscriptions/" + subscriptionId))
            .willReturn(aResponse().withStatus(204)));

    lexofficeApi.eventSubscriptions().delete(subscriptionId);
  }
}
