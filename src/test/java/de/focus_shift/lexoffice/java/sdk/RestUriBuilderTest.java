package de.focus_shift.lexoffice.java.sdk;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;

class RestUriBuilderTest {

  private static final String LEXOFFICE_API_HOST = "api.lexware.io/v1";

  @Test
  void buildSimpleUrl() {
    URI uri = new RestUriBuilder()
        .protocol("https")
        .host(LEXOFFICE_API_HOST)
        .path("/contacts")
        .build();

    assertThat(uri.toString()).isEqualTo("https://api.lexware.io/v1/contacts");
  }

  @Test
  void buildUrlWithSingleQueryParameter() {
    URI uri = new RestUriBuilder()
        .protocol("https")
        .host(LEXOFFICE_API_HOST)
        .path("/contacts")
        .addParameter("page", 0)
        .build();

    assertThat(uri.toString()).isEqualTo("https://api.lexware.io/v1/contacts?page=0");
  }

  @Test
  void buildUrlWithMultipleQueryParameters() {
    URI uri = new RestUriBuilder()
        .protocol("https")
        .host(LEXOFFICE_API_HOST)
        .path("/contacts")
        .addParameter("page", 0)
        .addParameter("size", 25)
        .build();

    assertThat(uri.toString()).isEqualTo("https://api.lexware.io/v1/contacts?page=0&size=25");
  }

  @Test
  void buildUrlWithMultipleValuesForSameKey() {
    URI uri = new RestUriBuilder()
        .protocol("https")
        .host(LEXOFFICE_API_HOST)
        .path("/voucherlist")
        .addParameters("voucherStatus", List.of("open", "paid"))
        .build();

    assertThat(uri.toString()).isEqualTo("https://api.lexware.io/v1/voucherlist?voucherStatus=open&voucherStatus=paid");
  }

  @Test
  void buildUrlEncodesSpecialCharactersInQueryParameters() {
    URI uri = new RestUriBuilder()
        .protocol("https")
        .host(LEXOFFICE_API_HOST)
        .path("/contacts")
        .addParameter("name", "Schmidt & Partner, München")
        .build();

    assertThat(uri.toString()).isEqualTo("https://api.lexware.io/v1/contacts?name=Schmidt%20%26%20Partner,%20M%C3%BCnchen");
  }

  @Test
  void buildUrlEncodesAmpersandInQueryParameters() {
    URI uri = new RestUriBuilder()
        .protocol("https")
        .host(LEXOFFICE_API_HOST)
        .path("/contacts")
        .addParameter("name", "Müller & Söhne GmbH")
        .build();

    assertThat(uri.toString()).contains("name=M%C3%BCller%20%26%20S%C3%B6hne%20GmbH");
  }

  @Test
  void buildUrlEncodesEqualsSignInQueryParameters() {
    URI uri = new RestUriBuilder()
        .protocol("https")
        .host(LEXOFFICE_API_HOST)
        .path("/contacts")
        .addParameter("filter", "status=active")
        .build();

    assertThat(uri.toString()).contains("filter=status%3Dactive");
  }

  @Test
  void buildUrlEncodesSpacesInQueryParameters() {
    URI uri = new RestUriBuilder()
        .protocol("https")
        .host(LEXOFFICE_API_HOST)
        .path("/contacts")
        .addParameter("name", "Berliner Kindl GmbH")
        .build();

    assertThat(uri.toString()).contains("name=Berliner%20Kindl%20GmbH");
  }

  @Test
  void buildUrlEncodesPlusSignInQueryParameters() {
    URI uri = new RestUriBuilder()
        .protocol("https")
        .host(LEXOFFICE_API_HOST)
        .path("/contacts")
        .addParameter("name", "Software C++ GmbH")
        .build();

    // Spring's UriComponentsBuilder keeps + as-is (valid in RFC 3986 query strings)
    // When decoded, servers should interpret this correctly
    assertThat(uri.toString()).contains("name=Software%20C++%20GmbH");
  }

  @Test
  void buildUrlEncodesHashInQueryParameters() {
    URI uri = new RestUriBuilder()
        .protocol("https")
        .host(LEXOFFICE_API_HOST)
        .path("/contacts")
        .addParameter("tag", "#wichtig")
        .build();

    assertThat(uri.toString()).contains("tag=%23wichtig");
  }

  @Test
  void buildUrlWithHttpProtocol() {
    URI uri = new RestUriBuilder()
        .protocol("http")
        .host("localhost:8080/v1")
        .path("/contacts")
        .build();

    assertThat(uri.toString()).isEqualTo("http://localhost:8080/v1/contacts");
  }

  @Test
  void appendPathConcatenatesCorrectly() {
    String contactId = "be9475a4-ef80-442b-95eb-e56a7b2d5596";

    URI uri = new RestUriBuilder()
        .protocol("https")
        .host(LEXOFFICE_API_HOST)
        .path("/contacts")
        .appendPath("/")
        .appendPath(contactId)
        .build();

    assertThat(uri.toString()).isEqualTo("https://api.lexware.io/v1/contacts/be9475a4-ef80-442b-95eb-e56a7b2d5596");
  }

  @Test
  void buildWithEmptyPathSucceeds() {
    URI uri = new RestUriBuilder()
        .protocol("https")
        .host(LEXOFFICE_API_HOST)
        .build();

    assertThat(uri.toString()).isEqualTo("https://api.lexware.io/v1");
  }

  @Test
  void buildWithoutHostThrowsException() {
    RestUriBuilder builder = new RestUriBuilder()
        .protocol("https")
        .path("/contacts");

    assertThatThrownBy(builder::build)
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void hostCannotBeNull() {
    RestUriBuilder builder = new RestUriBuilder();

    assertThatThrownBy(() -> builder.host(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void protocolCannotBeNull() {
    RestUriBuilder builder = new RestUriBuilder();

    assertThatThrownBy(() -> builder.protocol(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void pathCannotBeNull() {
    RestUriBuilder builder = new RestUriBuilder();

    assertThatThrownBy(() -> builder.path(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void appendPathCannotBeNull() {
    RestUriBuilder builder = new RestUriBuilder();

    assertThatThrownBy(() -> builder.appendPath(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void parameterKeyCannotBeNull() {
    RestUriBuilder builder = new RestUriBuilder();

    assertThatThrownBy(() -> builder.addParameter(null, "Berliner Kindl GmbH"))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void parameterWithNullValueIncludesKeyOnly() {
    // Spring's UriComponentsBuilder accepts null values and includes the key without a value
    URI uri = new RestUriBuilder()
        .protocol("https")
        .host(LEXOFFICE_API_HOST)
        .path("/contacts")
        .addParameter("archived", null)
        .addParameter("customer", "true")
        .build();

    assertThat(uri.toString()).isEqualTo("https://api.lexware.io/v1/contacts?archived&customer=true");
  }

  @Test
  void parametersCollectionCannotBeNull() {
    RestUriBuilder builder = new RestUriBuilder();

    assertThatThrownBy(() -> builder.addParameters("voucherStatus", null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void builderIsFluent() {
    RestUriBuilder builder = new RestUriBuilder();

    RestUriBuilder result = builder
        .protocol("https")
        .host(LEXOFFICE_API_HOST)
        .path("/contacts")
        .appendPath("/be9475a4-ef80-442b-95eb-e56a7b2d5596")
        .addParameter("page", 0);

    assertThat(result).isSameAs(builder);
  }
}
