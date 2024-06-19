package ro.adela.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ro.adela.products.dto.product.Product;
import ro.adela.products.dto.repository.ProductJdbcRepository;

import java.sql.Date;

@SpringBootApplication
public class ProductsApplication {

	@Autowired
	ProductJdbcRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
	}

}
