package warehouse.app.forms.javaSwing.addProduct;

import warehouse.Entity.Product;
import warehouse.app.forms.javaSwing.FrameInheritance;
import warehouse.app.forms.javaSwing.mainFrame.MainFrame;

import javax.swing.*;
import java.awt.*;

public class addProductFrame extends FrameInheritance {
    private JTextField priceJField;
    private JFormattedTextField nameOfProductJField;
    private JButton addProductAndBackButton;
    private JButton backButton;
    private JButton addProductButton;
    private JPanel addProductPanel;
    private JLabel error;
    private JLabel titleJLabel;

    public addProductFrame(){
        setContentPane(addProductPanel);
        setVisible(true);
        error.setForeground(Color.red);
        titleJLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC,40));


        addProductButton.addActionListener(e -> addProduct());

        addProductAndBackButton.addActionListener(e -> {
            if(addProduct()){
                dispose();
                new MainFrame();
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new MainFrame();
        });
    }

    private boolean addProduct() {
        if(nameOfProductJField.getText().matches("[a-zA-Z]+") && priceJField.getText().matches("[0-9]+")
                && !nameOfProductJField.getText().equals("") && !priceJField.getText().equals("")) {
            Product product = new Product(nameOfProductJField.getText(), Integer.parseInt(priceJField.getText()));
            startSession();
            session.save(product);
            stopSession();
            nameOfProductJField.setText("");
            priceJField.setText("");
            return true;
        }else {
            error.setText("Check if values are correctly");
            return false;
        }
    }
}
