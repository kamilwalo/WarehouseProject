package warehouse.app.javafx;



//TODO invoices entity
//TODO new interface

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import warehouse.app.db.DbConnector;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {



        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main-windows.fxml"))));
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
//        new MainFrame();


    }


    public static void main(String[] args) {
        launch(args);
    }
}