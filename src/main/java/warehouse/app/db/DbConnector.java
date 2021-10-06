package warehouse.app.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import warehouse.Entity.*;


public class DbConnector {

    private static  SessionFactory sf;

    public  DbConnector(){
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Customers.class).addAnnotatedClass(CustomerDetails.class)
                .addAnnotatedClass(Employee.class).addAnnotatedClass(Product.class)
                .addAnnotatedClass(Order.class).addAnnotatedClass(OrderDetail.class)
                .addAnnotatedClass(ProductDetail.class).addAnnotatedClass(ProductCategory.class)
                .addAnnotatedClass(EmployeeAccount.class);
        sf = configuration.buildSessionFactory();
    }

    public SessionFactory getSf() {
        return sf;
    }
}
