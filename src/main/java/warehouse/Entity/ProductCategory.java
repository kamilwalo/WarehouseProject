package warehouse.Entity;

import javax.persistence.*;

@Table(name = "product_category")
@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product_category", nullable = false)
    private Integer id;

    @Column(name = "product_category", length = 30)
    private String productCategory;

    public ProductCategory() {
    }



    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", productCategory='" + productCategory + '\'' +
                '}';
    }
}