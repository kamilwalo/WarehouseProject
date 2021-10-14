package warehouse.app.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login-window.fxml"))));
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setScene(scene);

        stage.getIcons().add(new Image("images/addProduct.png"));

        stage.setResizable(false);
        stage.show();

//        new MainFrame();
        System.out.println("lol");

        String aa = "ss";
        String ab = "sssss";
        if (aa.equals(ab)){
            System.out.println("lol");
        }





    }


    public static void main(String[] args) {
        launch(args);
    }
}