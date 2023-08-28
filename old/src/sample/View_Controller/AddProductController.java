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
import java.util.*;

/**
 * This class provides functionality for adding products.
 */
public class AddProductController implements Initializable {

    @FXML
    private Button cancel_product;

    @FXML
    private Button save_product;

    @FXML
    private TextField product_id;


    @FXML
    private TextField product_Name;

    @FXML
    private TextField product_price;

    @FXML
    private TextField product_max;

    @FXML
    private TextField product_min;

    @FXML
    private TextField product_inv;

    @FXML
    private TextField search_part;

    @FXML
    private Button remove_part;

    @FXML
    private Button move_part;

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
    private int Id;
    Product p;

    ObservableList<Part> associatedItems = FXCollections.observableArrayList();


    /**
     * This method sets the inventory from main.
     *
     * @param inv - This is an inventory item
     */
    public void setInventory(Inventory inv) {
        this.inventory = inv;
    }

    /**
     * This method cancels the product add form if nothing is done
     * and returns to main view.
     */
    @FXML
    private void HideAddProductsForm() throws IOException {
        ButtonType OK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType CANCEL = new ButtonType("CANCEl", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are You Sure You Want Cancel Adding Product?", OK, CANCEL);

        alert.setTitle("Cancel Adding Product");
        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(res -> {
            if (res.equals(OK)) {
                Stage stage;
                Parent root;
                stage = (Stage) cancel_product.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader();
                try {
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
     * this method saves the @param product and pushes to the main view.
     */
    @FXML
    private void AddNewProduct(Inventory inv) throws IOException {

        Parent parent;
        Stage stage;
        stage = (Stage) save_product.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Inventory_main.fxml"));
        parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Inventory Management System");
        Inventory_mainController controller = loader.getController();
        controller.setInventory(this.inventory);


    }

    /**
     * This method gets the input from the user,validates inputs  and adds it to the inventory.
     */
    @FXML
    private void addProduct() throws IOException {
        List<TextField> textFields = Arrays.asList(product_Name, product_inv, product_max, product_min, product_price);
        if (!checkIfEmpty(textFields)) {
            if (validationPassed(textFields)) {
                if (checkMinMAxInv(Integer.parseInt(product_min.getText()), Integer.parseInt(product_max.getText()), Integer.parseInt(product_inv.getText()))) {
                    Double totalPartsPrice = 0.0;
                    p = new Product(Id, product_Name.getText(), Double.parseDouble(product_price.getText()), Integer.parseInt(product_inv.getText()), Integer.parseInt(product_min.getText()), Integer.parseInt(product_max.getText()));

                    if (!associatedItems.isEmpty()) {
                        for (Part part : associatedItems) {
                            p.addAssociatedPart(part);
                            totalPartsPrice += part.getPrice();
                            System.out.println(totalPartsPrice);

                        }
                        if (p.getPrice() > totalPartsPrice) {
                            inventory.addProduct(p);
                            AddNewProduct(inventory);
                        } else {
                            alertBox("Price of a product cannot be less than the cost of the parts");
                        }
                    } else {
                        inventory.addProduct(p);
                        AddNewProduct(inventory);
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
     * This method removes associated parts from product.
     */
    @FXML
    private void setRemove_part() {
        Part selectedPart = associatedParts_Table.getSelectionModel().getSelectedItem();
        associatedItems.remove(selectedPart);
        if (p != null) {
            p.deleteAssociatePart(selectedPart);
        }
    }

    /**
     * This method moves associated part to product.
     */
    @FXML
    private void setMove_part() {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null && !associatedItems.contains(selectedPart)) {
            associatedItems.add(selectedPart);
        } else {
            alertBox("Please select Item or Add new Part");
        }
        if (p != null) {
            p.addAssociatedPart(selectedPart);
        }
    }

    /**
     * This method searches for parts and updates table view.
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
     * This method validates the inventory lookup.
     *
     * @param str - value to be checked if numeric
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     * This method sets up the parts tableview.
     */
    private void setPartTableView() {
        partTableView.refresh();

        partTableView.setItems(this.inventory.getAllParts());
        part_id.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        part_cost.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        part_level.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        part_Name.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
    }

    /**
     * This method sets up the associated parts table view.
     */
    private void setAssociatedParts_Table() {
        associatedParts_Table.refresh();

        associatedParts_Table.setItems(associatedItems);
        associatedParts_Table.setPlaceholder(new Label("No parts associated with this product"));
        asspart_id.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        asspart_cost.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        asspart_level.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        asspart_Name.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
    }

    /**
     * This method generates unique ids for the parts.
     */
    @FXML
    private Integer generateUniqueNumber() {

        Random ints = new Random();
        Integer Id = ints.nextInt(999999);

        inventory.getAllProducts().forEach((item) -> {
            if (item.getId() == Id) {
                ints.nextInt();
            }
            ;

        });

        return Id;
    }


    /**
     * This method checks that the textfields are not empty.
     */
    @FXML
    private boolean checkIfEmpty(List<TextField> ts) {

        boolean isEmpty = false;

        //add part id
        for (TextField textField : ts) {
            if (textField.getText().trim().length() == 0) isEmpty = true;
        }

        return isEmpty;
    }


    /**
     * this method validates strings and integers and doubles as the needed inputs.
     */

    @FXML
    private boolean validationPassed(List<TextField> ts) throws IOException {

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
                        System.out.println("This is " + Id + " not  a String");
                        isValid = false;

                    } else if (Id.length() > 1) {
                        for (int i = 0; i < Id.length(); i++) {
                            if (Character.isDigit(Id.charAt(i))) {
                                System.out.println("This is " + Id.charAt(i) + " not  a String");
                                isValid = false;
                                return isValid;
                            } else {
                                isValid = true;
                            }

                        }
                    } else {
                        isValid = true;
                        System.out.println("This is " + Id + "  a String");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("The value :  " + Id + " is not a number");
                isValid = false;

            }
        }

        return isValid;
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
     * This initializes the controller and the tableview.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Id = generateUniqueNumber();
        product_id.setText(Integer.toString(Id));
        setPartTableView();
        setAssociatedParts_Table();
        setSearch_part();
    }
}
