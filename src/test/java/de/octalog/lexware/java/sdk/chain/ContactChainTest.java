package de.octalog.lexware.java.sdk.chain;


import de.octalog.lexware.java.sdk.LexwareApi;
import de.octalog.lexware.java.sdk.model.Contact;
import de.octalog.lexware.java.sdk.model.Page;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ContactChainTest {

    @Test
    @Disabled
    void exampleUsage() {
        LexwareApi lexwareApi = Mockito.mock(LexwareApi.class);

        Page<Contact> resultList = lexwareApi.contact().fetch()
                .customer(true)
                .get();
    }
}
