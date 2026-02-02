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
import de.focus_shift.lexoffice.java.sdk.model.Voucher;
import de.focus_shift.lexoffice.java.sdk.model.VoucherStatus;
import de.focus_shift.lexoffice.java.sdk.model.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class VoucherListChainIntegrationTest {

  private LexofficeApi lexofficeApi;

  @BeforeEach
  void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
    String wiremockHost = wmRuntimeInfo.getHttpBaseUrl().replace("http://", "") + "/v1";

    lexofficeApi =
        new LexofficeApiBuilder().apiToken("test-api-key").protocol("http").host(wiremockHost).build();
  }

  @Test
  void getVoucherList() {
    stubFor(
        get(urlPathEqualTo("/v1/voucherlist"))
            .withQueryParam("voucherType", equalTo("invoice"))
            .withQueryParam("voucherStatus", equalTo("open"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "content": [
                            {
                              "id": "e9066f04-8cc7-4616-93f8-ac9571ec5e11",
                              "voucherType": "invoice",
                              "voucherStatus": "open",
                              "voucherNumber": "RE1019",
                              "voucherDate": "2023-02-22T00:00:00.000+01:00",
                              "createdDate": "2023-06-17T18:32:07.480+02:00",
                              "updatedDate": "2023-06-17T18:32:07.551+02:00",
                              "dueDate": "2023-03-08T00:00:00.000+01:00",
                              "contactId": "97c5794f-8ab2-43ad-b459-c5980b055e4d",
                              "contactName": "Berliner Kindl GmbH",
                              "totalAmount": 23.90,
                              "openAmount": 23.90,
                              "currency": "EUR",
                              "archived": false
                            },
                            {
                              "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                              "voucherType": "invoice",
                              "voucherStatus": "open",
                              "voucherNumber": "RE1020",
                              "voucherDate": "2023-02-23T00:00:00.000+01:00",
                              "createdDate": "2023-06-18T10:15:30.123+02:00",
                              "updatedDate": "2023-06-18T10:15:30.456+02:00",
                              "dueDate": "2023-03-09T00:00:00.000+01:00",
                              "contactId": "12345678-abcd-ef12-3456-7890abcdef12",
                              "contactName": "Test Kunde AG",
                              "totalAmount": 1190.00,
                              "openAmount": 500.00,
                              "currency": "EUR",
                              "archived": false
                            }
                          ],
                          "first": true,
                          "last": true,
                          "totalPages": 1,
                          "totalElements": 2,
                          "numberOfElements": 2,
                          "size": 25,
                          "number": 0,
                          "sort": [
                            {
                              "property": "voucherDate",
                              "direction": "DESC"
                            }
                          ]
                        }
                        """)));

    Page<Voucher> voucherPage =
        lexofficeApi.voucherList().voucherType(VoucherType.INVOICE).voucherStatus(VoucherStatus.OPEN).get();

    assertThat(voucherPage).isNotNull();
    assertThat(voucherPage.getContent()).hasSize(2);
    assertThat(voucherPage.getTotalElements()).isEqualTo(2);
    assertThat(voucherPage.getFirst()).isTrue();
    assertThat(voucherPage.getLast()).isTrue();

    Voucher firstVoucher = voucherPage.getContent().get(0);
    assertThat(firstVoucher.getVoucherNumber()).isEqualTo("RE1019");
    assertThat(firstVoucher.getVoucherType()).isEqualTo(VoucherType.INVOICE);
    assertThat(firstVoucher.getVoucherStatus()).isEqualTo(VoucherStatus.OPEN);
    assertThat(firstVoucher.getContactName()).isEqualTo("Berliner Kindl GmbH");
  }

  @Test
  void getVoucherListWithPagination() {
    stubFor(
        get(urlPathEqualTo("/v1/voucherlist"))
            .withQueryParam("voucherType", equalTo("invoice"))
            .withQueryParam("voucherStatus", equalTo("open"))
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
                              "id": "e9066f04-8cc7-4616-93f8-ac9571ec5e11",
                              "voucherType": "invoice",
                              "voucherStatus": "open",
                              "voucherNumber": "RE1019",
                              "voucherDate": "2023-02-22T00:00:00.000+01:00",
                              "contactName": "Berliner Kindl GmbH",
                              "totalAmount": 23.90,
                              "currency": "EUR",
                              "archived": false
                            }
                          ],
                          "first": true,
                          "last": false,
                          "totalPages": 5,
                          "totalElements": 50,
                          "numberOfElements": 10,
                          "size": 10,
                          "number": 0
                        }
                        """)));

    Page<Voucher> voucherPage =
        lexofficeApi
            .voucherList()
            .voucherType(VoucherType.INVOICE)
            .voucherStatus(VoucherStatus.OPEN)
            .page(0)
            .pageSize(10)
            .get();

    assertThat(voucherPage).isNotNull();
    assertThat(voucherPage.getTotalPages()).isEqualTo(5);
    assertThat(voucherPage.getTotalElements()).isEqualTo(50);
    assertThat(voucherPage.getSize()).isEqualTo(10);
    assertThat(voucherPage.getNumber()).isEqualTo(0);
    assertThat(voucherPage.getFirst()).isTrue();
    assertThat(voucherPage.getLast()).isFalse();
  }

  @Test
  void getVoucherListWithSorting() {
    stubFor(
        get(urlPathEqualTo("/v1/voucherlist"))
            .withQueryParam("voucherType", equalTo("invoice"))
            .withQueryParam("voucherStatus", equalTo("open"))
            .withQueryParam("sort", equalTo("voucherDate,DESC"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "content": [
                            {
                              "id": "e9066f04-8cc7-4616-93f8-ac9571ec5e11",
                              "voucherType": "invoice",
                              "voucherStatus": "open",
                              "voucherNumber": "RE1019",
                              "voucherDate": "2023-02-22T00:00:00.000+01:00",
                              "contactName": "Berliner Kindl GmbH",
                              "totalAmount": 23.90,
                              "currency": "EUR",
                              "archived": false
                            }
                          ],
                          "first": true,
                          "last": true,
                          "totalPages": 1,
                          "totalElements": 1,
                          "numberOfElements": 1,
                          "size": 25,
                          "number": 0
                        }
                        """)));

    Page<Voucher> voucherPage =
        lexofficeApi
            .voucherList()
            .voucherType(VoucherType.INVOICE)
            .voucherStatus(VoucherStatus.OPEN)
            .sortByVoucherDate(false)
            .get();

    assertThat(voucherPage).isNotNull();
    assertThat(voucherPage.getContent()).hasSize(1);
  }

  @Test
  void getVoucherListFilteredByContactName() {
    stubFor(
        get(urlPathEqualTo("/v1/voucherlist"))
            .withQueryParam("voucherType", equalTo("invoice"))
            .withQueryParam("voucherStatus", equalTo("open"))
            .withQueryParam("contactName", equalTo("Berliner Kindl"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "content": [
                            {
                              "id": "e9066f04-8cc7-4616-93f8-ac9571ec5e11",
                              "voucherType": "invoice",
                              "voucherStatus": "open",
                              "voucherNumber": "RE1019",
                              "voucherDate": "2023-02-22T00:00:00.000+01:00",
                              "contactName": "Berliner Kindl GmbH",
                              "totalAmount": 23.90,
                              "currency": "EUR",
                              "archived": false
                            }
                          ],
                          "first": true,
                          "last": true,
                          "totalPages": 1,
                          "totalElements": 1,
                          "numberOfElements": 1,
                          "size": 25,
                          "number": 0
                        }
                        """)));

    Page<Voucher> voucherPage =
        lexofficeApi
            .voucherList()
            .voucherType(VoucherType.INVOICE)
            .voucherStatus(VoucherStatus.OPEN)
            .contactName("Berliner Kindl")
            .get();

    assertThat(voucherPage).isNotNull();
    assertThat(voucherPage.getContent()).hasSize(1);
    assertThat(voucherPage.getContent().get(0).getContactName()).isEqualTo("Berliner Kindl GmbH");
  }
}
