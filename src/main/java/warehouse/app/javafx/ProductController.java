package warehouse.app.javafx;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import warehouse.Entity.ProductCategory;
import warehouse.Entity.ProductDetail;
import warehouse.app.EmployeeLoginContainer;
import warehouse.app.db.DbConnector;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController extends Controller implements Initializable {

    @FXML private TableView<Object[]> productTable;
    @FXML private TableColumn <ProductDetail,Object> idProductColumn;
    @FXML private TableColumn <ProductDetail,String> nameOfProductColumn;
    @FXML private TableColumn <ProductDetail,Integer> priceOfProductColumn;
    @FXML private TableColumn <ProductDetail ,Integer> quantityOfProductColumn;

    @FXML private ComboBox categoryComboBox;

    @FXML private TextField searchField;


    private ObservableList<Object[]> products;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setIconToButtonMenu();

        productTable.setPlaceholder(new Label("Waiting for connection with db..."));
        Runnable connectToDb = ()-> Platform.runLater(()->{
            dbConnector = new DbConnector();
            sf = dbConnector.getSf();
            setTable("", "");

            startSession();
            List<ProductCategory> resultList = session.createQuery("from ProductCategory ").getResultList();
            stopSession();

            ObservableList<Object> observableList = FXCollections.observableArrayList();
            observableList.add("-all-");
            for (ProductCategory productCategory : resultList) {
                observableList.add(productCategory.getProductCategory());
            }
            categoryComboBox.setItems(observableList);
            categoryComboBox.setValue("-all-");
            productTable.setPlaceholder(new Label("No results"));
        });
        Thread thread = new Thread(connectToDb);
        thread.setDaemon(true);
        thread.start();
    }

    public void searchButtonClicked(){
        if(categoryComboBox.getValue()!=null)
        setTable(searchField.getText(), (String) categoryComboBox.getValue());
        else setTable(searchField.getText(), "-all-");
    }

    private void setTable(String textFromSearchField,String category){
        products = FXCollections.observableArrayList();
        if (category.equals("-all-")) category="";
        startSession();

        if (textFromSearchField.equals("") && category.equals(""))
            products.addAll(session.createQuery("from  ProductDetail ").getResultList());
        else
        if (textFromSearchField.matches("[0-9]+") && category.equals(""))
            products.addAll(session.createQuery("from ProductDetail where product.productId = :productId ")
                    .setParameter("productId",Integer.parseInt(textFromSearchField)).getResultList());
        else
        if(!textFromSearchField.equals("") && !textFromSearchField.matches("[0-9]+") && category.equals(""))
            products.addAll(session.createQuery("from ProductDetail where product.productName like '%" + textFromSearchField + "%'").getResultList());
        else
        if (textFromSearchField.equals(""))
            products.addAll(session.createQuery("from  ProductDetail  where  productCategory.productCategory=:productCategory")
                    .setParameter("productCategory",category).getResultList());
        else
        if (textFromSearchField.matches("[0-9]+"))
            products.addAll(session.createQuery("from ProductDetail  where product.productId = :productId and productCategory.productCategory=:productCategory")
                    .setParameter("productId",Integer.parseInt(textFromSearchField))
                    .setParameter("productCategory",category)
                    .getResultList());
        else
        if( !textFromSearchField.matches("[0-9]+"))
            products.addAll(session.createQuery("from ProductDetail where product.productName like '%" + textFromSearchField + "%' and productCategory.productCategory=:productCategory")
                            .setParameter("productCategory",category)
                    .getResultList());



        stopSession();

        idProductColumn.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getProduct().getProductId()));
        nameOfProductColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getProductName()));
        priceOfProductColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getProduct().getPrice()));
        quantityOfProductColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getQuantityInStock()));

        productTable.setItems(products);
    }
}