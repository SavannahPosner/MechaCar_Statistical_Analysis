package sample.Model;

public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor.
     *
     * @param machineId The InHouse part machine id
     * @param name
     * @param stock
     * @param id
     * @param min
     * @param max
     * @param price
     */

    public InHouse(int id, String name, Double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * The setMachineId sets the part's machine id.
     *
     * @param machineId The InHouse part machine id
     */

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * getMachineId method.
     *
     * @return The part's machineId
     */

    public int getMachineId() {
        return machineId;
    }


}
