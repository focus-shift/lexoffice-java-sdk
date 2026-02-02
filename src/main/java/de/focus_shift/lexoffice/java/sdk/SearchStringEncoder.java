package de.focus_shift.lexoffice.java.sdk;

/**
 * Utility class for encoding search strings according to Lexoffice API requirements.
 *
 * <p>Due to technical constraints, for some endpoints (contact, vouchers, voucherlist),
 * search strings require special handling: HTML special characters {@code &}, {@code <},
 * and {@code >} need to be sent in their HTML encoded form before URL encoding is applied.
 *
 * <p>The HTML encoding needs to match the canonical representation as stored in the
 * Lexware database. Unicode character encoding or any other form of representation
 * should not be used.
 *
 * @see <a href="https://developers.lexware.io/partner/docs/#faq-search-string-encoding">
 *     Lexoffice API - Search String Encoding</a>
 */
public final class SearchStringEncoder {

    private SearchStringEncoder() {
        // utility class
    }

    /**
     * HTML encodes special characters in search strings for Lexoffice API endpoints
     * that require this encoding (contact, vouchers, voucherlist).
     *
     * <p>The following characters are encoded:
     * <ul>
     *   <li>{@code &} → {@code &amp;}</li>
     *   <li>{@code <} → {@code &lt;}</li>
     *   <li>{@code >} → {@code &gt;}</li>
     * </ul>
     *
     * <p>Example: {@code "johnson & partner"} becomes {@code "johnson &amp; partner"}
     *
     * @param value the search string to encode, may be null
     * @return the HTML encoded string, or null if input was null
     * @see <a href="https://developers.lexware.io/partner/docs/#faq-search-string-encoding">
     *     Lexoffice API - Search String Encoding</a>
     */
    public static String htmlEncode(String value) {
        if (value == null) {
            return null;
        }
        // Order matters: & must be encoded first to avoid double-encoding
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
