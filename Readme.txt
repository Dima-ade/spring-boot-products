# spring-boot-products

The application is a web application developed in Java.
It uses SpringBoot v3+, JDK 17, Maven v3.6+, Flyway, Logback, H2, Lombok.

In order to execute the flyway scripts
    V1__create_product_table.sql
    V2__insert_product_table.sql
the following command should be run: mvn flyway:migrate

A H2 databases is used in the memory to store the products.

The command to build the application: mvn clean install.

The available endpoints are:
   POST: http://localhost:8080/products/login
   for authentication and authorization, which sends email and password and generates a JWT token.
   {
   	"email": "ex@example.com",
   	"password": "123456"
   }

   The generated token is used as Bearer token in the header of the following endpoints:

1. GET: http://localhost:8080/products/retrieveAllProducts/{pageId}/{numberOfElements}
   Pagination mechanism is implemented here

2. GET: http://localhost:8080/products/retrieveProductById/{productId}

3. DELETE: http://localhost:8080/products/deleteProduct/{productId}

4. POST: http://localhost:8080/products/insertProduct

5. UPDATE: http://localhost:8080/products/updateProduct

The log is configured in logback.xml and level INFO is utilized in main class.

The error handling is configured in ControllerExceptionHandler and used on the endpoints controller.

The unit tests were created in the class ProductsApplicationTests.
