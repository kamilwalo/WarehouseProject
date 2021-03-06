package warehouse.Entity;

import javax.persistence.*;

@Table(name = "product_detail")
@Entity
public class ProductDetail {

    @OneToOne(optional = false)
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @Column(name = "quantity_in_stock")
    private Integer quantityInStock;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product_detail", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_product_category")
    private ProductCategory productCategory;

    public ProductDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory manyToOne) {
        this.productCategory = manyToOne;
    }

    public void setPrice(Integer price){
        product.setPrice(price);
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "product=" + product +
                ", quantityInStock=" + quantityInStock +
                ", id=" + id +
                ", productCategory=" + productCategory +
                '}';
    }
}