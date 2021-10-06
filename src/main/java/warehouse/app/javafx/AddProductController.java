package warehouse.app.javafx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import warehouse.Entity.Product;
import warehouse.Entity.ProductCategory;
import warehouse.Entity.ProductDetail;
import warehouse.app.db.DbConnector;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddProductController extends Controller implements Initializable {

    @FXML TextField productNameField;
    @FXML TextField productPriceFieldText;
    @FXML TextField productQuantityField;
    @FXML ComboBox  productCategoryComboBox;
    @FXML Label     errorAddProductField;

    @FXML TextField idModificationTextField;
    @FXML Label     productNameModificationLabel;
    @FXML Label     priceModificationLabel;
    @FXML Label     categoryModificationLabel;
    @FXML Label     quantityModificationLabel;
    @FXML TextField productNameTextField;
    @FXML TextField priceTextField;
    @FXML TextField quantityTextField;
    @FXML ComboBox  productCategoryModificationComboBox;
    @FXML Button    modifyButton;
    @FXML Label     errorModificationLabel;

    private ProductDetail productToModification = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Object>  listOfProductCategory = FXCollections.observableArrayList();
        Runnable connectToDb = ()-> Platform.runLater(()->{
            dbConnector = new DbConnector();
            sf = dbConnector.getSf();
            startSession();
            listOfProductCategory.addAll(session.createQuery("select productCategory from ProductCategory").getResultList());
            stopSession();
        });
        Thread thread = new Thread(connectToDb);
        thread.setDaemon(true);
        thread.start();

        productCategoryComboBox.setItems(listOfProductCategory);
        productCategoryModificationComboBox.setItems(listOfProductCategory);
    }

    public void findProduct(){
        if (idModificationTextField.getText().matches("[0-9]+")){
            startSession();
            List<ProductDetail> result = session.createQuery("from ProductDetail pd  where product.productId=:id")
                    .setParameter("id", Integer.parseInt(idModificationTextField.getText())).getResultList();
            stopSession();
            productToModification=null;
            for (ProductDetail objects : result) productToModification = objects;

            if (productToModification != null) {
                productNameModificationLabel.setText(productToModification.getProduct().getProductName());
                priceModificationLabel.setText(String.valueOf(productToModification.getProduct().getPrice()));
                categoryModificationLabel.setText(productToModification.getProductCategory().getProductCategory());
                quantityModificationLabel.setText(String.valueOf(productToModification.getQuantityInStock()));
                errorModificationLabel.setText("");
            } else {
                setNulls();
                errorModificationLabel.setText("there is no such product");
            }
            clearTextFieldsModify();
        }else {
            errorModificationLabel.setText("wrong Id");
            clearTextFieldsModify();
            setNulls();
        }
    }

    private void setNulls() {
        productNameModificationLabel.setText("null");
        priceModificationLabel.setText("null");
        categoryModificationLabel.setText("null");
        quantityModificationLabel.setText("null");
    }

    private void clearTextFieldsModify () {
        productNameTextField.setText("");
        priceTextField.setText("");
        productCategoryModificationComboBox.getSelectionModel().select(null);
        quantityTextField.setText("");
    }

    public void modifyProduct(){
        if((priceTextField.getText().matches("[0-9]+") && !priceTextField.getText().equals(""))|| priceTextField.getText().equals("")) {
            if ((quantityTextField.getText().matches("[0-9]+") && !quantityTextField.getText().equals("")) || quantityTextField.getText().equals("")) {
                if (productToModification != null) {
                    errorModificationLabel.setText("");


                    if (productCategoryModificationComboBox.getSelectionModel().getSelectedIndex() >= 0)
                        productToModification.getProductCategory().setId(productCategoryModificationComboBox.getSelectionModel().getSelectedIndex() + 1);
                    if (!quantityTextField.getText().equals(""))
                        productToModification.setQuantityInStock(Integer.parseInt(quantityTextField.getText()));
                    if (!productNameTextField.getText().equals(""))
                        productToModification.getProduct().setProductName(productNameTextField.getText());
                    if (!priceTextField.getText().equals(""))
                        productToModification.getProduct().setPrice(Integer.valueOf(priceTextField.getText()));

                    startSession();
                    session.update(productToModification.getProduct());
                    session.update(productToModification);
                    stopSession();
                    findProduct();
                    System.out.println();
                    System.out.println(productToModification.toString());

                } else
                    errorModificationLabel.setText("set product!");
            } else errorModificationLabel.setText("wrong data!");
        }else errorModificationLabel.setText("wrong data!");
    }

    public void deleteProduct(){
        if(productToModification!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting product");
            alert.setHeaderText("You are going to delete product " + productToModification.getProduct().getProductName());
            alert.setContentText("are you sure ?");

            if (alert.showAndWait().isPresent() ) {
                startSession();
                session.createQuery("delete from ProductDetail where product.productId=:productId")
                        .setParameter("productId", productToModification.getProduct().getProductId()).executeUpdate();
                session.createQuery("delete from Product where productId=:productId")
                        .setParameter("productId", productToModification.getProduct().getProductId()).executeUpdate();
                stopSession();
                clearLabelModify();
                clearTextFieldsModify();
            }
        }else{
            errorModificationLabel.setText("set product!");
        }
    }

    private void clearLabelModify() {
        productNameModificationLabel.setText("null");
        priceModificationLabel.setText("null");
        categoryModificationLabel.setText("null");
        quantityModificationLabel.setText("null");
    }

    public void addProduct(){
        if(productNameField.getText().equals("") || productPriceFieldText.getText().equals("") || productQuantityField.getText().equals("")
        || productCategoryComboBox.getSelectionModel().isEmpty() ||
        !productPriceFieldText.getText().matches("[0-9]+") || !productQuantityField.getText().matches("[0-9]+")){
            System.out.println("error");
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Adding product");
            alert.setHeaderText("You are going to add product to database" + productNameField.getText() );
            alert.setContentText("are you sure ?");

            if (alert.showAndWait().isPresent()) {
                Product product = new Product();
                ProductDetail productDetail = new ProductDetail();
                product.setProductName(productNameField.getText());
                product.setPrice(Integer.valueOf(productPriceFieldText.getText()));
                productDetail.setProduct(product);
                productDetail.setQuantityInStock(Integer.valueOf(productQuantityField.getText()));
                ProductCategory productCategory = new ProductCategory();
                productCategory.setId(productCategoryComboBox.getSelectionModel().getSelectedIndex());
                productDetail.setProductCategory(productCategory);

                startSession();
                session.save(product);
                session.save(productDetail);
                stopSession();

                clearAddingProductTextFields();
            }

        }
    }

    private void clearAddingProductTextFields() {
        productNameField.setText("");
        productPriceFieldText.setText("");
        productQuantityField.setText("");
        productCategoryComboBox.getSelectionModel().select(null);
    }
}