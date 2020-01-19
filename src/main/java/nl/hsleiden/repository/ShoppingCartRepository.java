package nl.hsleiden.repository;

import nl.hsleiden.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

     List<ShoppingCart> findByUserid(Long userid);
     ShoppingCart findShoppingCartByUseridAndProductid(Long userid, Long productid);
//     void delete(ShoppingCart shoppingCart);

    @Override
     List<ShoppingCart> findAll();
}
