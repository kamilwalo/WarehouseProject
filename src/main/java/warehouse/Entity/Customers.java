package warehouse.Entity;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Integer customerId;

    @Column(name = "first_name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_customer_detail")
    private CustomerDetails customerDetails;

    public Customers() {
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Customers(String name, String last_name) {
        this.name = name;
        this.lastName = last_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", last_name='" + lastName + '\'' +
                ", customerDetails=" + customerDetails +
                '}';
    }
}