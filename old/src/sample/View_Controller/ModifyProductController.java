package sample.View_Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides functionality to modify a product.
 */
public class ModifyProductController implements Initializable {
    @FXML
    private Button cancel_product;

    @FXML
    private TextField product_id;

    @FXML
    private TextField product_name;

    @FXML
    private TextField product_inv;

    @FXML
    private TextField product_price;

    @FXML
    private TextField product_max;

    @FXML
    private TextField product_min;


    @FXML
    private TextField search_part;


    @FXML
    private Button save_product;

    @FXML
    private Button move_part;

    @FXML
    private Button remove_associated_part;


    @FXML
    private TableView<Part> partTableView;


    @FXML
    private TableView<Part> associatedParts_Table;

    @FXML
    private TableColumn<Part, Integer> part_id;


    @FXML
    private TableColumn<Part, String> part_Name;

    @FXML
    private TableColumn<Part, Integer> part_level;

    @FXML
    private TableColumn<Part, Double> part_cost;


    @FXML
    private TableColumn<Part, Integer> asspart_id;


    @FXML
    private TableColumn<Part, String> asspart_Name;

    @FXML
    private TableColumn<Part, Integer> asspart_level;

    @FXML
    private TableColumn<Part, Double> asspart_cost;


    Inventory inventory;
    Product product;

    ObservableList<Part> associatedItems = FXCollections.observableArrayList();

    /**
     * This method sets the inventory from main.
     *
     * @param inv This initialises the inventory and is passed from main
     */
    public void setInventory(Inventory inv) {
        this.inventory = inv;
    }


    /**
     * This method updates text fields with the selected input.
     *
     * @param p - This is product p that has been selected to be modified and updated
     */
    public void setProduct(Product p) {
        this.product = p;

        System.out.println(product.getName());

        product_id.setText(Integer.toString(product.getId()));
        product_name.setText(product.getName());
        product_price.setText(Double.toString(product.getPrice()));
        product_max.setText(Integer.toString(product.getMax()));
        product_min.setText(Integer.toString(product.getMin()));
        product_inv.setText(Integer.toString(product.getStock()));

        if (product.getAllAssociatedParts().isEmpty()) {
            associatedParts_Table.setPlaceholder(new Label(("No parts associated with this product.Add Parts")));
        } else {
            associatedParts_Table.setItems(product.getAllAssociatedParts());
        }

    }

    /**
     * This method removes associated parts from product.
     */
    @FXML
    private void setRemove_part() {
        Part selectedPart = associatedParts_Table.getSelectionModel().getSelectedItem();
        associatedItems.remove(selectedPart);
        product.deleteAssociatePart(selectedPart);
    }

