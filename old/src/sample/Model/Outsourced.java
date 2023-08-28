package sample.Model;

/**
 * This Out-sourced class hold's info whether a Part is outsourced.
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Constructor.
     *
     * @param companyName The InHouse part machine id
     * @param price       The InHouse part price
     * @param max         The InHouse part max
     * @param min         The InHouse part min
     * @param id          The InHouse part id
     * @param stock       The InHouse part inventory level
     * @param name        The InHouse part name
     */
    public Outsourced(int id, String name, Double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * The setCompanyName sets the part's company name.
     *
     * @param companyName The InHouse part machine id
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * getCompanyName method.
     *
     * @return The part's company name
     */
    public String getCompanyName() {
        return companyName;
    }


}
