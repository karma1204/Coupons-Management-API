# Coupons-Management-API
RESTful API to manage and apply different types of discount coupons (cart-wise, product-wise, and BxGy) for an e-commerce platform, with the ability to easily add new types of coupons in the future.

---
## Features

### Implemented
- Create, read, update, and delete coupons
  - Coupon types - 
    - Cart-wise percentage discount
    - Cart wise fixed amount discount
    - Buy X Get Y free
- Apply coupons to shopping carts
  - Based on -
    - Active status
    - Validity date range
    - Coupon specific rules
  - Calculate discount amount for each applicable coupons
  - Return list of applicable coupons with discount amount sorted by **maximum discount**
- Easily extendable to add new coupon types and application strategy


### To Be implemented

#### Features 
- Filters for listing coupons
  - Validity date range
  - Update Coupon code for partial search query
  - Validity date time or range
- Coupon types
  - Product-wise percentage discount
  - Product-wise fixed amount discount
  - Payment method based discounts
- User based coupon management
  - Assign coupons to users
  - Track coupon usage per user
  - Limit coupon applications per user
- Advanced coupon application rules
  - Coupon Type based applicable coupons, that can be used at different stages of checkout 
- Endpoint to apply a selected coupon on cart

#### Technical Enhancements
- Database migration using Flyway or Liquibase
- Unit and integration tests
- Oauth2 based authentication and authorization
- Caching for frequently accessed data(when scaling)
- Monitoring and logging
---
## Setup
#### Prerequisites
 - Java 17 
 - Maven 
 - MySQL running locally

#### Steps
- git clone https://github.com/karma1204/Coupons-Management-API
- cd Coupons-Management-API
- Configure DB credentials in application.properties file.
- Run command `./mvnw spring-boot:run`
- Hit APIs using Postman or any other API testing tool at `http://localhost:8080/api/`

#### Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- Swagger for API documentation
- Postman for API testing
- Lombok to reduce boilerplate code
---
## API Endpoints
#### Create Coupon `POST  /coupons`
#### Get Coupon by ID `GET /coupons/{id}`
#### List Coupons (with params) `GET /coupons?active=true&type={couponType}`
#### Update Coupon `PUT /coupons/{id}`
#### Delete Coupon `DELETE /coupons/{id}`
#### Get Applicable Coupons for Cart `POST /applicable-coupons`

For more details on request and response structures you can : 
- Run the application and open `http://localhost:8080/swagger-ui.html` for Swagger UI documentation.
- Refer to the Postman collection in the repository.
---
## Design Considerations
- **Extensibility**: The application is designed to easily add new coupon types and application strategies by following the Open/Closed Principle. New coupon types can be added directly to the ENUM and new application strategies can be implemented by creating new classes that implement the `CouponApplicationStrategy` interface.
- **Separation of Concerns**: The application separates different responsibilities into different layers (Controller, Service, Repository, Strategy) to enhance maintainability and readability.
- **Validation**: Input validation is performed at the service layer to ensure data integrity and business rules are enforced.
- **Error Handling**: Error handling is implemented to provide meaningful error messages and HTTP status codes for different failure scenarios. Though the detailed error handling is yet to be implemented. 
- **Documentation**: Swagger is used to document the API endpoints, making it easier for developers to understand and use the API.
---
## Assumptions and Limitations

### Assumptions:
- All time-related fields (validFrom, validTill) are expected to be provided and processed in UTC.
- Using an active field to enable or disable a coupon irrespective of validity period.
- All price fields represent amounts in one major currency unit using decimal values. 
- Product quantities are assumed to be whole numbers.
- Coupon discounts are applied to the cart subtotal before taxes, shipping, or additional fees.
- The cart payload is assumed to be internally consistent (no duplicate product entries).

### Limitations:

