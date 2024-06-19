package ro.adela.products;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ro.adela.products.dto.product.Product;
import ro.adela.products.dto.repository.ProductJdbcRepository;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")
class ProductsApplicationTests {

	@Autowired
    private Flyway flyway;

    @Autowired
    ProductJdbcRepository productJdbcRepository;


    @Test
    public void skipAutomaticAndTriggerManualFlywayMigration() {
        flyway.migrate();
    }

    @Test
    public void shouldGetListOfProducts(){
        List<Product> products = productJdbcRepository.findAll(1, 5);
        Assertions.assertEquals(5, products.size());
    }
}
