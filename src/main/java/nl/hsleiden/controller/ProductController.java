package nl.hsleiden.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jdk.nashorn.internal.runtime.options.LoggingOption;
import nl.hsleiden.View;
import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Product;
import nl.hsleiden.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/api/products")
    @PreAuthorize("hasAuthority('" + Role.USER + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public Collection<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/api/products/{productId}")
    @PreAuthorize("hasAuthority('" + Role.USER + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public Optional<Product> getProduct(@PathVariable Long productId) {
        LOGGER.info("Fetching product with id" + productId);
        return productRepository.findById(productId);
    }
//    @GetMapping("/api/products/beschrijving/{productId}")
//    @PreAuthorize("hasAuthority('" + Role.USER + "') or hasAuthority('" + Role.ADMIN + "')")
//    @JsonView(View.Public.class)
//    public Optional<Product> getProductBeschrijving(@PathVariable Long productId) {
//        LOGGER.info("Fetching product with id" + productId);
//        return productRepository.findById(productId);
//    }

    @PostMapping("/api/products")
    @PreAuthorize(" hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public Product createProduct(@Valid @RequestBody Product product) {
        LOGGER.info("Creating product.");


        return productRepository.save(product);
    }

    @PutMapping("/api/products/{productid}")
    @PreAuthorize(" hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public Product updateProduct(@PathVariable Long productid, @Valid @RequestBody Product updatedProduct) {
        LOGGER.info("updating product: "+ productid);
        return productRepository.findById(productid).map(product -> {
            product.setProductName(updatedProduct.getProductName());
            product.setPrice(updatedProduct.getPrice());
            product.setImagePath(updatedProduct.getImagePath());

            return productRepository.save(product);
        }).orElseThrow(() -> new ResourceNotFoundException("product not found with id " + productid));
    }

    @DeleteMapping("/api/products/{productId}")
//    @PreAuthorize("hasAuthority('" + Role.ADMIN + "')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        LOGGER.info("Deleting product with id: " + productId);
        return productRepository.findById(productId).map(product-> {
            productRepository.delete(product);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("product not found with id" + productId));
    }
}
