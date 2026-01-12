# Lexoffice Java SDK

This project is an unofficial Java SDK for the [Lexware Office Public API](https://office.lexware.de/funktionen/public-api/).

Use 3.x to access the api via https://api.lexware.io instead of https://api.lexoffice.io.

## Requirements


| lexoffice-java-sdk | Java | Spring Boot |
|--------------------|------|-------------|
| 3.5                | 21   | 4.0.x       |
| 3.2                | 21   | 3.5.x       |
| 3.0                | 21   | 3.4.x       |
| 2.1                | 21   | 3.4.x       |
| 2.x                | 21   | 3.3.x       |
| 1.x                | 17   | 3.1.x       |
| 0.x                | 17   | 2.7.x       |


## Usage

Include lexoffice java-sdk to your project:

```xml
<dependency>
    <groupId>de.focus-shift.lexoffice</groupId>
    <artifactId>java-sdk</artifactId>
    <version>...</version>
</dependency>
```

```java
String apiToken = "";
LexofficeApi lexofficeApi = new LexofficeApiBuilder().apiToken(apiToken).build();
```

See FAQ [Get an API key](https://developers.lexoffice.io/docs/#faq-get-an-api-key) to generate an API key.


## Implementation Status

The following Endpoints are implemented based on lexoffice developer documentation [change-log](https://developers.lexoffice.io/docs/#change-log) of 16.08.2022.

* [x] Contacts Endpoint
* [ ] Countries Endpoint
* [ ] Credit Notes Endpoint
* [ ] Delivery Notes Endpoint
* [ ] Dunnings Endpoint
* [ ] Down Payment Invoices Endpoint
* [x] Event Subscriptions Endpoint
* [ ] Files Endpoint
* [x] Invoices Endpoint
* [ ] Order Confirmations Endpoint
* [ ] Payments Endpoint
* [ ] Payment Conditions Endpoint
* [ ] Posting Categories Endpoint
* [ ] Profile Endpoint
* [x] Quotations
* [x] Recurring Templates Endpoint
* [x] Voucherlist Endpoint
* [ ] Vouchers Endpoint


## Reference documentation

See [lexoffice API Documentation](https://developers.lexoffice.io/docs/).


## License

Lexoffice Java SDK is Open Source software released under the [MIT license](LICENSE).


## Credits

This project is based on the work of [rocketbase-io/lexoffice-api](https://github.com/rocketbase-io/lexoffice-api)
and [focus-shift/lexoffice-java-sdk](https://github.com/focus-shift/lexoffice-java-sdk)
