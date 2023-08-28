package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Model.InHouse;
import sample.Model.Inventory;
import sample.Model.Outsourced;
import sample.Model.Product;
import sample.View_Controller.Inventory_mainController;

/**
 * This is the main class. This class initializes the application.
 * <p>
 * <p>
 * Part G -b:
 * <p>
 * To extend the functionality of this project I would include a data analysis feature so that users can be able to quickly search for
 * the most used parts and the least used parts. This woudl be important because it would :
 * 1) Ensure profitability - since it would mean the demand for that product is high,
 * 2) Reduce Costs - Since this would mean parts in demand would demand a higher budget than those with less demand -Therefore
 * the company doesn't spend resources on the least effective parts,
 * 3) The data analysis component would also be good at showing trends and making predictions on what direction the company should take for maximum
 * profitability.
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Inventory inv = new Inventory();

        Inventory.addPart(new InHouse(1, "Screw", 600.00, 100, 50, 200, 5));
        inv.addPart(new Outsourced(2, "Nail", 300.00, 150, 50, 200, "NailedIt Ltd"));
        inv.addPart(new InHouse(3, "Glue", 200.00, 200, 50, 250, 23));
        inv.addPart(new InHouse(4, "Door Knob", 669.00, 250, 50, 300, 32));
        inv.addPart(new Outsourced(5, "Hinges", 210.00, 300, 50, 350, "Hinges Inc"));
        inv.addPart(new InHouse(6, "Wrench", 300.00, 350, 50, 400, 76));
        inv.addPart(new Outsourced(7, "Locks", 1000.00, 400, 50, 450, "Locks Inc"));
        inv.addPart(new InHouse(8678, "Frames", 10000.00, 450, 50, 500, 87));
        inv.addPart(new InHouse(8, "Cushions", 10000.00, 450, 50, 500, 87));
        inv.addPart(new Outsourced(9, "Leather", 100.00, 50, 50, 440, "Skins Ltd"));
        inv.addPart(new InHouse(10, "Soft boards", 150.00, 150, 50, 590, 93));

        inv.addProduct(new Product(1, "Chair", 100.00, 50, 44, 104));
        inv.addProduct(new Product(2, "Door", 250.00, 57, 33, 98));
        inv.addProduct(new Product(3, "Cabinet", 370.00, 89, 77, 245));


        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inventory_main.fxml"));
        Parent parent;
        parent = loader.load();
        primaryStage.setTitle("Inventory");
        primaryStage.setScene(new Scene(parent, 940, 600));
        Inventory_mainController controller = loader.getController();
        controller.setInventory(inv);
        primaryStage.show();
    }


}
