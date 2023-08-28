package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * The Inventory class is a class that holds data for both parts  and products.
 */
public class Inventory {

    /**
     * @value allParts
     * @value allProducts
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * addPart method.
     * This adds a new part
     *
     * @param newPart This a new created part
     */

    public static void addPart(Part newPart) {
        if (newPart != null) {
            allParts.add(newPart);
        }
    }

    /**
     * addProduct method.
     * This adds a new Product
     *
     * @param newProduct This a new created Product
     */

    public static void addProduct(Product newProduct) {
        if (newProduct != null) allProducts.add(newProduct);
    }

    /**
     * lookupPart-id method.
     * This looks up part using id
     *
     * @param partId This is a the part id
     * @return null
     */
    public static Part lookupPart(int partId) {


        for (Part p : allParts) {
            if (String.valueOf(p.getId()).toLowerCase().contains(String.valueOf(partId))) return p;
        }
        return null;

    }

    /**
     * lookupProduct-id method.
     * This looks up product using id
     *
     * @param productId This is the product id
     * @return null
     */
    public static Product lookupProduct(int productId) {
        for (Product p : allProducts) {
            if (String.valueOf(p.getId()).contains(String.valueOf(productId))) return p;
        }
        return null;
    }

    /**
     * lookupPart-name method.
     * This looks up part using name
     *
     * @param partName This is the part Name
     * @return parts - ObservableList
     */
    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        FilteredList<Part> filteredData = new FilteredList<>(allParts, p -> true);
        filteredData.setPredicate(filteredPart -> {
            if (partName == null || partName.isEmpty()) {
                return true;
            }

            return filteredPart.getName().toLowerCase().contains(partName.toLowerCase());
        });

        filteredParts.setAll(filteredData);
        return filteredParts;
    }

    /**
     * lookupProduct-name method.
     * This looks up product using name
     *
     * @param productName This is the Product name
     * @return product observable list
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> result = FXCollections.observableArrayList();
        for (Product obj : allProducts) {
            if (obj.getName().toLowerCase().contains(productName.toLowerCase())) {
                result.add(obj);
            }
        }
        return result;
    }

    /**
     * updatePart method.
     * This updates a part
     *
     * @param selectedPart This is a selected part
     * @param index        This is a selected Index
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * updateProduct method.
     * This updates a product
     *
     * @param newProduct This is a selectedproduct
     * @param index      This is the selected's product index
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * deletePart method.
     * This deletes a part.
     *
     * @param selectedPart This is a selected part
     * @return boolean
     */
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    /**
     * deleteProduct method.
     * This deletes a product.
     *
     * @param selectedProduct This is a Selected Product
     * @return boolean
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (selectedProduct.getAllAssociatedParts().isEmpty()) {
            selectedProduct.getAllAssociatedParts().toString();
            return true;
        } else {
            selectedProduct.getAllAssociatedParts().toString();
            return false;
        }
    }

    /**
     * getAllParts method.
     *
     * @return getAllParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * getAllProducts method.
     *
     * @return getAllProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


}
