package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class defines a products
 */
public class Product {

    /**
     * @value associatedParts initializes the associated parts for the product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private Double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor
     *
     * @param id    The product's identifier
     * @param name  The product's name
     * @param price The product's retail price
     * @param stock The amount available in th inventory
     * @param min   The min  for the product
     * @param max   The max  for the product
     */

    public Product(int id, String name, Double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * The setId sets the product's id.
     *
     * @param id The product's identifier.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The setName sets the product's name.
     *
     * @param name The product's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The setPrice sets the product's price.
     *
     * @param price The product's retail price.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * The setStock sets the product's stock.
     *
     * @param stock The amount available in th inventory.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * The setMin sets the product's min.
     *
     * @param min The min  for the product.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * The setMax sets the product's max.
     *
     * @param max The max for the product.
     */
    public void setMax(int max) {
        this.max = max;
    }


    /**
     * getId method.
     *
     * @return The product's id.
     */
    public int getId() {
        return id;
    }

    /**
     * getName method.
     *
     * @return The product's name.
     */
    public String getName() {
        return name;
    }

    /**
     * getPrice method.
     *
     * @return The product's price.
     */
    public Double getPrice() {
        return price;
    }


    /**
     * getStock method.
     *
     * @return The product's stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * getMin method.
     *
     * @return The product's min.
     */
    public int getMin() {
        return min;
    }

    /**
     * getMax method
     *
     * @return The product's Max
     */
    public int getMax() {
        return max;
    }

    /**
     * addAssociatedPart method.
     * adds part to products list.
     *
     * @param part This is the associated part for the Product
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * deleteAssociatePart method.
     * deletes part form products.
     *
     * @param selectedAssociatedPart This is the selected associated part
     * @return boolean
     */
    public boolean deleteAssociatePart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * getAllAssociatedParts method.
     *
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {

        return associatedParts;
    }
}
