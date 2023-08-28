package sample.View_Controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Model.Inventory;
import sample.Model.Part;
import sample.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * This controller extends the main functionality to navigate to other controllers and set up the tableviews
 */
public class Inventory_mainController implements Initializable {
    //Parts

    @FXML
    private TableView<Part> parts_table;


    @FXML
    private TableColumn<Part, Integer> part_id;


    @FXML
    private TableColumn<Part, String> part_Name;

    @FXML
    private TableColumn<Part, Integer> part_level;

    @FXML
    private TableColumn<Part, Double> part_cost;


    @FXML
    private TextField part_search_field;

    @FXML
    private Button exit_main;

    @FXML
    private Button add_part;

    @FXML
    private Button modify_part;


    @FXML
    private Button modify_product;

    @FXML
    private Label table_message;


    //Product

    @FXML
    private TableView<Product> products_table;

    @FXML
    private TableColumn<Product, Integer> product_id;


    @FXML
    private TableColumn<Product, String> product_Name;

    @FXML
    private TableColumn<Product, Integer> product_level;

    @FXML
    private TableColumn<Product, Double> product_cost;


    @FXML
    private TextField product_search_field;


    Inventory inventory;


    /**
     * This method sets the inventory form main.
     *
     * @param v this is a passed inventory
     */
    public void setInventory(Inventory v) {
        this.inventory = v;
    }


    /**
     * This method gets the updated part and updates it.
     *
     * @param p -This is a part item
     */
    public void setSelectedPart(Part p) {

        for (Part updatedPart : inventory.getAllParts()) {
            if (p.getId() == updatedPart.getId()) {
                int index = inventory.getAllProducts().indexOf(p);
                inventory.updatePart(index, p);
            }
        }
    }


    /**
     * This method gets the updated product and updates it.
     *
     * @param product - this is a product item
     */
    public void setSelectedProduct(Product product) {

        for (Product updatedProduct : inventory.getAllProducts()) {
            if (product.getId() == updatedProduct.getId()) {
                int index = inventory.getAllProducts().indexOf(product);
                inventory.updateProduct(index, product);
            }
        }
    }


    /**
     * This method sets up the Parts table.
     */
    public void setParts_table() {
        parts_table.refresh();

        parts_table.setItems(this.inventory.getAllParts());
        part_id.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        part_cost.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        part_level.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        part_Name.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
    }


    /**
     * This method sets up the Products table.
     */
    public void setProducts_table() {
        products_table.refresh();
        products_table.setItems(this.inventory.getAllProducts());
        product_id.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        product_cost.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        product_level.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        product_Name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
    }


    /**
     * This method initializes the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setParts_table();
        setProducts_table();
        SearchParts();
        SearchProduct();

    }

    /**
     * This method initializes the search function and returns the typed Product input if found.
     */
    public void SearchProduct() {

        product_search_field.textProperty().addListener((Obs, oldText, newText) -> {


            ObservableList<Product> foundItems = FXCollections.observableArrayList();

            if (isNumeric(product_search_field.getText()) && !product_search_field.getText().isEmpty()) {
                int number = Integer.parseInt(product_search_field.getText());
                Product p = inventory.lookupProduct(number);

                if (p == null) {
                    products_table.setItems(null);
                    products_table.setPlaceholder(new Label("Product Not Found"));
                    alertBox("Part not Found");
                } else {
                    foundItems.add(p);
                    products_table.setItems(foundItems);
                }

            } else if (!isNumeric(product_search_field.getText()) && !product_search_field.getText().isEmpty()) {
                if (inventory.lookupPart(product_search_field.getText()).isEmpty()) {
                    alertBox("Product not Found");
                } else {
                    products_table.setItems(inventory.lookupProduct(product_search_field.getText()));
                }
            } else {
                products_table.setItems(inventory.getAllProducts());
            }
        });
    }

