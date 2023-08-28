package sample.View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.InHouse;
import sample.Model.Inventory;
import sample.Model.Outsourced;
import sample.Model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 This class provides functionality of adding a product.
 */
public class AddPartController implements Initializable {

    @FXML
    private RadioButton radio_in_house;

    @FXML
    private RadioButton radio_outsourced;

    @FXML
    private TextField part_machineId;

    @FXML
    private TextField part_Name;


    @FXML
    private TextField part_Inv;


    @FXML
    private TextField part_price;


    @FXML
    private TextField part_Max;


    @FXML
    private TextField part_Min;

    @FXML
    private Label radio_option;

    @FXML
    private TextField part_id;

    @FXML
    private Button cancel_part;


    @FXML
    private Button save_part;


    Inventory inventory;
    private int Id;


    /**
     This method sets the inventory from main.
     @param v This is an inventory item
     */
    public void setInventory(Inventory v) {
        this.inventory = v;
    }

    /**
     This method initializes the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Id = generateUniqueNumber();
        part_id.setText(Integer.toString(Id));
    }

    /**
     This method listens for the inhouse radiobutton
     and assigns the correct labels.
     */
    @FXML
    private void In_houseListener(ActionEvent event) {
        if (radio_in_house.isSelected()) {
            radio_option.setText("Machine ID");
            part_machineId.promptTextProperty().setValue("Machine ID");
            radio_outsourced.setSelected(false);
        }
    }

    /**
     This method listens for the outsource  radiobutton
     and assigns the correct labels.
     */
    @FXML
    private void outSourcedListener(ActionEvent event) {
        if (radio_outsourced.isSelected()) {
            radio_option.setText("Company Name");
        }
        part_machineId.clear();
        part_machineId.promptTextProperty().setValue("Company Name");
        radio_in_house.setSelected(false);
    }


    /**
     This method loads the main view and returns part to inventory.
     */
    private void AddPart(Inventory inv) throws IOException {
        Parent parent;
        Stage stage;
        stage = (Stage) save_part.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Inventory_main.fxml"));
        parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Add Part");
        Inventory_mainController controller = loader.getController();
        controller.setInventory(this.inventory);
    }


    /**
     This method cancels the part add form if nothing is done
     and returns to main view.
     @throws IOException catches input out put exceptions
     */
    public void HideAddPartsForm() throws IOException {
        ButtonType OK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType CANCEL = new ButtonType("CANCEl", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Are You Sure You Want Cancel Adding Part?", OK, CANCEL);

        alert.setTitle("Cancel Adding Part");
        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(res -> {
            if (res.equals(OK)) {
                Stage stage;
                Parent root;
                stage = (Stage) cancel_part.getScene().getWindow();

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
     This method gets the input from the user,validates inputs  and adds it to the inventory.
     */
    @FXML
    private void addedPart(ActionEvent event) throws IOException {

        List<TextField> textFields = Arrays.asList(part_Name, part_price, part_Inv, part_Min, part_Max, part_machineId);

        Part p;
        if (!checkIfEmpty(textFields)) {


            if (radio_in_house.isSelected() && validationPassed(textFields)) {
                if (checkMinMAxInv(Integer.parseInt(part_Min.getText()), Integer.parseInt(part_Max.getText()), Integer.parseInt(part_Inv.getText()))) {
                    p = new InHouse(Id,
                            part_Name.getText(),
                            Double.valueOf(part_price.getText()),
                            Integer.parseInt(part_Inv.getText()),
                            Integer.parseInt(part_Min.getText()),
                            Integer.parseInt(part_Max.getText()),
                            Integer.parseInt(part_machineId.getText()));

                    inventory.addPart(p);
                    AddPart(inventory);

                } else {

                    alertBox("Min should be less than Max; and Inv should be between those two values");
                }


            } else if (radio_outsourced.isSelected() && validationPassed(textFields)) {
                if (checkMinMAxInv(Integer.parseInt(part_Min.getText()), Integer.parseInt(part_Max.getText()), Integer.parseInt(part_Inv.getText()))) {

                    p = new Outsourced(Id,
                            part_Name.getText(),
                            Double.valueOf(part_price.getText()),
                            Integer.parseInt(part_Inv.getText()),
                            Integer.parseInt(part_Min.getText()),
                            Integer.parseInt(part_Max.getText()),
                            part_machineId.getText());
                    inventory.addPart(p);
                    AddPart(inventory);


                } else {
                    alertBox("Min should be less than Max; and Inv should be between those two values");
                }

            } else {
                alertBox("name shouel be letters, Min,Max,price and inv should be numbers");
            }
        } else {

            alertBox("Fields Cannot be empty");
        }

    }


    /**
     This is a warning dialog box.
     */
    private void alertBox(String err) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Error");
        alert.setContentText(err);

        alert.showAndWait();
    }


    /**
     This method checks that the textfields are not empty.
     */
    @FXML
    private boolean checkIfEmpty(List<TextField> ts) {
        boolean isEmpty = false;

        for (TextField textField : ts) {
            if (textField.getText().trim().length() == 0) {
                isEmpty = true;
            }
        }

        return isEmpty;
    }


    /**
     This method validates strings and integers and doubles as the needed inputs.
     */

    @FXML
    private boolean validationPassed(List<TextField> ts) throws IOException {
        boolean isValid = true;

        for (TextField textField : ts) {
            String Id = textField.getId();
            try {
                if (Id.equals(part_Inv.getId()) || Id.equals(part_Max.getId()) || Id.equals(part_Min.getId())) {
                    Integer.valueOf(textField.getText());


                } else if (Id.equals(part_price.getId())) {

                    Double.valueOf(textField.getText());

                } else if (Id.equals(part_machineId.getId())) {

                    if (radio_in_house.isSelected()) {
                        Integer.valueOf(textField.getText());
                    }

                } else {
                    Id = textField.getText();


                    if (Character.isDigit(Id.charAt(0))) {

                        isValid = false;

                    } else if (Id.length() > 1) {
                        for (int i = 0; i < Id.length(); i++) {
                            if (Character.isDigit(Id.charAt(i))) {

                                isValid = false;
                                return isValid;
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

    /**
     This method generates unique ids for the parts.
     */

    @FXML
    private Integer generateUniqueNumber() {
        Random ints = new Random();
        Integer Id = ints.nextInt(999999);

        inventory.getAllParts().forEach((item) -> {

            if (item.getId() == Id) {
                ints.nextInt();
            }
            ;

        });

        return Id;
    }


    /**
     This method is part of the validation -methods checks for the input values for max,min and inv
     max>inv>min.

     @returns boolean

     */
    @FXML
    private boolean checkMinMAxInv(int min, int max, int inv) {

        if (inv >= min) {
            return max > inv;
        } else {
            return false;
        }

    }


}
