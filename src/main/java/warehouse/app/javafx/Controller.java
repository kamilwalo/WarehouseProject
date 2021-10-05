package warehouse.app.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import warehouse.app.db.DbConnector;

import java.io.IOException;
import java.util.Objects;

public abstract class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;

    protected DbConnector dbConnector ;
    protected SessionFactory sf ;
    protected Session session ;


    public void switchToAddCustomerScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddCustomer-window.fxml")));
        switchScene(event);
    }

    public void switchToProductScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Product-windows.fxml")));
        switchScene(event);
    }


    public void switchScene(ActionEvent event){
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
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
}
