package de.octalog.lexoffice.java.sdk.model;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class RecurringTemplateTest {

    private final String json = """
            {
              "id": "ac1d66a8-6d59-408b-9413-d56b1db7946f",
              "organizationId": "aa93e8a8-2aa3-470b-b914-caad8a255dd8",
              "createdDate": "2023-02-10T09:00:00.000+01:00",
              "updatedDate": "2023-02-10T09:00:00.000+01:00",
              "version": 0,
              "language": "de",
              "archived": false,
              "address": {
                "contactId": "464f4881-7a8c-4dc4-87de-7c6fd9a506b8",
                "name": "Bike & Ride GmbH & Co. KG",
                "supplement": "Gebäude 10",
                "street": "Musterstraße 42",
                "city": "Freiburg",
                "zip": "79112",
                "countryCode": "DE"
              },
              "lineItems": [
                {
                  "id": "97b98491-e953-4dc9-97a9-ae437a8052b4",
                  "type": "material",
                  "name": "Abus Kabelschloss Primo 590 ",
                  "description": "· 9,5 mm starkes, smoke-mattes Spiralkabel mit integrierter Halterlösung zur Befestigung am Sattelklemmbolzen · bewährter Qualitäts-Schließzylinder mit praktischem Wendeschlüssel · KabelØ: 9,5 mm, Länge: 150 cm",
                  "quantity": 2,
                  "unitName": "Stück",
                  "unitPrice": {
                    "currency": "EUR",
                    "netAmount": 13.4,
                    "grossAmount": 15.95,
                    "taxRatePercentage": 19
                  },
                  "discountPercentage": 50,
                  "lineItemAmount": 13.4
                },
                {
                  "id": "dc4c805b-7df1-4310-a548-22be4499eb04",
                  "type": "service",
                  "name": "Aufwändige Montage",
                  "description": "Aufwand für arbeitsintensive Montagetätigkeit",
                  "quantity": 1,
                  "unitName": "Stunde",
                  "unitPrice": {
                    "currency": "EUR",
                    "netAmount": 8.32,
                    "grossAmount": 8.9,
                    "taxRatePercentage": 7
                  },
                  "discountPercentage": 0,
                  "lineItemAmount": 8.32
                },
                {
                  "id": null,
                  "type": "custom",
                  "name": "Energieriegel Testpaket",
                  "description": null,
                  "quantity": 1,
                  "unitName": "Stück",
                  "unitPrice": {
                    "currency": "EUR",
                    "netAmount": 5,
                    "grossAmount": 5,
                    "taxRatePercentage": 0
                  },
                  "discountPercentage": 0,
                  "lineItemAmount": 5
                },
                {
                  "type": "text",
                  "name": "Freitextposition",
                  "description": "This item type can contain either a name or a description or both."
                }
              ],
              "totalPrice": {
                "currency": "EUR",
                "totalNetAmount": 26.72,
                "totalGrossAmount": 29.85,
                "totalTaxAmount": 3.13,
                "totalDiscountAbsolute": null,
                "totalDiscountPercentage": null
              },
              "taxAmounts": [
                {
                  "taxRatePercentage": 0,
                  "taxAmount": 0,
                  "netAmount": 5
                },
                {
                  "taxRatePercentage": 7,
                  "taxAmount": 0.58,
                  "netAmount": 8.32
                },
                {
                  "taxRatePercentage": 19,
                  "taxAmount": 2.55,
                  "netAmount": 13.4
                }
              ],
              "taxConditions": {
                "taxType": "net",
                "taxTypeNote": null
              },
              "paymentConditions": {
                "paymentTermLabel": "10 Tage - 3 %, 30 Tage netto",
                "paymentTermLabelTemplate": "{discountRange} Tage -{discount}, {paymentRange} Tage netto",
                "paymentTermDuration": 30,
                "paymentDiscountConditions": {
                  "discountPercentage": 3,
                  "discountRange": 10
                }
              },
              "title": "Rechnung",
              "introduction": "Ihre bestellten Positionen stellen wir Ihnen hiermit in Rechnung",
              "remark": "Vielen Dank für Ihren Einkauf",
              "recurringTemplateSettings": {
                "id": "9c5b8bde-7d36-49e8-af5c-4fbe7dc9fa01",
                "startDate": "2023-03-01",
                "endDate": "2023-06-30",
                "finalize": true,
                "shippingType": "service",
                "retroactiveInvoice": false,
                "executionInterval": "MONTHLY",
                "nextExecutionDate": "2023-03-01",
                "lastExecutionFailed": false,
                "lastExecutionErrorMessage": null,
                "executionStatus": "ACTIVE"
              }
            }
            """;

    @Test
    void deserializeRecurringTemplate() {
        JsonMapper jsonMapper = JsonMapper.builder().build();

        RecurringTemplate template = jsonMapper.readValue(json, RecurringTemplate.class);

        // Basic fields
        assertThat(template.getId()).isEqualTo("ac1d66a8-6d59-408b-9413-d56b1db7946f");
        assertThat(template.getOrganizationId()).isEqualTo("aa93e8a8-2aa3-470b-b914-caad8a255dd8");
        assertThat(template.getVersion()).isEqualTo(0);
        assertThat(template.getLanguage()).isEqualTo("de");
        assertThat(template.isArchived()).isFalse();
        assertThat(template.getCreatedDate()).isNotNull();
        assertThat(template.getUpdatedDate()).isNotNull();

        // Address
        assertThat(template.getAddress()).isNotNull();
        assertThat(template.getAddress().getContactId()).isEqualTo("464f4881-7a8c-4dc4-87de-7c6fd9a506b8");
        assertThat(template.getAddress().getName()).isEqualTo("Bike & Ride GmbH & Co. KG");
        assertThat(template.getAddress().getSupplement()).isEqualTo("Gebäude 10");
        assertThat(template.getAddress().getStreet()).isEqualTo("Musterstraße 42");
        assertThat(template.getAddress().getCity()).isEqualTo("Freiburg");
        assertThat(template.getAddress().getZip()).isEqualTo("79112");
        assertThat(template.getAddress().getCountryCode()).isEqualTo("DE");

        // Line items
        assertThat(template.getLineItems()).hasSize(4);
        assertThat(template.getLineItems().get(0).getType()).isEqualTo(LineItemType.MATERIAL);
        assertThat(template.getLineItems().get(0).getName()).isEqualTo("Abus Kabelschloss Primo 590 ");
        assertThat(template.getLineItems().get(0).getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(2));
        assertThat(template.getLineItems().get(0).getUnitPrice().getCurrency()).isEqualTo(Currency.EUR);
        assertThat(template.getLineItems().get(0).getDiscountPercentage()).isEqualTo(50L);

        assertThat(template.getLineItems().get(1).getType()).isEqualTo(LineItemType.SERVICE);
        assertThat(template.getLineItems().get(2).getType()).isEqualTo(LineItemType.CUSTOM);
        assertThat(template.getLineItems().get(3).getType()).isEqualTo(LineItemType.TEXT);

        // Total price
        assertThat(template.getTotalPrice()).isNotNull();
        assertThat(template.getTotalPrice().getCurrency()).isEqualTo(Currency.EUR);
        assertThat(template.getTotalPrice().getTotalNetAmount()).isEqualByComparingTo(BigDecimal.valueOf(26.72));
        assertThat(template.getTotalPrice().getTotalGrossAmount()).isEqualByComparingTo(BigDecimal.valueOf(29.85));
        assertThat(template.getTotalPrice().getTotalTaxAmount()).isEqualByComparingTo(BigDecimal.valueOf(3.13));

        // Tax amounts
        assertThat(template.getTaxAmounts()).hasSize(3);
        assertThat(template.getTaxAmounts().get(0).getTaxRatePercentage()).isEqualTo(0L);
        assertThat(template.getTaxAmounts().get(1).getTaxRatePercentage()).isEqualTo(7L);
        assertThat(template.getTaxAmounts().get(2).getTaxRatePercentage()).isEqualTo(19L);

        // Tax conditions
        assertThat(template.getTaxConditions()).isNotNull();
        assertThat(template.getTaxConditions().getTaxType()).isEqualTo(TaxType.NET);

        // Payment conditions
        assertThat(template.getPaymentConditions()).isNotNull();
        assertThat(template.getPaymentConditions().getPaymentTermLabel()).isEqualTo("10 Tage - 3 %, 30 Tage netto");
        assertThat(template.getPaymentConditions().getPaymentTermLabelTemplate()).isEqualTo("{discountRange} Tage -{discount}, {paymentRange} Tage netto");
        assertThat(template.getPaymentConditions().getPaymentTermDuration()).isEqualTo(30);
        assertThat(template.getPaymentConditions().getPaymentDiscountConditions()).isNotNull();
        assertThat(template.getPaymentConditions().getPaymentDiscountConditions().getDiscountPercentage()).isEqualByComparingTo(BigDecimal.valueOf(3));
        assertThat(template.getPaymentConditions().getPaymentDiscountConditions().getDiscountRange()).isEqualTo(10);

        // Title, introduction, remark
        assertThat(template.getTitle()).isEqualTo("Rechnung");
        assertThat(template.getIntroduction()).isEqualTo("Ihre bestellten Positionen stellen wir Ihnen hiermit in Rechnung");
        assertThat(template.getRemark()).isEqualTo("Vielen Dank für Ihren Einkauf");

        // Recurring template settings
        assertThat(template.getRecurringTemplateSettings()).isNotNull();
        assertThat(template.getRecurringTemplateSettings().getId()).isEqualTo("9c5b8bde-7d36-49e8-af5c-4fbe7dc9fa01");
        assertThat(template.getRecurringTemplateSettings().getStartDate()).isNotNull();
        assertThat(template.getRecurringTemplateSettings().getEndDate()).isNotNull();
        assertThat(template.getRecurringTemplateSettings().getFinalize()).isTrue();
        assertThat(template.getRecurringTemplateSettings().getShippingType()).isEqualTo(ShippingType.SERVICE);
        assertThat(template.getRecurringTemplateSettings().getRetroactiveInvoice()).isFalse();
        assertThat(template.getRecurringTemplateSettings().getExecutionInterval()).isEqualTo(ExecutionInterval.MONTHLY);
        assertThat(template.getRecurringTemplateSettings().getNextExecutionDate()).isNotNull();
        assertThat(template.getRecurringTemplateSettings().getLastExecutionFailed()).isFalse();
        assertThat(template.getRecurringTemplateSettings().getLastExecutionErrorMessage()).isNull();
        assertThat(template.getRecurringTemplateSettings().getExecutionStatus()).isEqualTo(ExecutionStatus.ACTIVE);
    }
}
