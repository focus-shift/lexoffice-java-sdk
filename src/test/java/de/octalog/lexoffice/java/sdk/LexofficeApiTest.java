package de.octalog.lexoffice.java.sdk;


import de.octalog.lexoffice.java.sdk.model.Contact;
import de.octalog.lexoffice.java.sdk.model.EventSubscription;
import de.octalog.lexoffice.java.sdk.model.Page;
import de.octalog.lexoffice.java.sdk.model.Voucher;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@Disabled
class LexofficeApiTest {

    protected LexofficeApi getLexofficeApi() {
        String apiToken = System.getenv("LEXOFFICE_API_TOKEN");

        if (apiToken == null || apiToken.isBlank()) {
            fail("Please configure env variable LEXOFFICE_API_TOKEN");
        }

        return new LexofficeApiBuilder()
                .apiToken(apiToken)
                .build();
    }

    @SneakyThrows
    @Test
    void fetchContacts() {
        LexofficeApi lexofficeApi = getLexofficeApi();

        Page<Contact> resultList = lexofficeApi.contact().fetch().get();

        assertThat(resultList).isNotNull();
    }

    @SneakyThrows
    @Test
    void fetchVoucherList() {
        LexofficeApi lexofficeApi = getLexofficeApi();

        Page<Voucher> resultList = lexofficeApi.voucherList().get();

        assertThat(resultList).isNotNull();
    }

    @SneakyThrows
    @Test
    void getEventSubscriptions() {
        LexofficeApi lexofficeApi = getLexofficeApi();

        List<EventSubscription> resultList = lexofficeApi.eventSubscriptions().getAll();

        assertThat(resultList).isNotNull();
    }

}
