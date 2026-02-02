package de.focus_shift.lexoffice.java.sdk.chain;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
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
import de.focus_shift.lexoffice.java.sdk.model.ItemCreatedResult;
import de.focus_shift.lexoffice.java.sdk.model.Quotation;
import de.focus_shift.lexoffice.java.sdk.model.VoucherStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class QuotationChainIntegrationTest {

  private LexofficeApi lexofficeApi;

  @BeforeEach
  void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
    String wiremockHost = wmRuntimeInfo.getHttpBaseUrl().replace("http://", "") + "/v1";

    lexofficeApi =
        new LexofficeApiBuilder().apiToken("test-api-key").protocol("http").host(wiremockHost).build();
  }

  @Test
  void getQuotation() {
    String quotationId = "424f784e-1f4e-439e-8f71-19673e6a8b9d";

    stubFor(
        get(urlPathEqualTo("/v1/quotations/" + quotationId))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "424f784e-1f4e-439e-8f71-19673e6a8b9d",
                          "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                          "createdDate": "2023-04-11T14:15:22.123+02:00",
                          "updatedDate": "2023-04-11T14:17:45.456+02:00",
                          "version": 2,
                          "language": "de",
                          "archived": false,
                          "voucherStatus": "open",
                          "voucherNumber": "AG0021",
                          "voucherDate": "2023-04-11T00:00:00.000+02:00",
                          "expirationDate": "2023-05-11T00:00:00.000+02:00",
                          "address": {
                            "contactId": "97c5794f-8ab2-43ad-b459-c5980b055e4d",
                            "name": "Muster GmbH",
                            "street": "Musterstraße 42",
                            "zip": "12345",
                            "city": "Musterstadt",
                            "countryCode": "DE"
                          },
                          "lineItems": [
                            {
                              "id": "68569bfc-e5ae-472d-bbdf-6d51a82b1d2f",
                              "type": "custom",
                              "name": "Beratungsleistung",
                              "description": "Projektberatung und Konzepterstellung",
                              "quantity": 8.0000,
                              "unitName": "Stunden",
                              "unitPrice": {
                                "currency": "EUR",
                                "netAmount": 150.00,
                                "grossAmount": 178.50,
                                "taxRatePercentage": 19
                              },
                              "lineItemAmount": 1428.00
                            }
                          ],
                          "totalPrice": {
                            "currency": "EUR",
                            "totalNetAmount": 1200.00,
                            "totalGrossAmount": 1428.00,
                            "totalTaxAmount": 228.00
                          },
                          "taxAmounts": [
                            {
                              "taxRatePercentage": 19,
                              "taxAmount": 228.00,
                              "netAmount": 1200.00
                            }
                          ],
                          "taxConditions": {
                            "taxType": "gross"
                          },
                          "title": "Angebot",
                          "introduction": "Gerne unterbreiten wir Ihnen folgendes Angebot",
                          "remark": "Dieses Angebot ist 30 Tage gültig"
                        }
                        """)));

    Quotation quotation = lexofficeApi.quotation().get(quotationId);

    assertThat(quotation).isNotNull();
    assertThat(quotation.getId()).isEqualTo(quotationId);
    assertThat(quotation.getVoucherNumber()).isEqualTo("AG0021");
    assertThat(quotation.getVoucherStatus()).isEqualTo(VoucherStatus.OPEN);
    assertThat(quotation.getAddress().getName()).isEqualTo("Muster GmbH");
    assertThat(quotation.getLineItems()).hasSize(1);
    assertThat(quotation.getLineItems().get(0).getName()).isEqualTo("Beratungsleistung");
  }

  @Test
  void createQuotation() {
    stubFor(
        post(urlPathEqualTo("/v1/quotations"))
            .willReturn(
                aResponse()
                    .withStatus(201)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "424f784e-1f4e-439e-8f71-19673e6a8b9d",
                          "resourceUri": "https://api.lexoffice.io/v1/quotations/424f784e-1f4e-439e-8f71-19673e6a8b9d",
                          "createdDate": "2023-04-11T14:15:22.123+02:00",
                          "updatedDate": "2023-04-11T14:15:22.123+02:00",
                          "version": 0
                        }
                        """)));

    Quotation newQuotation = Quotation.builder().title("Angebot").language("de").build();

    ItemCreatedResult result = lexofficeApi.quotation().create().submit(newQuotation);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("424f784e-1f4e-439e-8f71-19673e6a8b9d");
    assertThat(result.getResourceUri()).contains("/v1/quotations/424f784e-1f4e-439e-8f71-19673e6a8b9d");
    assertThat(result.getVersion()).isEqualTo(0L);

    verify(
        postRequestedFor(urlPathEqualTo("/v1/quotations"))
            .withRequestBody(
                equalToJson(
                    """
                    {
                      "title": "Angebot",
                      "language": "de"
                    }
                    """,
                    true,
                    true)));
  }

  @Test
  void createQuotationFinalized() {
    stubFor(
        post(urlPathEqualTo("/v1/quotations"))
            .withQueryParam("finalize", equalTo("true"))
            .willReturn(
                aResponse()
                    .withStatus(201)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "424f784e-1f4e-439e-8f71-19673e6a8b9d",
                          "resourceUri": "https://api.lexoffice.io/v1/quotations/424f784e-1f4e-439e-8f71-19673e6a8b9d",
                          "createdDate": "2023-04-11T14:15:22.123+02:00",
                          "updatedDate": "2023-04-11T14:15:22.123+02:00",
                          "version": 1
                        }
                        """)));

    Quotation newQuotation = Quotation.builder().title("Angebot").language("de").build();

    ItemCreatedResult result = lexofficeApi.quotation().create().finalize(true).submit(newQuotation);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("424f784e-1f4e-439e-8f71-19673e6a8b9d");
    assertThat(result.getVersion()).isEqualTo(1L);

    verify(
        postRequestedFor(urlPathEqualTo("/v1/quotations"))
            .withQueryParam("finalize", equalTo("true"))
            .withRequestBody(
                equalToJson(
                    """
                    {
                      "title": "Angebot",
                      "language": "de"
                    }
                    """,
                    true,
                    true)));
  }
}
