package warehouse.app.forms.addCustomer;


import warehouse.Entity.CustomerDetails;
import warehouse.Entity.Customers;
import warehouse.app.forms.FrameInheritance;
import warehouse.app.forms.mainFrame.MainFrame;

import javax.swing.*;

public class AddCustomer extends FrameInheritance {
    private JTextField firstNameJField;
    private JTextField lastNameJField;
    private JTextField phoneNumberJField;
    private JButton addUserAndBackButton;
    private JButton addUserButton;
    private JButton backButton;
    private JPanel mainPanel;
    private JLabel errorJLabel;


    public AddCustomer(){

        setContentPane(mainPanel);

        setVisible(true);


        addUserButton.addActionListener(e -> addCustomerToData());

        addUserAndBackButton.addActionListener(e -> {
            if(addCustomerToData()) dispose();
        });

        backButton.addActionListener(e -> {
            new MainFrame();
            dispose();
        });
    }

    private boolean addCustomerToData() {
        if(firstNameJField.getText().matches("[a-zA-Z]+")
        && lastNameJField.getText().matches("[a-zA-Z]+")
        && phoneNumberJField.getText().matches("[0-9]+")) {
            errorJLabel.setText(null);
            Customers customer = new Customers(firstNameJField.getText(), lastNameJField.getText());
            CustomerDetails customerDetails = new CustomerDetails(phoneNumberJField.getText());
            customer.setCustomerDetails(customerDetails);

            startSession();

            session.persist(customer);

            stopSession();
            return true;

        }else {
            errorJLabel.setText("<html><div style='text-align: center'><div style=color:red>Something went wrong.</div><br/>" +
                    " Check if first and last name has only letter and phone has only numbers</div></html>");
            return false;
        }
    }
}
