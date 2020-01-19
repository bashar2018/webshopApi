package nl.hsleiden.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import nl.hsleiden.auth.Role;
import nl.hsleiden.model.ShoppingCart;
import nl.hsleiden.repository.ShoppingCartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
public class ShoppingCartController {
    private final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartController.class);

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @GetMapping(value = "/api/shoppingcarts")
    @JsonView(View.Public.class)
    @PreAuthorize("hasAuthority('" + Role.ADMIN + "')")
    public List<ShoppingCart> getShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    @GetMapping("/api/shoppingcarts/{userid}")
    @JsonView(View.Public.class)
    public List<ShoppingCart> getShoppingCart(@PathVariable Long userid) {
        LOGGER.info("Fetching users with id" + userid);
        return shoppingCartRepository.findByUserid(userid);
    }

    @GetMapping("/api/shoppingcarts/{userid}/{productid}")
    @JsonView(View.Public.class)
    public ShoppingCart getSpecifiedShoppingCart(@PathVariable Long userid, @PathVariable Long productid) {
        return shoppingCartRepository.findShoppingCartByUseridAndProductid(userid, productid);
    }
////
    @PostMapping("/api/shoppingcarts")
    @JsonView(View.Public.class)
    @PreAuthorize("hasAuthority('" + Role.USER + "')")
    public ShoppingCart createShoppingCart(@Valid @RequestBody ShoppingCart shoppingCart) {
        System.out.println("test");
       return shoppingCartRepository.save(shoppingCart);
    }
//
    @DeleteMapping("/api/shoppingcarts/{userid}")
    public void deleteShoppingCart(@PathVariable Long userid) {
        List<ShoppingCart> shoppingCart = shoppingCartRepository.findByUserid(userid);
        Iterator iterator = shoppingCart.iterator();
        while (iterator.hasNext()){
            shoppingCartRepository.delete((ShoppingCart)iterator.next());
        }
    }
//
    @DeleteMapping("/api/shoppingcarts/{userid}/{productid}")
    public void deleteShoppingCartItem(@PathVariable Long userid, @PathVariable Long productid) {
        ShoppingCart shoppingCartItem = shoppingCartRepository.findShoppingCartByUseridAndProductid(userid, productid);
        shoppingCartRepository.delete(shoppingCartItem);
    }
}

