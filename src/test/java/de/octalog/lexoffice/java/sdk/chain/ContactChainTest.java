package de.octalog.lexoffice.java.sdk.chain;


import de.octalog.lexoffice.java.sdk.LexofficeApi;
import de.octalog.lexoffice.java.sdk.model.Contact;
import de.octalog.lexoffice.java.sdk.model.Page;
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
