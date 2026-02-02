package de.focus_shift.lexoffice.java.sdk.chain;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.focus_shift.lexoffice.java.sdk.LexofficeApi;
import de.focus_shift.lexoffice.java.sdk.LexofficeApiBuilder;
import de.focus_shift.lexoffice.java.sdk.model.Page;
import de.focus_shift.lexoffice.java.sdk.model.RecurringTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class RecurringTemplateChainIntegrationTest {

  private LexofficeApi lexofficeApi;

  @BeforeEach
  void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
    String wiremockHost = wmRuntimeInfo.getHttpBaseUrl().replace("http://", "") + "/v1";

    lexofficeApi =
        new LexofficeApiBuilder().apiToken("test-api-key").protocol("http").host(wiremockHost).build();
  }

  @Test
  void getRecurringTemplate() {
    String templateId = "a1b2c3d4-e5f6-7890-abcd-ef1234567890";

    stubFor(
        get(urlPathEqualTo("/v1/recurring-templates/" + templateId))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                          "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                          "createdDate": "2023-01-15T10:30:00.000+01:00",
                          "updatedDate": "2023-06-01T14:45:30.123+02:00",
                          "version": 5,
                          "language": "de",
                          "archived": false,
                          "address": {
                            "contactId": "97c5794f-8ab2-43ad-b459-c5980b055e4d",
                            "name": "Stammkunde GmbH",
                            "street": "Hauptstraße 1",
                            "zip": "10115",
                            "city": "Berlin",
                            "countryCode": "DE"
                          },
                          "lineItems": [
                            {
                              "id": "item-uuid-1234",
                              "type": "custom",
                              "name": "Monatliche Wartung",
                              "description": "Regelmäßige Systemwartung und Updates",
                              "quantity": 1.0000,
                              "unitName": "Pauschale",
                              "unitPrice": {
                                "currency": "EUR",
                                "netAmount": 500.00,
                                "grossAmount": 595.00,
                                "taxRatePercentage": 19
                              },
                              "lineItemAmount": 595.00
                            }
                          ],
                          "totalPrice": {
                            "currency": "EUR",
                            "totalNetAmount": 500.00,
                            "totalGrossAmount": 595.00,
                            "totalTaxAmount": 95.00
                          },
                          "taxAmounts": [
                            {
                              "taxRatePercentage": 19,
                              "taxAmount": 95.00,
                              "netAmount": 500.00
                            }
                          ],
                          "taxConditions": {
                            "taxType": "gross"
                          },
                          "paymentConditions": {
                            "paymentTermLabel": "Zahlbar innerhalb von 14 Tagen",
                            "paymentTermDuration": 14
                          },
                          "title": "Wartungsvertrag",
                          "introduction": "Hiermit stellen wir Ihnen die vereinbarte Wartungspauschale in Rechnung",
                          "remark": "Vielen Dank für Ihr Vertrauen",
                          "recurringTemplateSettings": {
                            "id": "settings-uuid-5678",
                            "startDate": "2023-01-01T00:00:00.000+01:00",
                            "endDate": "2024-12-31T00:00:00.000+01:00",
                            "finalize": true,
                            "shippingType": "service",
                            "executionInterval": "MONTHLY",
                            "executionStatus": "ACTIVE",
                            "lastExecutionFailed": false,
                            "lastExecutionErrorMessage": null,
                            "nextExecutionDate": "2023-07-01T00:00:00.000+02:00"
                          }
                        }
                        """)));

    RecurringTemplate template = lexofficeApi.recurringTemplates().get(templateId);

    assertThat(template).isNotNull();
    assertThat(template.getId()).isEqualTo(templateId);
    assertThat(template.getTitle()).isEqualTo("Wartungsvertrag");
    assertThat(template.getAddress().getName()).isEqualTo("Stammkunde GmbH");
    assertThat(template.getLineItems()).hasSize(1);
    assertThat(template.getLineItems().get(0).getName()).isEqualTo("Monatliche Wartung");
    assertThat(template.getRecurringTemplateSettings()).isNotNull();
  }

  @Test
  void fetchRecurringTemplates() {
    stubFor(
        get(urlPathEqualTo("/v1/recurring-templates"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "content": [
                            {
                              "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                              "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                              "createdDate": "2023-01-15T10:30:00.000+01:00",
                              "updatedDate": "2023-06-01T14:45:30.123+02:00",
                              "version": 5,
                              "language": "de",
                              "archived": false,
                              "title": "Wartungsvertrag",
                              "address": {
                                "name": "Stammkunde GmbH"
                              }
                            },
                            {
                              "id": "b2c3d4e5-f6g7-8901-bcde-f23456789012",
                              "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                              "createdDate": "2023-02-20T09:00:00.000+01:00",
                              "updatedDate": "2023-05-15T16:30:00.456+02:00",
                              "version": 3,
                              "language": "de",
                              "archived": false,
                              "title": "Hosting-Abonnement",
                              "address": {
                                "name": "Web Services AG"
                              }
                            }
                          ],
                          "first": true,
                          "last": true,
                          "totalPages": 1,
                          "totalElements": 2,
                          "numberOfElements": 2,
                          "size": 25,
                          "number": 0
                        }
                        """)));

    Page<RecurringTemplate> templatePage = lexofficeApi.recurringTemplates().fetch().get();

    assertThat(templatePage).isNotNull();
    assertThat(templatePage.getContent()).hasSize(2);
    assertThat(templatePage.getTotalElements()).isEqualTo(2);
    assertThat(templatePage.getFirst()).isTrue();
    assertThat(templatePage.getLast()).isTrue();

    assertThat(templatePage.getContent().get(0).getTitle()).isEqualTo("Wartungsvertrag");
    assertThat(templatePage.getContent().get(1).getTitle()).isEqualTo("Hosting-Abonnement");
  }

  @Test
  void fetchRecurringTemplatesWithPagination() {
    stubFor(
        get(urlPathEqualTo("/v1/recurring-templates"))
            .withQueryParam("page", equalTo("0"))
            .withQueryParam("size", equalTo("10"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "content": [
                            {
                              "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                              "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                              "createdDate": "2023-01-15T10:30:00.000+01:00",
                              "updatedDate": "2023-06-01T14:45:30.123+02:00",
                              "version": 5,
                              "language": "de",
                              "archived": false,
                              "title": "Wartungsvertrag"
                            }
                          ],
                          "first": true,
                          "last": false,
                          "totalPages": 3,
                          "totalElements": 25,
                          "numberOfElements": 10,
                          "size": 10,
                          "number": 0
                        }
                        """)));

    Page<RecurringTemplate> templatePage =
        lexofficeApi.recurringTemplates().fetch().page(0).pageSize(10).get();

    assertThat(templatePage).isNotNull();
    assertThat(templatePage.getTotalPages()).isEqualTo(3);
    assertThat(templatePage.getTotalElements()).isEqualTo(25);
    assertThat(templatePage.getSize()).isEqualTo(10);
    assertThat(templatePage.getFirst()).isTrue();
    assertThat(templatePage.getLast()).isFalse();
  }

  @Test
  void fetchRecurringTemplatesWithSorting() {
    stubFor(
        get(urlPathEqualTo("/v1/recurring-templates"))
            .withQueryParam("sort", equalTo("createdDate,DESC"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "content": [
                            {
                              "id": "b2c3d4e5-f6g7-8901-bcde-f23456789012",
                              "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                              "createdDate": "2023-02-20T09:00:00.000+01:00",
                              "updatedDate": "2023-05-15T16:30:00.456+02:00",
                              "version": 3,
                              "language": "de",
                              "archived": false,
                              "title": "Hosting-Abonnement"
                            },
                            {
                              "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                              "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                              "createdDate": "2023-01-15T10:30:00.000+01:00",
                              "updatedDate": "2023-06-01T14:45:30.123+02:00",
                              "version": 5,
                              "language": "de",
                              "archived": false,
                              "title": "Wartungsvertrag"
                            }
                          ],
                          "first": true,
                          "last": true,
                          "totalPages": 1,
                          "totalElements": 2,
                          "numberOfElements": 2,
                          "size": 25,
                          "number": 0
                        }
                        """)));

    Page<RecurringTemplate> templatePage =
        lexofficeApi.recurringTemplates().fetch().sortByCreatedDate(false).get();

    assertThat(templatePage).isNotNull();
    assertThat(templatePage.getContent()).hasSize(2);
    // First item should be the more recently created one (descending order)
    assertThat(templatePage.getContent().get(0).getTitle()).isEqualTo("Hosting-Abonnement");
    assertThat(templatePage.getContent().get(1).getTitle()).isEqualTo("Wartungsvertrag");
  }
}
