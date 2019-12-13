package nl.hsleiden.controller;

import com.fasterxml.jackson.annotation.JsonView;
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

    @PostMapping("/api/products")
    @PreAuthorize(" hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public Product createProduct(@Valid @RequestBody Product product) {
        LOGGER.info("Creating product.");


        return productRepository.save(product);
    }

//    @PutMapping("/api/products/{productid}")
//    @PreAuthorize(" hasAuthority('" + Role.ADMIN + "')")
//    @JsonView(View.Public.class)
//    public Instructor updateInstructor(@PathVariable Long instructorId, @Valid @RequestBody Instructor updatedInstructor) {
//        LOGGER.info("Updating instructor with id: " + instructorId);
//        return instructorRepository.findById(instructorId).map(instructor -> {
//            instructor.setFirstName(updatedInstructor.getFirstName());
//            instructor.setInfix(updatedInstructor.getInfix());
//            instructor.setLastName(updatedInstructor.getLastName());
//            instructor.setEmail(updatedInstructor.getEmail());
//            instructor.setPhoneNumber(updatedInstructor.getPhoneNumber());
//            instructor.setNote(updatedInstructor.getNote());
//
//            return instructorRepository.save(instructor);
//        }).orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id " + instructorId));
//    }

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
