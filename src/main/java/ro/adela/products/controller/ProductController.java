package ro.adela.products.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.adela.products.auth.JwtUtil;
import ro.adela.products.auth.model.User;
import ro.adela.products.auth.model.request.LoginReq;
import ro.adela.products.auth.model.response.ErrorRes;
import ro.adela.products.auth.model.response.LoginRes;
import ro.adela.products.dto.product.Product;
import ro.adela.products.dto.repository.ProductJdbcRepository;
import ro.adela.products.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductJdbcRepository repository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody LoginReq loginReq)  {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            String email = authentication.getName();
            User user = new User(email,"");
            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(email,token);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

     @GetMapping(path = "/retrieveAllProducts/{page}/{totalByPage}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> retrieveAllProducts(@PathVariable Integer page, @PathVariable Integer totalByPage) {
         List<Product> products = repository.findAll(page, totalByPage);
         logger.info("All PRODUCTS by page -> {}", products);
         return Optional.ofNullable(products)
                 .map(ResponseEntity::ok)
                 .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
     }

    @GetMapping(path = "/retrieveProductById/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> retrieveProductById(@PathVariable Long productId) {
        Product product = repository.findById(productId);
        logger.info("Product id %d -> {}", productId, product);
        return Optional.ofNullable(product)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Not found product with id = " + productId));
    }

    @PostMapping(path = "/insertProduct", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> insertProduct(@RequestBody Product product) {
        Integer rowNum =  repository.insert(product);
        logger.info("Inserting -> {}", rowNum);
        return new ResponseEntity<>(rowNum, HttpStatus.CREATED);
    }

    @PutMapping(path = "/updateProduct", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updateProduct(@RequestBody Product product) {
        Integer rowNum =  repository.update(product);
        logger.info("Update %d -> {}", product.getId(), rowNum);
        return new ResponseEntity<>(rowNum, HttpStatus.OK);
    }

    @DeleteMapping (path = "/deleteProduct/{productId}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long productId) {
        repository.deleteById(productId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
