package itmo.localpiper.p3;

public class Drink {
    private final String type;
    private int quantity;

    public Drink(String type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void consume(int mil) {
        quantity -= mil;
    }
}
