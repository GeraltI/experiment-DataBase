package JDBC.category;

public class orders_food {

    public orders_food() {
    }

    private int orders_id;
    private int food_id;
    private short food_number;

    public int getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(int orders_id) {
        this.orders_id = orders_id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public short getFood_number() {
        return food_number;
    }

    public void setFood_number(short food_number) {
        this.food_number = food_number;
    }

    @Override
    public String toString() {
        return "orders_food{" +
                "orders_id=" + orders_id +
                ", food_id=" + food_id +
                ", food_number=" + food_number +
                '}';
    }
}
