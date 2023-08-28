package sample.View_Controller;

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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides functionality to modify and edit a arts features.
 */
public class ModifyPartController implements Initializable {

    @FXML
    private RadioButton radio_in_house;

    @FXML
    private RadioButton radio_outsourced;

    @FXML
    private Label radio_option;

    @FXML
    private TextField part_machineId;


    @FXML
    private Button cancel_part;

    @FXML
    private TextField part_Id;

    @FXML
    private TextField part_Name;

    @FXML
    private TextField part_Max;

    @FXML
    private TextField part_Min;

    @FXML
    private TextField part_Price;

    @FXML
    private TextField part_Inv;

    @FXML
    private Button save_part;

    Part part;

    Inventory inventory;


    /**
     * This sets up the part in the correct texfields to be  modified by user.
     *
     * @param p This is the selected part to be modified
     */
    public void setPart(Part p) {

        this.part = p;

        part_Id.setText(Integer.toString(part.getId()));
        part_Name.setText(part.getName());
        part_Price.setText(Double.toString(part.getPrice()));
        part_Max.setText(Integer.toString(part.getMax()));
        part_Min.setText(Integer.toString(part.getMin()));
        part_Inv.setText(Integer.toString(part.getStock()));

        if (part instanceof InHouse) {

            radio_in_house.setSelected(true);
            radio_outsourced.setSelected(false);
            InHouse inHouse = (InHouse) part;
            radio_option.setText("Machine ID");
            part_machineId.setText(Integer.toString(inHouse.getMachineId()));
            part_machineId.promptTextProperty().setValue("Machine ID");

        } else {
            radio_in_house.setSelected(false);
            radio_outsourced.setSelected(true);
            Outsourced outsourced = (Outsourced) part;
            radio_option.setText("Company Name");
            part_machineId.setText(outsourced.getCompanyName());
            part_machineId.promptTextProperty().setValue("Company Name");
        }

    }

    /**
     * This method gets the inventory from the main controller.
     *
     * @param inv This is the inventory
     */
    public void getInventory(Inventory inv) {
        this.inventory = inv;

    }

    /**
     * This method intitalizes the Moodify Part Controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        List<TextField> textFields = Arrays.asList(part_Name, part_Price, part_Inv, part_Min, part_Max, part_machineId);
        textFields.forEach(textField -> {
            textField.textProperty().addListener((obs, old, newWord) -> {
                // TODO here

                try {

                    textField.setText(newWord);
                    setValues(textField);
                } catch (NumberFormatException e) {
                    System.out.println("I got you error");
                }
            });
        });
    }


    /**
     * This method gets user input and updates the required fields.
     *
     * @param ts This is a textfield that has been selected by user to update info
     */
    public void setValues(TextField ts) {
        if (ts.getId().equals(part_Id.getId())) {
            part.setId(Integer.parseInt(ts.getText()));
        } else if (ts.getId().equals(part_Name.getId())) {
            part.setName(ts.getText());
        } else if (ts.getId().equals(part_Inv.getId())) {
            part.setStock(Integer.parseInt(ts.getText()));
        } else if (ts.getId().equals(part_Price.getId())) {
            part.setPrice(Double.parseDouble(ts.getText()));
        } else if (ts.getId().equals(part_Max.getId())) {
            part.setMax(Integer.parseInt(ts.getText()));
        } else if (ts.getId().equals(part_Min.getId())) {
            part.setMin(Integer.parseInt(ts.getText()));
        } else if (ts.getId().equals(part_machineId.getId())) {

            if (part instanceof InHouse && radio_in_house.isSelected()) {

                InHouse inHouse = (InHouse) part;
                inHouse.setMachineId(Integer.parseInt(ts.getText()));

            } else if (part instanceof Outsourced && radio_outsourced.isSelected()) {
                Outsourced outsourced = (Outsourced) part;
                outsourced.setCompanyName(ts.getText());

            } else if (part instanceof InHouse && !radio_in_house.isSelected()) {

                inventory.deletePart(part);
                part = new Outsourced(Integer.parseInt(part_Id.getText()), part_Name.getText(), Double.parseDouble(part_Price.getText()), Integer.parseInt(part_Inv.getText()), Integer.parseInt(part_Min.getText()), Integer.parseInt(part_Max.getText()), part_machineId.getText());
                inventory.addPart(part);


            } else {

                inventory.deletePart(part);
                part = new InHouse(Integer.parseInt(part_Id.getText()), part_Name.getText(), Double.parseDouble(part_Price.getText()), Integer.parseInt(part_Inv.getText()), Integer.parseInt(part_Min.getText()), Integer.parseInt(part_Max.getText()), Integer.parseInt(part_machineId.getText()));
                inventory.addPart(part);
            }


        }


    }

    /**
     * This method listens to the inhouse radio-listener to set the correct source.
     */
    @FXML
    private void In_houseListener() {
        if (radio_in_house.isSelected()) radio_option.setText("Machine ID");
        part_Id.setText(Integer.toString(part.getId()));
        part_machineId.promptTextProperty().setValue("Machine ID");
        radio_outsourced.setSelected(false);

    }

    /**
     * This method listens to the outsource radio-listener to set the correct source.
     */
    @FXML
    private void outSourcedListener() {
        if (radio_outsourced.isSelected()) {
            radio_option.setText("Company Name");
            part_machineId.promptTextProperty().setValue("Company Name");
        }
        radio_in_house.setSelected(false);
    }


    /**
     * This method cancels the part add form if nothing is done
     * and returns to main view.
     */
    @FXML
    private void HideModifyPartsForm() throws IOException {

        ButtonType OK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType CANCEL = new ButtonType("CANCEl", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are You Sure You Want cancel Modifying Part", OK, CANCEL);

        alert.setTitle("Exit the Modify Part Form");
        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(res -> {
            if (res.equals(OK)) {

                try {
                    Stage stage;
                    Parent root;

                    stage = (Stage) cancel_part.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    root = loader.load(getClass().getResource("../Inventory_main.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (NullPointerException | IOException e) {
                    e.printStackTrace();
                }

            } else {
                alert.hide();
            }
        });

    }


    /**
     * This method saves the modified part and pushes it to the main controller to be updated.
     */
    @FXML
    private void SaveModifiedPart() throws IOException {

        Parent parent;
        Stage stage;
        stage = (Stage) save_part.getScene().getWindow();

        List<TextField> textFields = Arrays.asList(part_Name, part_Inv, part_Max, part_Min, part_machineId, part_Price);

        if (!checkIfEmpty(textFields)) {
            if (validationPassed(textFields)) {
                if (checkMinMAxInv(Integer.parseInt(part_Min.getText()), Integer.parseInt(part_Max.getText()), Integer.parseInt(part_Inv.getText()))) {

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Inventory_main.fxml"));
                        parent = loader.load();
                        Scene scene = new Scene(parent);
                        stage.setScene(scene);
                        stage.setTitle("Inventory");
                        Inventory_mainController controller = loader.getController();
                        controller.setSelectedPart(part);
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

    @FXML
    private boolean validationPassed(List<TextField> ts) throws IOException {

        boolean isValid = true;

        for (TextField textField : ts) {
            String Id = textField.getId();
            try {
                if (Id.equals(part_Inv.getId()) || Id.equals(part_Max.getId()) || Id.equals(part_Min.getId())) {
                    Integer.valueOf(textField.getText());


                } else if (Id.equals(part_Price.getId())) {

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

}
