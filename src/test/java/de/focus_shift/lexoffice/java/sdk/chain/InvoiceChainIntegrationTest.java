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
import de.focus_shift.lexoffice.java.sdk.model.Invoice;
import de.focus_shift.lexoffice.java.sdk.model.VoucherStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class InvoiceChainIntegrationTest {

  private LexofficeApi lexofficeApi;

  @BeforeEach
  void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
    String wiremockHost = wmRuntimeInfo.getHttpBaseUrl().replace("http://", "") + "/v1";

    lexofficeApi =
        new LexofficeApiBuilder().apiToken("test-api-key").protocol("http").host(wiremockHost).build();
  }

  @Test
  void getInvoice() {
    String invoiceId = "e9066f04-8cc7-4616-93f8-ac9571ec5e11";

    stubFor(
        get(urlPathEqualTo("/v1/invoices/" + invoiceId))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "e9066f04-8cc7-4616-93f8-ac9571ec5e11",
                          "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                          "createdDate": "2023-06-17T18:32:07.480+02:00",
                          "updatedDate": "2023-06-17T18:32:07.551+02:00",
                          "version": 1,
                          "language": "de",
                          "archived": false,
                          "voucherStatus": "open",
                          "voucherNumber": "RE1019",
                          "voucherDate": "2023-02-22T00:00:00.000+01:00",
                          "dueDate": "2023-03-08T00:00:00.000+01:00",
                          "address": {
                            "contactId": "97c5794f-8ab2-43ad-b459-c5980b055e4d",
                            "name": "Berliner Kindl GmbH",
                            "street": "Juliusstraße 25",
                            "zip": "12051",
                            "city": "Berlin",
                            "countryCode": "DE"
                          },
                          "lineItems": [
                            {
                              "id": "97b98491-e953-4dc9-97a9-ae437a8052b4",
                              "type": "custom",
                              "name": "Axa Rahmenschloss Defender RL",
                              "description": "Vollständig symmetrisches Design in metallicfarbener Ausführung. Der ergonomische Bedienkopf mit praktischem Zahlenfeld.",
                              "quantity": 1.0000,
                              "unitName": "Stück",
                              "unitPrice": {
                                "currency": "EUR",
                                "netAmount": 20.08,
                                "grossAmount": 23.90,
                                "taxRatePercentage": 19
                              },
                              "lineItemAmount": 23.90
                            }
                          ],
                          "totalPrice": {
                            "currency": "EUR",
                            "totalNetAmount": 20.08,
                            "totalGrossAmount": 23.90,
                            "totalTaxAmount": 3.82
                          },
                          "taxAmounts": [
                            {
                              "taxRatePercentage": 19,
                              "taxAmount": 3.82,
                              "netAmount": 20.08
                            }
                          ],
                          "taxConditions": {
                            "taxType": "gross"
                          },
                          "paymentConditions": {
                            "paymentTermLabel": "10 Tage - 3 %, 30 Tage netto",
                            "paymentTermDuration": 30,
                            "paymentDiscountConditions": {
                              "discountPercentage": 3,
                              "discountRange": 10
                            }
                          },
                          "title": "Rechnung",
                          "introduction": "Ihre bestellten Positionen stellen wir Ihnen hiermit in Rechnung",
                          "remark": "Vielen Dank für Ihren Einkauf"
                        }
                        """)));

    Invoice invoice = lexofficeApi.invoice().get(invoiceId);

    assertThat(invoice).isNotNull();
    assertThat(invoice.getId()).isEqualTo(invoiceId);
    assertThat(invoice.getVoucherNumber()).isEqualTo("RE1019");
    assertThat(invoice.getVoucherStatus()).isEqualTo(VoucherStatus.OPEN);
    assertThat(invoice.getAddress().getName()).isEqualTo("Berliner Kindl GmbH");
    assertThat(invoice.getLineItems()).hasSize(1);
    assertThat(invoice.getLineItems().get(0).getName()).isEqualTo("Axa Rahmenschloss Defender RL");
  }

  @Test
  void createInvoice() {
    stubFor(
        post(urlPathEqualTo("/v1/invoices"))
            .willReturn(
                aResponse()
                    .withStatus(201)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "e9066f04-8cc7-4616-93f8-ac9571ec5e11",
                          "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                          "createdDate": "2023-06-17T18:32:07.480+02:00",
                          "updatedDate": "2023-06-17T18:32:07.551+02:00",
                          "version": 0,
                          "voucherStatus": "draft"
                        }
                        """)));

    Invoice newInvoice = Invoice.builder().title("Rechnung").language("de").build();

    Invoice createdInvoice = lexofficeApi.invoice().create().submit(newInvoice);

    assertThat(createdInvoice).isNotNull();
    assertThat(createdInvoice.getId()).isEqualTo("e9066f04-8cc7-4616-93f8-ac9571ec5e11");
    assertThat(createdInvoice.getVoucherStatus()).isEqualTo(VoucherStatus.DRAFT);

    verify(
        postRequestedFor(urlPathEqualTo("/v1/invoices"))
            .withRequestBody(
                equalToJson(
                    """
                    {
                      "title": "Rechnung",
                      "language": "de"
                    }
                    """,
                    true,
                    true)));
  }

  @Test
  void createInvoiceFinalized() {
    stubFor(
        post(urlPathEqualTo("/v1/invoices"))
            .withQueryParam("finalize", equalTo("true"))
            .willReturn(
                aResponse()
                    .withStatus(201)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "e9066f04-8cc7-4616-93f8-ac9571ec5e11",
                          "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                          "createdDate": "2023-06-17T18:32:07.480+02:00",
                          "updatedDate": "2023-06-17T18:32:07.551+02:00",
                          "version": 0,
                          "voucherStatus": "open",
                          "voucherNumber": "RE1020"
                        }
                        """)));

    Invoice newInvoice = Invoice.builder().title("Rechnung").language("de").build();

    Invoice createdInvoice = lexofficeApi.invoice().create().finalize(true).submit(newInvoice);

    assertThat(createdInvoice).isNotNull();
    assertThat(createdInvoice.getId()).isEqualTo("e9066f04-8cc7-4616-93f8-ac9571ec5e11");
    assertThat(createdInvoice.getVoucherStatus()).isEqualTo(VoucherStatus.OPEN);
    assertThat(createdInvoice.getVoucherNumber()).isEqualTo("RE1020");

    verify(
        postRequestedFor(urlPathEqualTo("/v1/invoices"))
            .withQueryParam("finalize", equalTo("true"))
            .withRequestBody(
                equalToJson(
                    """
                    {
                      "title": "Rechnung",
                      "language": "de"
                    }
                    """,
                    true,
                    true)));
  }
}
