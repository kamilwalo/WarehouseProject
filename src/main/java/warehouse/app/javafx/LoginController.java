package warehouse.app.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import warehouse.Entity.Employee;
import warehouse.Entity.EmployeeAccount;
import warehouse.app.EmployeeLoginContainer;
import warehouse.app.db.DbConnector;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class LoginController extends Controller implements Initializable {

    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Label error;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbConnector = new DbConnector();
        sf = dbConnector.getSf();
        loginField.setText("test");
        passwordField.setText("test");
    }

    public void login(ActionEvent event) throws IOException {
        Employee employee = null;
        startSession();
        List<EmployeeAccount> resultList = session.createQuery("from EmployeeAccount where password=:password and login=:login ")
                .setParameter("password", passwordField.getText())
                .setParameter("login", loginField.getText())
                .getResultList();
        stopSession();
        for (EmployeeAccount employeeResult : resultList) {
            employee=employeeResult.getIdEmployee();
        }
        if(employee==null){
            error.setText("Wrong password or login");
        }else {
            EmployeeLoginContainer.setLoggedEmployee(employee);
            switchToProductScene(event);
        }
    }
}
