package de.octalog.lexware.java.sdk;


import de.octalog.lexware.java.sdk.model.Contact;
import de.octalog.lexware.java.sdk.model.EventSubscription;
import de.octalog.lexware.java.sdk.model.Page;
import de.octalog.lexware.java.sdk.model.Voucher;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@Disabled
class LexwareApiTest {

    protected LexwareApi getLexwareApi() {
        String apiToken = System.getenv("LEXWARE_API_TOKEN");

        if (apiToken == null || apiToken.isBlank()) {
            fail("Please configure env variable LEXWARE_API_TOKEN");
        }

        return new LexwareApiBuilder()
                .apiToken(apiToken)
                .build();
    }

    @SneakyThrows
    @Test
    void fetchContacts() {
        LexwareApi lexwareApi = getLexwareApi();

        Page<Contact> resultList = lexwareApi.contact().fetch().get();

        assertThat(resultList).isNotNull();
    }

    @SneakyThrows
    @Test
    void fetchVoucherList() {
        LexwareApi lexwareApi = getLexwareApi();

        Page<Voucher> resultList = lexwareApi.voucherList().get();

        assertThat(resultList).isNotNull();
    }

    @SneakyThrows
    @Test
    void getEventSubscriptions() {
        LexwareApi lexwareApi = getLexwareApi();

        List<EventSubscription> resultList = lexwareApi.eventSubscriptions().getAll();

        assertThat(resultList).isNotNull();
    }

}
