package warehouse.app.javafx;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import warehouse.Entity.CustomerDetails;
import warehouse.Entity.Customers;
import warehouse.app.db.DbConnector;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddCustomerController extends Controller implements Initializable {

    @FXML private TextField nameField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField lastNameField;
    @FXML private TextField streetField;
    @FXML private TextField cityField;
    @FXML private TextField postalCodeField;
    @FXML private TextField houseNumberField;

    @FXML private TableView<Object[]> customersTable;
    @FXML private TableColumn<Customers,Integer> idColumn;
    @FXML private TableColumn<Customers,String> nameColumn;
    @FXML private TableColumn<Customers,String> lastNameColumn;
    @FXML private TableColumn<Customers,String> phoneColumn;
    @FXML private TableColumn<Customers,String> streetColumn;
    @FXML private TableColumn<Customers,String> houseColumn;
    @FXML private TableColumn<Customers,String> postalCodeColumn;
    @FXML private TableColumn<Customers,String> cityColumn;

    @FXML private TextField searchField;
    @FXML private CheckBox phoneCheckBox;

    @FXML private Label nameLabel;
    @FXML private Label phoneLabel;
    @FXML private Label lastNameLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInfoAboutAccount();
        setIconToButtonMenu();
        nameField.setText("");
        lastNameField.setText("");
        phoneNumberField.setText("");

        Runnable connectToDb = ()-> Platform.runLater(()->{
            dbConnector = new DbConnector();
            sf = dbConnector.getSf();
            setDataToTable(searchField.getText());
        });
        Thread thread = new Thread(connectToDb);
        thread.setDaemon(true);
        thread.start();

        final Pattern pattern = Pattern.compile("\\d{0,5}");
        TextFormatter<?> formatter = new TextFormatter<>(change -> {
            if (pattern.matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        });
        postalCodeField.setTextFormatter(formatter);
    }

    private void setDataToTable(String textFromSearchField) {
        ObservableList<Object[]> customers = FXCollections.observableArrayList();

        startSession();
        if(!phoneCheckBox.isSelected()){
            if (textFromSearchField.equals(""))
                customers.addAll(session.createQuery("from Customers ").getResultList());
            else if (searchField.getText().matches("[0-9]+")) {
                customers.addAll(session.createQuery("from Customers where id=:text")
                        .setParameter("text", Integer.parseInt(textFromSearchField))
                        .getResultList());
            } else
                customers.addAll(session.createQuery("from Customers where name=:text or lastName=:text " +
                                "or customerDetails.city=:text or customerDetails.phoneNumber=:text or customerDetails.postalCode=:text or " +
                                "customerDetails.street=:text")
                        .setParameter("text", textFromSearchField)
                        .getResultList());
        }else {
            if (textFromSearchField.equals(""))
                customers.addAll(session.createQuery("from Customers ").getResultList());
            else
                customers.addAll(session.createQuery("from Customers where customerDetails.phoneNumber=:text")
                        .setParameter("text",textFromSearchField).getResultList());
        }
        stopSession();

        idColumn.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getCustomerId()));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        lastNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLastName()));
        phoneColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerDetails().getPhoneNumber()));
        streetColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerDetails().getStreet()));
        houseColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerDetails().getHouseNumber()));
        postalCodeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerDetails().getPostalCode()));
        cityColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerDetails().getCity()));

        customersTable.setItems(customers);
    }

    public void createCustomer(){
        if(checkTextField()){
            Customers newCustomer = new Customers();
            newCustomer.setName(nameField.getText());
            newCustomer.setLastName(lastNameField.getText());

            CustomerDetails newCustomerDetail = new CustomerDetails();
            newCustomer.setCustomerDetails(newCustomerDetail);

            newCustomerDetail.setPhoneNumber(phoneNumberField.getText());
            newCustomerDetail.setStreet(streetField.getText());
            newCustomerDetail.setPostalCode(postalCodeField.getText());
            newCustomerDetail.setCity(cityField.getText());
            newCustomerDetail.setStreet(streetField.getText());
            newCustomerDetail.setHouseNumber(houseNumberField.getText());

            startSession();
            session.persist(newCustomer);
            stopSession();

            nameField.setText("");
            lastNameField.setText("");
            phoneNumberField.setText("");
            streetField.setText("");
            houseNumberField.setText("");
            postalCodeField.setText("");
            cityField   .setText("");
        }
    }

    private boolean checkTextField() {

        if(nameField.getText().equals("")) {
            nameLabel.setTextFill(Color.color(1, 0, 0));
            nameLabel.setStyle("-fx-font-weight: bold");
        }else {
            nameLabel.setTextFill(Color.color(0, 0, 0));
            nameLabel.setStyle("-fx-font-weight: regular");
        }
        if(lastNameField.getText().equals("")) {
            lastNameLabel.setTextFill(Color.color(1, 0, 0));
            lastNameLabel.setStyle("-fx-font-weight: bold");
        }else {
            lastNameLabel.setTextFill(Color.color(0, 0, 0));
            lastNameLabel.setStyle("-fx-font-weight: regular");
        }
        if(phoneNumberField.getText().equals("")) {
            phoneLabel.setTextFill(Color.color(1, 0, 0));
            phoneLabel.setStyle("-fx-font-weight: bold");
        }else {
            phoneLabel.setTextFill(Color.color(0, 0, 0));
            phoneLabel.setStyle("-fx-font-weight: regular");
        }

        return !nameField.getText().equals("") && !lastNameField.getText().equals("") && !phoneNumberField.getText().equals("");
    }

    public void clickedSearchButton(){
        setDataToTable(searchField.getText());
    }
}
