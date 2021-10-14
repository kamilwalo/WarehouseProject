package warehouse.app.javafx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import warehouse.Entity.Customers;
import warehouse.app.db.DbConnector;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddOrderController extends Controller implements Initializable {


    @FXML private TextField searchForField;
    @FXML private ComboBox customerComboBox;
    @FXML private Button searchButton;
    @FXML private CheckBox searchByPhoneNumber;

    private List<Customers> customers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Runnable connectToDb = ()-> Platform.runLater(()->{
            dbConnector = new DbConnector();
            sf = dbConnector.getSf();
            setDataToTable("");
        });
        Thread thread = new Thread(connectToDb);
        thread.setDaemon(true);
        thread.start();
        setInfoAboutAccount();
    }

    public void clickedButtonSearch(ActionEvent event){

        if(searchForField.getText().equals("")) setDataToTable("");
        else setDataToTable(searchForField.getText());
    }

    private void setDataToTable(String textFromSearchField) {

        customers = new ArrayList();

        startSession();
        if(!searchByPhoneNumber.isSelected()){
            if (textFromSearchField.equals(""))
                customers.addAll(session.createQuery("from Customers ").setMaxResults(10).getResultList());
            else if (searchForField.getText().matches("[0-9]+")) {
                customers.addAll(session.createQuery("from Customers where id=:text").setMaxResults(10)
                        .setParameter("text", Integer.parseInt(textFromSearchField))
                        .getResultList());
            } else
                customers.addAll(session.createQuery("from Customers where name=:text or lastName=:text " +
                                "or customerDetails.city=:text or customerDetails.phoneNumber=:text or customerDetails.postalCode=:text or " +
                                "customerDetails.street=:text").setMaxResults(10)
                        .setParameter("text", textFromSearchField)
                        .getResultList());
        }else {
            if (textFromSearchField.equals(""))
                customers.addAll(session.createQuery("from Customers ").setMaxResults(10).getResultList());
            else
                customers.addAll(session.createQuery("from Customers where customerDetails.phoneNumber='%"+textFromSearchField+"%'").setMaxResults(10).getResultList());
        }
        stopSession();

        ObservableList<String> listOfCustomers = FXCollections.observableArrayList();
        for (Customers customer : customers) {
            listOfCustomers.add("Id: "+customer.getCustomerId()+" "+customer.getName()+" "+customer.getLastName() + " "+ customer.getCustomerDetails().getPhoneNumber());
        }

        customerComboBox.setItems(listOfCustomers);
    }
}
