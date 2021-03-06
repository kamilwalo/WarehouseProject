package warehouse.app.javafx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import warehouse.app.EmployeeLoginContainer;
import warehouse.app.db.DbConnector;

import java.io.IOException;
import java.util.Objects;

public abstract class Controller  {
    private Parent root;

    protected DbConnector dbConnector ;
    protected SessionFactory sf ;
    protected Session session ;

    @FXML protected Button customerMenuButton;
    @FXML protected Button productMenuButton;
    @FXML protected Label infoAboutAccount;


    protected void setInfoAboutAccount(){
        infoAboutAccount.setText(infoAboutAccount.getText()+" "+ EmployeeLoginContainer.getLoggedEmployee().getNameOfEmployee()+" "+EmployeeLoginContainer.getLoggedEmployee().getLastNameOfEmployee());
    };

    public void switchToAddCustomerScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddCustomer-window.fxml")));
        switchScene(event);
    }

    public void switchToAddOrderScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddOrder-window.fxml")));
        switchScene(event);
    }

    public void switchToProductScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Product-windows.fxml")));
        switchScene(event);
    }

    public void switchToAddProductScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddProduct-window.fxml")));
        switchScene(event);
    }


    public void switchScene(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    protected void startSession(){
        session = sf.getCurrentSession();
        session.beginTransaction();
    }

    protected void stopSession(){
        session.getTransaction().commit();
    }

    protected void setIconToButtonMenu(){
        ImageView customerImage = new ImageView(new Image("images/addCustomer.png"));
        customerImage.setFitHeight(75);
        customerImage.setFitWidth(75);
        customerMenuButton.setGraphic(customerImage);
    }

}
