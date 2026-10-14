package de.focus_shift.lexoffice.java.sdk.model;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import static org.assertj.core.api.Assertions.assertThat;

class ContactTest {

    // same mapper as RequestContext uses for the API calls
    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    private final String companyContactJson = """
            {
              "id": "be9475a4-ef80-442b-95eb-e56a7b2d5596",
              "version": 3,
              "roles": { "customer": { "number": 10001 } },
              "company": {
                "name": "Berliner Kindl GmbH",
                "contactPersons": [
                  {
                    "salutation": "Frau",
                    "firstName": "Erika",
                    "lastName": "Musterfrau",
                    "primary": true,
                    "emailAddress": "erika@example.org"
                  }
                ]
              },
              "xRechnung": {
                "buyerReference": "04011000-1234512345-06",
                "vendorNumberAtCustomer": "70123456"
              },
              "archived": false
            }
            """;

    @Test
    void readsXRechnungAndPrimaryContactPerson() {
        final Contact contact = jsonMapper.readValue(companyContactJson, Contact.class);

        assertThat(contact.getXRechnung().getBuyerReference()).isEqualTo("04011000-1234512345-06");
        assertThat(contact.getXRechnung().getVendorNumberAtCustomer()).isEqualTo("70123456");
        assertThat(contact.getCompany().getContactPersons().getFirst().getPrimary()).isTrue();
    }

    @Test
    void writesXRechnungAndPrimaryContactPersonBack() {
        final Contact contact = jsonMapper.readValue(companyContactJson, Contact.class);

        final JsonNode written = jsonMapper.valueToTree(contact);

        assertThat(written.at("/xRechnung/buyerReference").asString()).isEqualTo("04011000-1234512345-06");
        assertThat(written.at("/xRechnung/vendorNumberAtCustomer").asString()).isEqualTo("70123456");
        assertThat(written.at("/company/contactPersons/0/primary").asBoolean()).isTrue();
        // lombok names the accessor getXRechnung, which jackson would map to a second property "xrechnung"
        assertThat(written.has("xrechnung")).isFalse();
    }

    @Test
    void omitsXRechnungWhenNotSet() {
        final JsonNode written = jsonMapper.valueToTree(Contact.builder().id("be9475a4-ef80-442b-95eb-e56a7b2d5596").build());

        assertThat(written.has("xRechnung")).isFalse();
        assertThat(written.has("xrechnung")).isFalse();
    }

    @Test
    void omitsPrimaryForPersonContacts() {
        final Contact contact = Contact.builder()
                .person(ContactPerson.builder().salutation("Herr").firstName("Max").lastName("Mustermann").build())
                .build();

        final JsonNode written = jsonMapper.valueToTree(contact);

        assertThat(written.at("/person/lastName").asString()).isEqualTo("Mustermann");
        assertThat(written.at("/person").has("primary")).isFalse();
    }
}