    /**
     * This method initializes the search function and returns the typed Parts input if found.
     * <p>
     * Part G a: This function can result in a logical error where a user is not able to search for both
     * products using name and id correctly. First,it can result in a number exception error,a return of the
     * wrong item or no items,or cause the data not to be populated.To fix this - add a listener to the search_field
     * to listen for changes ,initialise an empty observable array to hold the return values-This is helpful because
     * searching with an part Id only returns a part and the tableview only accepts observable lists Then include the
     * isNumeric function to determine whether the input is a digit or string so that we can use the appropriate functions.
     * Check whether the search_field is empty to avoid the number exception error when trying to convert it to integer.
     * To synchronize both part id and name - add the id to an observable list and return a specific item - The lookup
     * part overloaded method returns a specific instance while the name returns a List.
     */
    public void SearchParts() {
        part_search_field.textProperty().addListener((Obs, oldText, newText) -> {


            ObservableList<Part> foundItems = FXCollections.observableArrayList();

            if (isNumeric(part_search_field.getText()) && !part_search_field.getText().isEmpty()) {
                int number = Integer.parseInt(part_search_field.getText());
                Part p = inventory.lookupPart(number);

                if (p == null) {

                    parts_table.setItems(null);
                    parts_table.setPlaceholder(new Label("Part Not Found"));
                    alertBox("Product not Found");
                } else {

                    foundItems.add(p);
                    parts_table.setItems(foundItems);

                }

            } else if (!isNumeric(part_search_field.getText()) && !part_search_field.getText().isEmpty()) {

                if (inventory.lookupPart(part_search_field.getText()).isEmpty()) {
                    alertBox("Product not Found");
                } else {
                    parts_table.setItems(inventory.lookupPart(part_search_field.getText()));
                }

            } else {
                parts_table.setItems(inventory.getAllParts());
            }
        });
    }

    /**
     * validates digit inputs.
     *
     * @param str - The value to be checked  if numeric
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }


    /**
     * This method pushes to the Add parts form.
     */
    @FXML
    private void pushToAddPartsForm(ActionEvent event) throws IOException {
        Parent parent;
        Stage stage;
        stage = (Stage) modify_part.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart.fxml"));
        parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Add Part");
        AddPartController controller = loader.getController();
        controller.setInventory(this.inventory);
    }


    /**
     * This method pushes to the Add products form.
     */

    @FXML
    private void pushToAddProductsForm() throws IOException {
        Parent parent;
        Stage stage;
        stage = (Stage) modify_part.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
        parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Add Product");
        AddProductController controller = loader.getController();
        controller.setInventory(inventory);
    }

    /**
     * This method pushes to the Modify Parts form.
     */
    @FXML
    private void pushToModifyPartsForm(ActionEvent event) throws IOException {

        if (parts_table.getSelectionModel().getSelectedItem() != null) {
            Part selectedPart = parts_table.getSelectionModel().getSelectedItem();
            Parent parent;
            Stage stage;
            stage = (Stage) modify_part.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
            parent = loader.load();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Modify Part");
            ModifyPartController controller = loader.getController();
            controller.setPart(selectedPart);
            controller.getInventory(inventory);
        } else {
            alertBox("Please select Part to Modify");
        }
    }

    /**
     * This is a warning dialog box.
     */
    private void alertBox(String err) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Error");
        alert.setContentText(err);

        alert.showAndWait();
    }

    /**
     * This method pushes to the Modify Products form.
     */
    @FXML
    private void pushToModifyProductsForm() throws IOException {
        if (products_table.getSelectionModel().getSelectedItem() != null) {
            Product selectedProduct = products_table.getSelectionModel().getSelectedItem();
            Parent parent;
            Stage stage;
            stage = (Stage) modify_product.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
            parent = loader.load();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Modify Product");
            ModifyProductController controller = loader.getController();
            controller.setProduct(selectedProduct);
            controller.setInventory(inventory);
        } else {
            alertBox("Please select Product to Modify");
        }

    }

    /**
     * This a Button function that deletes a part from the tableview and the partlist.
     */
    @FXML
    private void deletePart(ActionEvent event) {
        Part selectedPart = parts_table.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            parts_table.getItems().remove(selectedPart);
            parts_table.refresh();
            inventory.deletePart(selectedPart);
        } else {
            alertBox("Please select part to delete");
        }
    }

    /**
     * This a Button function that deletes a product from the tableview and the product list.
     */
    @FXML
    private void deleteProduct(ActionEvent event) {


        Product selectedProduct = products_table.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            if (inventory.deleteProduct(selectedProduct)) {
                inventory.getAllProducts().remove(selectedProduct);
                products_table.getItems().remove(selectedProduct);
                products_table.refresh();
            } else {
                alertBox("Product has Associated Parts Cannot be Deleted");
            }

        } else {
            alertBox("Please select product to delete");
        }
    }


    /**
     * This is a button function that exits the application.
     */
    @FXML
    private void exitInventory() {
        ButtonType OK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType CANCEL = new ButtonType("CANCEl", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are You Sure You Want to exit", OK, CANCEL);

        alert.setTitle("Exit the Inventory system");
        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(res -> {
            if (res.equals(OK)) {
                Stage stage = (Stage) exit_main.getScene().getWindow();
                stage.close();
            } else {
                alert.hide();
            }
        });
    }
}
