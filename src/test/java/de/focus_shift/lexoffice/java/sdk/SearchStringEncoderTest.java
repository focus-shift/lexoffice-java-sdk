package de.focus_shift.lexoffice.java.sdk;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SearchStringEncoderTest {

    @Test
    void htmlEncodeReturnsNullForNullInput() {
        assertThat(SearchStringEncoder.htmlEncode(null)).isNull();
    }

    @Test
    void htmlEncodeReturnsEmptyStringForEmptyInput() {
        assertThat(SearchStringEncoder.htmlEncode("")).isEqualTo("");
    }

    @Test
    void htmlEncodeReturnsUnchangedStringWithoutSpecialCharacters() {
        assertThat(SearchStringEncoder.htmlEncode("Berliner Kindl GmbH"))
                .isEqualTo("Berliner Kindl GmbH");
    }

    @Test
    void htmlEncodeEncodesAmpersand() {
        assertThat(SearchStringEncoder.htmlEncode("johnson & partner"))
                .isEqualTo("johnson &amp; partner");
    }

    @Test
    void htmlEncodeEncodesLessThan() {
        assertThat(SearchStringEncoder.htmlEncode("value < 100"))
                .isEqualTo("value &lt; 100");
    }

    @Test
    void htmlEncodeEncodesGreaterThan() {
        assertThat(SearchStringEncoder.htmlEncode("value > 50"))
                .isEqualTo("value &gt; 50");
    }

    @Test
    void htmlEncodeEncodesMultipleSpecialCharacters() {
        assertThat(SearchStringEncoder.htmlEncode("a < b & c > d"))
                .isEqualTo("a &lt; b &amp; c &gt; d");
    }

    @Test
    void htmlEncodeEncodesMultipleAmpersands() {
        assertThat(SearchStringEncoder.htmlEncode("Müller & Söhne & Partner GmbH"))
                .isEqualTo("Müller &amp; Söhne &amp; Partner GmbH");
    }

    @Test
    void htmlEncodePreservesUmlauts() {
        assertThat(SearchStringEncoder.htmlEncode("Müller & Söhne"))
                .isEqualTo("Müller &amp; Söhne");
    }

    @Test
    void htmlEncodeDoesNotDoubleEncodeAlreadyEncodedAmpersand() {
        // If someone passes already encoded text, it will be encoded again
        // This is expected behavior - the input should be raw text
        assertThat(SearchStringEncoder.htmlEncode("&amp;"))
                .isEqualTo("&amp;amp;");
    }

    @Test
    void htmlEncodeHandlesOnlyAmpersand() {
        assertThat(SearchStringEncoder.htmlEncode("&"))
                .isEqualTo("&amp;");
    }

    @Test
    void htmlEncodeHandlesOnlyLessThan() {
        assertThat(SearchStringEncoder.htmlEncode("<"))
                .isEqualTo("&lt;");
    }

    @Test
    void htmlEncodeHandlesOnlyGreaterThan() {
        assertThat(SearchStringEncoder.htmlEncode(">"))
                .isEqualTo("&gt;");
    }

    @Test
    void htmlEncodeHandlesConsecutiveSpecialCharacters() {
        assertThat(SearchStringEncoder.htmlEncode("<<&&>>"))
                .isEqualTo("&lt;&lt;&amp;&amp;&gt;&gt;");
    }

    @Test
    void htmlEncodePreservesWhitespace() {
        assertThat(SearchStringEncoder.htmlEncode("  Schmidt & Partner  "))
                .isEqualTo("  Schmidt &amp; Partner  ");
    }
}
