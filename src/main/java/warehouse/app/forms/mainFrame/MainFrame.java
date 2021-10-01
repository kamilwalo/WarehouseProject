package warehouse.app.forms.mainFrame;

import warehouse.app.forms.FrameInheritance;
import warehouse.app.forms.addCustomer.AddCustomer;
import warehouse.app.forms.addOrder.AddOrder;
import warehouse.app.forms.addProduct.addProductFrame;
import warehouse.app.forms.deleteData.deleteWindow;
import warehouse.app.forms.showOrdersWindow.ShowOrders;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

//TODO make it pretty


public class MainFrame extends FrameInheritance {
    private JButton showEmployeeButton;
    private JTable resultJTable;
    private JButton showCustomersButton;
    private JPanel mainJPanel;
    private JCheckBox showPhoneNumberCheckBox;
    private JButton addCustomerButton;
    private JButton deleteCustomerEmployeeButton;
    private JButton showOrdersButton;
    private JButton addOrderButton;
    private JButton addProductButton;
    private JButton exitButton;

    private List<Object> listToTable = new ArrayList<>();


    public MainFrame(){
        resultJTable.setVisible(false);

        setParameterToWindow();


        showOrdersButton.addActionListener(e -> {
            new ShowOrders();
            dispose();
        });

        addOrderButton.addActionListener(e -> {
            new AddOrder();
            dispose();
        });

        addProductButton.addActionListener(e -> {
            new addProductFrame();
            dispose();
        });

        exitButton.addActionListener(e -> System.exit(0));
    }

    private void setParameterToWindow() {
        setContentPane(mainJPanel);
        setVisible(true);
        showPhoneNumberCheckBox.setVisible(false);

        showEmployeeButton.addActionListener(e -> {
            resultJTable.setVisible(true);
            showPhoneNumberCheckBox.setVisible(false);
            drawListOfEmployees();
        });

        showCustomersButton.addActionListener(e -> {
            resultJTable.setVisible(true);
            drawListOfCustomers();
            showPhoneNumberCheckBox.setVisible(true);
        });

        showPhoneNumberCheckBox.addActionListener(e -> drawListOfCustomers());

        addCustomerButton.addActionListener(e -> {
            new AddCustomer();
            dispose();
        });

        deleteCustomerEmployeeButton.addActionListener(e -> {
            new deleteWindow();
            dispose();
        });
    }

    private void drawListOfCustomers() {
        if(showPhoneNumberCheckBox.isSelected()){
            listToTable = getList("Select c.customerId,c.name,c.lastName, cd.phoneNumber " +
                    "from Customers c join c.customerDetails cd");
            drawJTable(new String[]{"id", "name", "last_name", "position", "phone"});
        }else {
            listToTable = getList("Select c.customerId,c.name,c.lastName from Customers c");
            drawJTable(new String[]{"id", "name", "last_name", "position"});
        }
    }

    private void drawListOfEmployees() {
        listToTable = getList("Select e.employeeId,e.nameOfEmploee,e.lastNameOfEmployee,e.position from Employee e");
        drawJTable(new String[]{"id", "name", "last_name", "position"});
    }

    private void drawJTable(String[] columnNames) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(columnNames,0);
        resultJTable.setModel(defaultTableModel);

        //adding rows
        for (Object objects : listToTable) {
            defaultTableModel.addRow((Object[]) objects);
        }
    }

    private List<Object> getList(String definedQuery) {
        startSession();
        List<Object> objects = session.createQuery(definedQuery).getResultList();
        stopSession();
        //123

        return objects;
    }
}