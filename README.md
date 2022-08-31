# Lexoffice Java SDK

This project is an unofficial Java SDK for the [lexoffice Public API](https://www.lexoffice.de/funktionen/public-api/).


## Requirements

This project requires Java 17 runtime.


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
* [ ] Recurring Templates Endpoint
* [x] Voucherlist Endpoint
* [ ] Vouchers Endpoint


## Reference documentation

See [lexoffice API Documentation](https://developers.lexoffice.io/docs/).


## License

Lexoffice Java SDK is Open Source software released under the [MIT license](LICENSE).


## Credits

This project is based on the work of [rocketbase-io/lexoffice-api](https://github.com/rocketbase-io/lexoffice-api) and
forked from commit [c192de26e150ff08559d91713e634fe233dc8e08](https://github.com/rocketbase-io/lexoffice-api/commit/c192de26e150ff08559d91713e634fe233dc8e08)
