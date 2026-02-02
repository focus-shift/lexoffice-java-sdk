package de.focus_shift.lexoffice.java.sdk.chain;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.focus_shift.lexoffice.java.sdk.LexofficeApi;
import de.focus_shift.lexoffice.java.sdk.LexofficeApiBuilder;
import de.focus_shift.lexoffice.java.sdk.model.Company;
import de.focus_shift.lexoffice.java.sdk.model.Contact;
import de.focus_shift.lexoffice.java.sdk.model.ItemCreatedResult;
import de.focus_shift.lexoffice.java.sdk.model.Page;
import de.focus_shift.lexoffice.java.sdk.model.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class ContactChainIntegrationTest {

  private LexofficeApi lexofficeApi;

  @BeforeEach
  void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
    String wiremockHost = wmRuntimeInfo.getHttpBaseUrl().replace("http://", "") + "/v1";

    lexofficeApi =
        new LexofficeApiBuilder().apiToken("test-api-key").protocol("http").host(wiremockHost).build();
  }

  @Test
  void getContact() {
    String contactId = "be9475a4-ef80-442b-95eb-e56a7b2d5596";

    stubFor(
        get(urlPathEqualTo("/v1/contacts/" + contactId))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "be9475a4-ef80-442b-95eb-e56a7b2d5596",
                          "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                          "version": 1,
                          "roles": {
                            "customer": {
                              "number": 10001
                            }
                          },
                          "company": {
                            "name": "Berliner Kindl GmbH",
                            "taxNumber": "12345/67890",
                            "vatRegistrationId": "DE123456789",
                            "allowTaxFreeInvoices": false,
                            "contactPersons": [
                              {
                                "salutation": "Herr",
                                "firstName": "Max",
                                "lastName": "Mustermann",
                                "primary": true,
                                "emailAddress": "max.mustermann@example.com",
                                "phoneNumber": "+49 30 123456"
                              }
                            ]
                          },
                          "addresses": {
                            "billing": [
                              {
                                "supplement": "Hinterhaus",
                                "street": "Juliusstraße 25",
                                "zip": "12051",
                                "city": "Berlin",
                                "countryCode": "DE"
                              }
                            ]
                          },
                          "emailAddresses": {
                            "business": ["info@example.com"],
                            "office": ["office@example.com"]
                          },
                          "phoneNumbers": {
                            "business": ["+49 30 123456"]
                          },
                          "archived": false
                        }
                        """)));

    Contact contact = lexofficeApi.contact().get(contactId);

    assertThat(contact).isNotNull();
    assertThat(contact.getId()).isEqualTo(contactId);
    assertThat(contact.getCompany().getName()).isEqualTo("Berliner Kindl GmbH");
    assertThat(contact.getCompany().getVatRegistrationId()).isEqualTo("DE123456789");
    assertThat(contact.getRoles().getCustomer()).isNotNull();
    assertThat(contact.getRoles().getCustomer().getNumber()).isEqualTo(10001L);
  }

  @Test
  void fetchContacts() {
    stubFor(
        get(urlPathEqualTo("/v1/contacts"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "content": [
                            {
                              "id": "be9475a4-ef80-442b-95eb-e56a7b2d5596",
                              "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                              "version": 1,
                              "roles": {
                                "customer": { "number": 10001 }
                              },
                              "company": {
                                "name": "Berliner Kindl GmbH"
                              },
                              "archived": false
                            },
                            {
                              "id": "c1234567-ef80-442b-95eb-e56a7b2d5596",
                              "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                              "version": 2,
                              "roles": {
                                "customer": { "number": 10002 }
                              },
                              "company": {
                                "name": "Test GmbH"
                              },
                              "archived": false
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

    Page<Contact> contactPage = lexofficeApi.contact().fetch().get();

    assertThat(contactPage).isNotNull();
    assertThat(contactPage.getContent()).hasSize(2);
    assertThat(contactPage.getTotalElements()).isEqualTo(2);
    assertThat(contactPage.getContent().get(0).getCompany().getName()).isEqualTo("Berliner Kindl GmbH");
    assertThat(contactPage.getContent().get(1).getCompany().getName()).isEqualTo("Test GmbH");
  }

  @Test
  void fetchContactsFilteredByCustomer() {
    stubFor(
        get(urlPathEqualTo("/v1/contacts"))
            .withQueryParam("customer", equalTo("true"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "content": [
                            {
                              "id": "be9475a4-ef80-442b-95eb-e56a7b2d5596",
                              "version": 1,
                              "roles": {
                                "customer": { "number": 10001 }
                              },
                              "company": {
                                "name": "Berliner Kindl GmbH"
                              },
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

    Page<Contact> contactPage = lexofficeApi.contact().fetch().customer(true).get();

    assertThat(contactPage).isNotNull();
    assertThat(contactPage.getContent()).hasSize(1);
    assertThat(contactPage.getContent().get(0).getRoles().getCustomer()).isNotNull();
  }

  @Test
  void fetchContactsFilteredByName() {
    stubFor(
        get(urlPathEqualTo("/v1/contacts"))
            .withQueryParam("name", equalTo("Berliner"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "content": [
                            {
                              "id": "be9475a4-ef80-442b-95eb-e56a7b2d5596",
                              "version": 1,
                              "roles": {
                                "customer": { "number": 10001 }
                              },
                              "company": {
                                "name": "Berliner Kindl GmbH"
                              },
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

    Page<Contact> contactPage = lexofficeApi.contact().fetch().name("Berliner").get();

    assertThat(contactPage).isNotNull();
    assertThat(contactPage.getContent()).hasSize(1);
    assertThat(contactPage.getContent().get(0).getCompany().getName()).contains("Berliner");
  }

  @Test
  void fetchContactsFilteredByNameWithSpecialCharacters() {
    String companyName = "Müller & Söhne GmbH";

    stubFor(
        get(urlPathEqualTo("/v1/contacts"))
            .withQueryParam("name", equalTo(companyName))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "content": [
                            {
                              "id": "special-char-uuid",
                              "version": 1,
                              "roles": {
                                "customer": { "number": 10003 }
                              },
                              "company": {
                                "name": "Müller & Söhne GmbH"
                              },
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

    Page<Contact> contactPage = lexofficeApi.contact().fetch().name(companyName).get();

    assertThat(contactPage).isNotNull();
    assertThat(contactPage.getContent()).hasSize(1);
    assertThat(contactPage.getContent().get(0).getCompany().getName()).isEqualTo(companyName);
  }

  @Test
  void fetchContactsWithPagination() {
    stubFor(
        get(urlPathEqualTo("/v1/contacts"))
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
                              "id": "be9475a4-ef80-442b-95eb-e56a7b2d5596",
                              "version": 1,
                              "company": {
                                "name": "Berliner Kindl GmbH"
                              },
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

    Page<Contact> contactPage = lexofficeApi.contact().fetch().page(0).pageSize(10).get();

    assertThat(contactPage).isNotNull();
    assertThat(contactPage.getTotalPages()).isEqualTo(5);
    assertThat(contactPage.getTotalElements()).isEqualTo(50);
    assertThat(contactPage.getSize()).isEqualTo(10);
    assertThat(contactPage.getFirst()).isTrue();
    assertThat(contactPage.getLast()).isFalse();
  }

  @Test
  void createContact() {
    stubFor(
        post(urlPathEqualTo("/v1/contacts"))
            .willReturn(
                aResponse()
                    .withStatus(201)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "e1234567-ef80-442b-95eb-e56a7b2d5596",
                          "resourceUri": "https://api.lexoffice.io/v1/contacts/e1234567-ef80-442b-95eb-e56a7b2d5596",
                          "createdDate": "2023-06-20T10:30:00.000+02:00",
                          "updatedDate": "2023-06-20T10:30:00.000+02:00",
                          "version": 0
                        }
                        """)));

    Contact newContact =
        Contact.builder()
            .version(0L)
            .company(Company.builder().name("Neue Firma GmbH").build())
            .roles(Roles.builder().build())
            .build();

    ItemCreatedResult result = lexofficeApi.contact().create(newContact);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("e1234567-ef80-442b-95eb-e56a7b2d5596");
    assertThat(result.getResourceUri()).contains("/v1/contacts/");
    assertThat(result.getVersion()).isEqualTo(0L);

    verify(
        postRequestedFor(urlPathEqualTo("/v1/contacts"))
            .withRequestBody(
                equalToJson(
                    """
                    {
                      "version": 0,
                      "company": {
                        "name": "Neue Firma GmbH"
                      },
                      "roles": {}
                    }
                    """,
                    true,
                    true)));
  }

  @Test
  void updateContact() {
    String contactId = "be9475a4-ef80-442b-95eb-e56a7b2d5596";

    stubFor(
        put(urlPathEqualTo("/v1/contacts/" + contactId))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "be9475a4-ef80-442b-95eb-e56a7b2d5596",
                          "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
                          "version": 2,
                          "roles": {
                            "customer": { "number": 10001 }
                          },
                          "company": {
                            "name": "Berliner Kindl GmbH - Updated"
                          },
                          "archived": false
                        }
                        """)));

    Contact contactToUpdate =
        Contact.builder()
            .id(contactId)
            .version(1L)
            .company(Company.builder().name("Berliner Kindl GmbH - Updated").build())
            .build();

    Contact updatedContact = lexofficeApi.contact().update(contactToUpdate);

    assertThat(updatedContact).isNotNull();
    assertThat(updatedContact.getId()).isEqualTo(contactId);
    assertThat(updatedContact.getVersion()).isEqualTo(2L);
    assertThat(updatedContact.getCompany().getName()).isEqualTo("Berliner Kindl GmbH - Updated");

    verify(
        putRequestedFor(urlPathEqualTo("/v1/contacts/" + contactId))
            .withRequestBody(
                equalToJson(
                    """
                    {
                      "id": "be9475a4-ef80-442b-95eb-e56a7b2d5596",
                      "version": 1,
                      "company": {
                        "name": "Berliner Kindl GmbH - Updated"
                      }
                    }
                    """,
                    true,
                    true)));
  }
}