#### Business Logic Limitations
- The current implementation does not support stacking multiple coupons on a single cart.
- Buy X Get Y rules do not currently support scenarios where free items are of the same product as buy items.
- Product-level eligibility (BuyXGetY or Product-wise coupons) is evaluated strictly based on product IDs, without category-level or attribute-based matching.
- Cart-wise coupons can be applied on a cart only once. 
- The system does not handle edge cases such as rounding issues in discount calculations.
- User-specific coupon management (assigning coupons to specific users, tracking usage per user) is not implemented.

#### Technical Limitations
- No caching mechanism is implemented, which may affect performance under high load.
- The system fetches all valid and active coupons at runtime; pagination or incremental evaluation is not applied for large datasets.
- Database migrations are not automated; manual schema updates are required for changes.
- Comprehensive unit and integration tests are not yet implemented.
- Authentication and authorization mechanisms are not in place.
- No optimistic locking or concurrency handling is implemented for coupon updates.
- Logging, error handling and monitoring are minimal and need enhancement for production readiness.
---
## Cases solved

### BuyXGetY Coupon
- If the cart contains enough quantity of the required product/s (X), the coupon is applicable. Discount is calculated based on the quantity of free products (Y) * product price.
- If the cart does not contain enough quantity of the required product/s (X), the coupon is not applicable.
- If the cart contains multiple sets of the required product/s (X), the coupon is applicable for each set. Discount is calculated based on the total quantity of free products (Y) * product price.
- If the cart contains more quantity of the required product/s (X) than needed for the coupon, the extra products are ignored.
- If the cart contains less quantity of the free product/s (Y) than specified in the coupon, the discount is calculated based on the available quantity.

#### Assumptions:
- A single coupon may contain multiple sets of BuyXGetY offers. New coupon does not need to be introduced for each set.
- The free products (Y) are not of the same type as the required products (X).

### Cart-wise Percentage Discount Coupon

- The coupon is applicable only if the cart subtotal meets or exceeds the configured threshold amount.
- The discount amount is calculated as a percentage of the cart subtotal.
- If a maximum discount amount is configured, the final discount is capped at that value.
- The coupon is not applicable if the cart subtotal is below the threshold amount.

#### Assumptions:
- Percentage discounts are applied on the cart subtotal before taxes and shipping.
- Discount amount does not exceed the cart subtotal.
- Added maxApplications field to define per-user redemption limits even though it does not belong to the coupon entity. Ideally, this should be enforced via a user-coupon usage table once users are introduced. Kept it here as a documented constraint to show intent without over-engineering the assignment.

### Cart-wise Fixed Amount Discount Coupon
- The coupon is applicable only if the cart subtotal meets or exceeds the configured threshold amount.
- A fixed discount amount is applied to the cart subtotal.
- The coupon is not applicable if the cart subtotal is below the threshold amount.

#### Assumptions:
- Fixed amount discounts are applied before taxes and shipping.
- Discount amount does not exceed the cart subtotal.
- Added maxApplications field to define per-user redemption limits even though it does not belong to the coupon entity. Ideally, this should be enforced via a user-coupon usage table once users are introduced. Kept it here as a documented constraint to show intent without over-engineering the assignment

### Applicable Coupons Flow

- The /applicable-coupons endpoint follows the below flow:
  - Fetch Candidate Coupons 
  - Retrieve all active coupons within the validity date range. 
  - Coupons outside the date range or inactive coupons are filtered out at the database level. 
- Applicability Check 
  - Each candidate coupon is evaluated against the cart. 
  - Coupon-specific rules determine whether the coupon is applicable. 
- Discount Calculation 
  - For applicable coupons, the discount amount is calculated based on coupon type. 
  - CouponType-specific calculation logic is encapsulated in dedicated strategy classes.
- Response Mapping 
  - Each applicable coupon is mapped to a response DTO containing:
    - Coupon ID 
    - Coupon type 
    - Discount Amount
- Calculated discount amount 
  - The response is sorted in descending order of discount amount.
