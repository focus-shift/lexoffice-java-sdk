# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an unofficial Java SDK for the Lexware Office Public API (formerly Lexoffice). The SDK provides a fluent API for interacting with Lexware's REST API endpoints.

## Build & Development Commands

### Building
```bash
./mvnw clean install          # Build and install locally
./mvnw verify                 # Build and run tests
./mvnw clean package          # Build JAR without installing
```

### Testing
```bash
./mvnw test                   # Run all tests
./mvnw test -Dtest=ClassName  # Run a single test class
./mvnw test -Dtest=ClassName#methodName  # Run a single test method
```

### Release (requires credentials)
```bash
./mvnw clean verify -P release  # Build with GPG signing and Maven Central publishing
```

## Architecture

### Core Components

**LexwareApi** - Main entry point created via `LexwareApiBuilder`. Provides access to endpoint-specific chains:
- `contact()` → ContactChain
- `invoice()` → InvoiceChain
- `quotation()` → QuotationChain
- `voucherList()` → VoucherListChain
- `eventSubscriptions()` → EventSubscriptionChain

**LexwareApiBuilder** - Builder for creating `LexwareApi` instances:
- `apiToken(String)` - Set API token (required)
- `host(String)` - Override default host (default: `api.lexware.io/v1`)
- `throttleProvider(ThrottleProvider)` - Custom throttle management (optional)
- `build()` - Creates the API instance

**RequestContext** - Handles HTTP communication via Spring's RestTemplate:
- Manages authentication (Bearer token in Authorization header)
- Implements automatic throttling (510ms between requests by default)
- Configures Jackson ObjectMapper to ignore unknown properties
- All API calls are synchronized to enforce rate limiting

### Chain Pattern

The SDK uses a fluent chain pattern for building API requests:

1. **RequestChain** - Base class managing path resolution and context
2. **ExecutableRequestChain** - Extends RequestChain with URI building capabilities
3. **Endpoint-specific chains** (e.g., ContactChain, InvoiceChain) - Provide domain-specific operations

Each chain follows this pattern:
```java
api.contact()           // Returns ContactChain
   .fetch()             // Returns nested Fetch chain
   .email("test@test.com")  // Add filters (returns self for chaining)
   .page(0)             // Add pagination (returns self for chaining)
   .get();              // Execute request, returns Page<Contact>
```

**Chain operations**:
- **Builder methods** - Return `this` for chaining (e.g., `page()`, `email()`, `name()`)
- **Terminal methods** - Execute the request and return results (e.g., `get()`, `submit()`)

### Model Package

Contains POJOs representing Lexware API entities:
- Domain models use Lombok annotations (`@Data`, `@Builder`, etc.)
- Jackson handles JSON serialization/deserialization
- Models map directly to Lexware API JSON structures

### Package Structure

```
de.octalog.lexware.java.sdk/
├── LexwareApi.java                # Main API facade
├── LexwareApiBuilder.java         # Builder for API instances
├── RequestContext.java            # HTTP client and throttling
├── RestUriBuilder.java            # URI construction utility
├── chain/                         # Fluent API chains
│   ├── RequestChain.java          # Base chain class
│   ├── ExecutableRequestChain.java # Chain with execution
│   ├── ContactChain.java          # Contacts endpoint
│   ├── InvoiceChain.java          # Invoices endpoint
│   ├── QuotationChain.java        # Quotations endpoint
│   ├── VoucherListChain.java      # Voucher list endpoint
│   └── EventSubscriptionChain.java # Event subscriptions
└── model/                         # API domain models
    ├── Contact.java
    ├── Invoice.java
    ├── Quotation.java
    └── ...
```

## Implementation Status

Currently implemented endpoints:
- ✅ Contacts
- ✅ Event Subscriptions
- ✅ Invoices
- ✅ Quotations
- ✅ Voucherlist

Not yet implemented:
- Countries, Credit Notes, Delivery Notes, Dunnings, Down Payment Invoices
- Files, Order Confirmations, Payments, Payment Conditions
- Posting Categories, Profile, Recurring Templates, Vouchers

## Key Implementation Patterns

### Adding a New Endpoint

1. Create a model class in `model/` package
2. Create a chain class in `chain/` package extending `RequestChain`
3. Add nested static classes for different operations (Get, Fetch, Create, Update)
4. Each operation class extends `ExecutableRequestChain`
5. Add accessor method in `LexwareApi` class

### API Rate Limiting

The SDK enforces throttling automatically:
- Default: 510ms between API calls (managed in `RequestContext`)
- Custom throttling via `ThrottleProvider` interface
- All execute methods are synchronized to prevent concurrent calls

## Dependencies

- Java 21
- Spring Boot 3.5.x (using Spring Web for RestTemplate)
- Google Guava (for Optional and utilities)
- Jackson (for JSON processing)
- Apache HttpClient 5
- Lombok (compile-time only)

## Testing

Tests use Spring Boot Test framework:
- Unit tests for chain builders and API operations
- Test classes follow naming: `*Test.java` (e.g., `ContactChainTest.java`)
