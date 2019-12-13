package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productid", columnDefinition = "serial")
    @JsonProperty("productid")
    @JsonView(View.Public.class)
    private Long productId;

    @Length(max = 255)
    @Column(name = "productname")
    @JsonProperty("productname")
    @JsonView(View.Public.class)
    private String productName;

    @Column(name = "price")
    @JsonProperty("price")
    @JsonView(View.Public.class)
    private double price;

    @Length(max = 255)
    @Column(name = "imagepath")
    @JsonProperty("imagepath")
    @JsonView(View.Public.class)
    private String imagePath;

    public Product() {
    }

    public Product(long ProductId, String ProductName, double Price, String ImagePath){
        this.productId = ProductId;
        this.productName = ProductName;
        this.price = Price;
        this.imagePath = ImagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

