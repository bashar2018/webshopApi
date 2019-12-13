package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@IdClass(ShoppingCart.class)
@Table(name = "shoppingcart")
public class ShoppingCart implements Serializable {

    @Id
    @Column(name = "userid")
    @JsonProperty("userid")
    @JsonView(View.Public.class)
    private Long userid;

    @Id
    @Column(name = "productid")
    @JsonProperty("productid")
    @JsonView(View.Public.class)
    private Long productid;

    @Column(name = "quantity")
    @JsonProperty("quantity")
    @JsonView(View.Public.class)
    private int quantity;

    public ShoppingCart(Long userid, Long productid, int quantity) {
        this.userid = userid;
        this.productid = productid;
        this.quantity = quantity;
    }

    public ShoppingCart() {
    }

    public Long getUserId() {
        return userid;
    }

    public void setUserId(Long userid) {
        this.userid = userid;
    }

    public Long getProductId() {
        return productid;
    }

    public void setProductId(Long productId) {
        this.productid = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
