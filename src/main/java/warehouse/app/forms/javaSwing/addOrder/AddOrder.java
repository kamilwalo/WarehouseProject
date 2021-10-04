package warehouse.app.forms.javaSwing.addOrder;

import warehouse.Entity.*;
import warehouse.app.forms.javaSwing.FrameInheritance;
import warehouse.app.forms.javaSwing.addCustomer.AddCustomer;
import warehouse.app.forms.javaSwing.mainFrame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddOrder extends FrameInheritance {
    private JPanel addJPanel;
    private JTextField quantityOfProduct;
    private JButton addNewProductButton;
    private JButton finishButton;
    private JButton backAndDonTButton;
    private JButton addCustomerButton;
    private JComboBox chooseProductComboBox;
    private JComboBox chooseCustomerComboBox;
    private JComboBox chooseEmployeeComboBox;
    private JLabel sumOfOrderJLabel;
    private JLabel sumJLabel;
    private JLabel choseCustomerText;
    private JLabel choseEmployeeText;
    private JLabel titleJLabel;
    private JButton continueButton;
    private JLabel infoAboutOrderJLabel;
    private JLabel errorJLabel;



    /*
    * Here are objects contains chosen options by user
    * */

    private Employee employee;
    private Product product;
    private Customers customer;

    /*
    * Here is int which contain whole price of order
    * */

    private int sumOfOrderPrice = 0;

    /*
    * Here are list of product chosen by user and quantity of this product
    * */

    private final List<Product> productsChosenByUser = new ArrayList<>();
    private final List<Integer> quantity = new ArrayList<>();

    /*
    * Here are lists of product, customer and employee
    * */

    private List<Object[]> resultListOfProduct ;
    private List<Object[]> resultListOfCustomer;
    private List<Object[]> resultListOfEmployee;


    public AddOrder() {
        setContentPane(addJPanel);

        insertData();

        finishButton.setVisible(false);
        sumOfOrderJLabel.setVisible(false);

        setVisible(true);

        titleJLabel.setFont(new Font("Times New Roman",Font.BOLD,40));
        addNewProductButton.setVisible(false);
        errorJLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
        errorJLabel.setForeground(Color.red);

        addCustomerButton.addActionListener(e -> {
            new AddCustomer();
            dispose();
        });

        chooseProductComboBox.addActionListener(e -> {
            if(chooseProductComboBox.getSelectedIndex() - 1!=-1){
                errorJLabel.setText("");
                startSession();
                product = session.get(Product.class, (Integer) resultListOfProduct.get(chooseProductComboBox.getSelectedIndex() - 1)[0]);
                stopSession();
                setSumInJLabel();
            }else product=null;
        });

        chooseCustomerComboBox.addActionListener(e -> {
            if(chooseCustomerComboBox.getSelectedIndex() - 1!=-1){
                errorJLabel.setText("");
                startSession();
                customer = session.get(Customers.class, (Integer) resultListOfCustomer.get(chooseCustomerComboBox.getSelectedIndex() - 1)[0]);
                stopSession();
            }else customer=null;
        });

        chooseEmployeeComboBox.addActionListener(e -> {
            if(chooseEmployeeComboBox.getSelectedIndex() - 1!=-1){
                errorJLabel.setText("");
                startSession();
                employee = session.get(Employee.class, (Integer) resultListOfEmployee.get(chooseEmployeeComboBox.getSelectedIndex() - 1)[0]);
                stopSession();
                System.out.println(employee.toString());
            }else employee=null;
        });

        quantityOfProduct.addActionListener(e -> setSumInJLabel());

        addNewProductButton.addActionListener(e -> {
            if(employee!=null && product != null && customer != null && !quantityOfProduct.getText().equals("") &&
                    chooseEmployeeComboBox.getSelectedIndex()!=0 && chooseCustomerComboBox.getSelectedIndex()!=0 && chooseProductComboBox.getSelectedIndex()!=0 &&
                    quantityOfProduct.getText().matches("[0-9]") &&  !quantityOfProduct.getText().equals("0")){
                errorJLabel.setText("");
                productsChosenByUser.add(product);
                quantity.add(Integer.valueOf(quantityOfProduct.getText()));

                sumTheOrderPrice(Integer.valueOf(quantityOfProduct.getText()));
                sumOfOrderJLabel.setText("Value of whole order = "+sumOfOrderPrice);

                quantityOfProduct.setText("");
                chooseProductComboBox.setSelectedIndex(0);
            }else {
                errorJLabel.setText("something is wrong");
            }
        });

        continueButton.addActionListener(e -> {
            if(employee!=null && product != null && customer != null && !quantityOfProduct.getText().equals("") &&
                    chooseEmployeeComboBox.getSelectedIndex()!=0 && chooseCustomerComboBox.getSelectedIndex()!=0 && chooseProductComboBox.getSelectedIndex()!=0 &&
                    quantityOfProduct.getText().matches("[0-9]") &&  !quantityOfProduct.getText().equals("0")){
                sumOfOrderJLabel.setVisible(true);
                finishButton.setVisible(true);
                addNewProductButton.setVisible(true);
                continueButton.setVisible(false);
                chooseEmployeeComboBox.setVisible(false);
                chooseCustomerComboBox.setVisible(false);
                addCustomerButton.setVisible(false);
                choseCustomerText.setVisible(false);
                choseEmployeeText.setVisible(false);
                errorJLabel.setText("");

                infoAboutOrderJLabel.setText(
                        "<html><center>" +
                                "Employee "+employee.getNameOfEmploee()+" "+employee.getLastNameOfEmployee()+" id="+employee.getEmployeeId()+"<br/>"+
                                "You are adding product to order for " + customer.getName() +" "+ customer.getLastName() +" IdCustomer="+customer.getCustomerId()+"" +
                                "</center></html>");
                productsChosenByUser.add(product);
                quantity.add(Integer.valueOf(quantityOfProduct.getText()));

                sumTheOrderPrice(Integer.valueOf(quantityOfProduct.getText()));
                sumOfOrderJLabel.setText("Value of whole order = "+sumOfOrderPrice);

                quantityOfProduct.setText("");
                chooseProductComboBox.setSelectedIndex(0);

            }else {
                errorJLabel.setText("something is wrong");
            }
        });

        backAndDonTButton.addActionListener(e -> {
            new MainFrame();
            dispose();
        });

        finishButton.addActionListener(e -> {
            Order newOrder = new Order(employee,customer);
            List<OrderDetail> newOrderDetail = new ArrayList<>();

            for (int i = 0; i < productsChosenByUser.size(); i++) {
                newOrderDetail.add(new OrderDetail(productsChosenByUser.get(i),quantity.get(i)));
                newOrderDetail.get(i).setOrder(newOrder);
            }
            newOrder.setOrderDetails(newOrderDetail);

            startSession();

            session.save(newOrder);

            stopSession();
            new MainFrame();
            dispose();
        });
    }

    private void sumTheOrderPrice(Integer quantity) {
        sumOfOrderPrice+=product.getPrice()*quantity;
        System.out.println(sumOfOrderPrice);
    }

    private void setSumInJLabel() {
        if(product!=null && !quantityOfProduct.getText().equals("") &&
                quantityOfProduct.getText().matches("[0-9]") &&  !quantityOfProduct.getText().equals("0")){
            sumJLabel.setText(String.valueOf(product.getPrice()*Integer.parseInt(quantityOfProduct.getText())));
        }else {
            errorJLabel.setText("something is wrong");
        }
    }

    private void insertData() {
        insertDataToEmployeeComboBox();
        insertDataToCustomerComboBox();
        insertDataProductToComboBox();
    }

    private void insertDataProductToComboBox() {
        startSession();
        resultListOfProduct = session.createQuery("select p.productId, p.productName, p.price from Product p").getResultList();
        stopSession();

        chooseProductComboBox.addItem("---choose Product---");
        for (Object[] objects : resultListOfProduct) {
            chooseProductComboBox.addItem("Id:"+objects[0]+"| "+objects[1]+" price:"+objects[2]);
        }
    }

    private void insertDataToCustomerComboBox() {
        startSession();
        resultListOfCustomer = session.createQuery("select c.customerId,c.name, c.lastName from Customers c").getResultList();
        stopSession();

        chooseCustomerComboBox.addItem("---choose Customer---");
        for (Object[] objects : resultListOfCustomer) {
            chooseCustomerComboBox.addItem("Id:"+objects[0]+"| "+objects[1]+" "+objects[2]);
        }
    }

    private void insertDataToEmployeeComboBox() {
        startSession();
        resultListOfEmployee = session.createQuery("select e.employeeId,e.nameOfEmploee, e.lastNameOfEmployee from Employee e").getResultList();
        stopSession();

        chooseEmployeeComboBox.addItem("---choose Employee---");
        for (Object[] objects : resultListOfEmployee) {
            chooseEmployeeComboBox.addItem("Id:"+objects[0]+"| "+objects[1]+" "+objects[2]);
        }
    }

}
