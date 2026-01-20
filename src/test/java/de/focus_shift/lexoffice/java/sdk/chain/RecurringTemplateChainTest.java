package de.focus_shift.lexoffice.java.sdk.chain;

import de.focus_shift.lexoffice.java.sdk.LexofficeApi;
import de.focus_shift.lexoffice.java.sdk.model.Address;
import de.focus_shift.lexoffice.java.sdk.model.ExecutionInterval;
import de.focus_shift.lexoffice.java.sdk.model.ExecutionStatus;
import de.focus_shift.lexoffice.java.sdk.model.RecurringTemplate;
import de.focus_shift.lexoffice.java.sdk.model.RecurringTemplateSettings;
import de.focus_shift.lexoffice.java.sdk.model.ShippingType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;

class RecurringTemplateChainTest {

    @Test
    @Disabled
    void exampleUsage() {
        LexofficeApi lexofficeApi = Mockito.mock(LexofficeApi.class);

        // Example of how the RecurringTemplate model looks
        RecurringTemplate template = RecurringTemplate.builder()
                .id("template-uuid")
                .language("de")
                .title("Monthly Invoice")
                .introduction("Monthly recurring invoice")
                .remark("Thank you for your business")
                .address(Address.builder()
                        .contactId("contact-uuid")
                        .build())
                .recurringTemplateSettings(RecurringTemplateSettings.builder()
                        .startDate(new Date())
                        .endDate(new Date())
                        .finalize(true)
                        .shippingType(ShippingType.SERVICE)
                        .executionInterval(ExecutionInterval.MONTHLY)
                        .executionStatus(ExecutionStatus.ACTIVE)
                        .build())
                .build();

        // GET single template
        lexofficeApi.recurringTemplates().get("template-uuid");

        // GET paginated list with sorting
        lexofficeApi.recurringTemplates()
                .fetch()
                .page(0)
                .pageSize(50)
                .sortByNextExecutionDate(true)
                .get();
    }
}
