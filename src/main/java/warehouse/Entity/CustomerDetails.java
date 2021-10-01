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
