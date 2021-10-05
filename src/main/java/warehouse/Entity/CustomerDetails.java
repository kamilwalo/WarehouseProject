package warehouse.Entity;

import javax.persistence.*;

@Entity
@Table(name = "customer_detail")
public class CustomerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer_detail")
    private Integer customerDetailId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "customerDetails", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Customers customers;

    @Column(name = "street", length = 100)
    private String street;

    @Column(name = "postal_code", length = 6)
    private String postalCode;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name ="house_number",length = 10)
    private String houseNumber;



    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public CustomerDetails() {
    }

    public CustomerDetails( String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getCustomerDetailId() {
        return customerDetailId;
    }

    public void setCustomerDetailId(Integer customerDetailId) {
        this.customerDetailId = customerDetailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "CustomerDetails{" +
                "customerDetailId=" + customerDetailId +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
