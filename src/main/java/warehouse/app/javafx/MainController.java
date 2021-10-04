package warehouse.app.javafx;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import warehouse.Entity.ProductDetail;
import warehouse.app.db.DbConnector;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private TableView<Object[]> productTable;
    @FXML private TableColumn <ProductDetail,Object> idProductColumn;
    @FXML private TableColumn <ProductDetail,String> nameOfProductColumn;
    @FXML private TableColumn <ProductDetail,Integer> priceOfProductColumn;
    @FXML private TableColumn <ProductDetail ,Integer> quantityOfProductColumn;



    @FXML private TextField searchField;
    @FXML private Button searchButton;

    private final DbConnector dbConnector = new DbConnector();
    private final SessionFactory sf = dbConnector.getSf();
    private Session session =sf.getCurrentSession();

    ObservableList<Object[]> products;


    private void startSession(){
        session = sf.getCurrentSession();
        session.beginTransaction();
    }

    private void stopSession(){
        session.getTransaction().commit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable("");
    }

    public void searchButtonClicked(){
        setTable(searchField.getText());
    }

    private void setTable(String textFromSearchField){
        products = FXCollections.observableArrayList();
        startSession();


        if (textFromSearchField.equals(""))products.addAll(
                session.createQuery(
                        "from  ProductDetail ").getResultList());
        else {
            if (textFromSearchField.matches("[0-9]+"))
            products.addAll(session.createQuery("from ProductDetail where product.productId = :productId ")
                    .setParameter("productId",Integer.parseInt(textFromSearchField))
                    .getResultList());
            else products.addAll(session.createQuery("from ProductDetail where product.productName like '%"+ textFromSearchField +"%'")
                    .getResultList());
        }
        stopSession();

        idProductColumn.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getProduct().getProductId()));
        nameOfProductColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getProductName()));
        priceOfProductColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getProduct().getPrice()));
        quantityOfProductColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getQuantityInStock()));


        productTable.setItems(products);
    }
}