    /**
     * This method adds associated parts from product.
     */
    @FXML
    private void setMove_part() {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null && !associatedItems.contains(selectedPart)) {
            associatedItems.add(selectedPart);
            product.addAssociatedPart(selectedPart);
        } else {
            alertBox("Please select Part to add or Item is already added");
        }

    }

    /**
     * This method cancels the Modify Products form.
     *
     * @throws IOException This catches an exception thrown during input output operations
     */
    public void HideModifyProductsForm() throws IOException {

        ButtonType OK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType CANCEL = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are You Sure You Want cancel Modifying Product", OK, CANCEL);

        alert.setTitle("Exit the Modify Product Form");
        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(res -> {
            if (res.equals(OK)) {


                try {
                    Stage stage;
                    Parent root;

                    stage = (Stage) cancel_product.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    root = loader.load(getClass().getResource("../Inventory_main.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                alert.hide();
            }
        });
    }


    /**
     * This method initializes the search function and returns the typed input if found.
     */
    @FXML
    private void setSearch_part() {
        search_part.textProperty().addListener((Obs, oldText, newText) -> {

            ObservableList<Part> foundItems = FXCollections.observableArrayList();

            if (isNumeric(search_part.getText()) && !search_part.getText().isEmpty()) {
                int number = Integer.parseInt(search_part.getText());
                Part p = inventory.lookupPart(number);

                if (p == null) {
                    System.out.println("Table is empty");

                    partTableView.setItems(null);
                    partTableView.setPlaceholder(new Label("Part Not Found"));
                } else {

                    foundItems.add(p);
                    partTableView.setItems(foundItems);

                }

            } else if (!isNumeric(search_part.getText()) && !search_part.getText().isEmpty()) {
                partTableView.setItems(inventory.lookupPart(search_part.getText()));
            } else {
                partTableView.setItems(inventory.getAllParts());
            }
        });
    }

    /**
     * This method validates that an input is digits.
     *
     * @param str - Passed string to check whether it is numeric
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     * This method sets the parts table view.
     */
    private void setPartTableView() {
        partTableView.refresh();

        partTableView.setItems(this.inventory.getAllParts());
        part_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        part_cost.setCellValueFactory(new PropertyValueFactory<>("price"));
        part_level.setCellValueFactory(new PropertyValueFactory<>("stock"));
        part_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    /**
     * This method sets the associated parts table.
     */
    private void setAssociatedParts_Table() {
        associatedParts_Table.refresh();
        associatedParts_Table.setItems(associatedItems);
        associatedParts_Table.setPlaceholder(new Label("No parts associated with this product"));
        asspart_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        asspart_cost.setCellValueFactory(new PropertyValueFactory<>("price"));
        asspart_level.setCellValueFactory(new PropertyValueFactory<>("stock"));
        asspart_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    /**
     * This method initializes the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setPartTableView();
        setAssociatedParts_Table();
        setSearch_part();
        List<TextField> textFields = Arrays.asList(product_name, product_price, product_min, product_max, product_inv);
        textFields.forEach(textField -> textField.textProperty().addListener((obs, old, newWord) -> {

            try {
                textField.setText(newWord);
                setValues(textField);
            } catch (NumberFormatException e) {
                System.out.println("I got you error");
            }
        }));

    }

    /**
     * This method updates products old values.
     *
     * @param ts This is a textfield that has been selected by user to update info
     */
    public void setValues(TextField ts) {
        if (ts.getId().equals(product_id.getId())) {
            product.setId(Integer.parseInt(ts.getText()));
        } else if (ts.getId().equals(product_name.getId())) {
            product.setName(ts.getText());
        } else if (ts.getId().equals(product_inv.getId())) {
            product.setStock(Integer.parseInt(ts.getText()));
        } else if (ts.getId().equals(product_price.getId())) {
            product.setPrice(Double.parseDouble(ts.getText()));
        } else if (ts.getId().equals(product_max.getId())) {
            product.setMax(Integer.parseInt(ts.getText()));
        } else {
            product.setMin(Integer.parseInt(ts.getText()));
        }
    }

    /**
     * This method saves a modified product and pushes it to main.
     */
    @FXML
    private void SaveModifiedProduct() throws IOException {

        Parent parent;
        Stage stage;
        stage = (Stage) save_product.getScene().getWindow();
        List<TextField> textFields = Arrays.asList(product_name, product_inv, product_max, product_min, product_price);

        if (!checkIfEmpty(textFields)) {
            if (validationPassed(textFields)) {
                if (checkMinMAxInv(Integer.parseInt(product_min.getText()), Integer.parseInt(product_max.getText()), Integer.parseInt(product_inv.getText()))) {

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Inventory_main.fxml"));
                        parent = loader.load();
                        Scene scene = new Scene(parent);
                        stage.setScene(scene);
                        stage.setTitle("Inventory");
                        Inventory_mainController controller = loader.getController();
                        controller.setSelectedProduct(product);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Item not modified");
                    }

                } else {
                    alertBox("Min should be less than Max. Inv Should be less than Max and Min");
                }
            } else {
                alertBox("Name should be letters, Inv,Max,Min and Price should be numbers");
            }

        } else {
            alertBox("Every Field must be filled");
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
     * This method checks that the textfields are not empty.
     */

    @FXML
    private boolean checkIfEmpty(List<TextField> ts) {

        boolean isEmpty = false;

        for (TextField textField : ts) {
            if (textField.getText().trim().length() == 0) isEmpty = true;
        }

        return isEmpty;
    }

    /**
     * This method is part of the validation -methods checks for the input values for max,min and inv
     * max>inv>min
     *
     * @returns boolean.
     */
    @FXML
    private boolean checkMinMAxInv(int min, int max, int inv) {

        if (inv >= min) {
            return max > inv;
        } else {
            return false;
        }

    }

    /**
     * This method validates strings and integers and doubles as the needed inputs.
     */

    @FXML
    private boolean validationPassed(List<TextField> ts) {

        boolean isValid = true;

        for (TextField textField : ts) {
            String Id = textField.getId();
            try {
                if (Id.equals(product_inv.getId()) || Id.equals(product_max.getId()) || Id.equals(product_min.getId())) {
                    Integer.valueOf(textField.getText());


                } else if (Id.equals(product_price.getId())) {

                    Double.valueOf(textField.getText());

                } else {
                    Id = textField.getText();


                    if (Character.isDigit(Id.charAt(0))) {
                        isValid = false;

                    } else if (Id.length() > 1) {
                        for (int i = 0; i < Id.length(); i++) {
                            if (Character.isDigit(Id.charAt(i))) {
                                return false;
                            } else {
                                isValid = true;
                            }

                        }
                    } else {
                        isValid = true;
                    }


                }


            } catch (NumberFormatException e) {
                isValid = false;

            }

        }

        return isValid;
    }

}
