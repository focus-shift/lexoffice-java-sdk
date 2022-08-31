package de.focus_shift.lexoffice.java.sdk.chain;


import de.focus_shift.lexoffice.java.sdk.LexofficeApi;
import de.focus_shift.lexoffice.java.sdk.model.Contact;
import de.focus_shift.lexoffice.java.sdk.model.Page;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ContactChainTest {

    @Test
    @Disabled
    void exampleUsage() {
        LexofficeApi lexofficeApi = Mockito.mock(LexofficeApi.class);

        Page<Contact> resultList = lexofficeApi.contact().fetch()
                .customer(true)
                .get();
    }
}
