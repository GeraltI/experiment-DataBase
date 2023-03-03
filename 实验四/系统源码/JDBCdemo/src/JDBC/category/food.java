package JDBC.category;

public class food {

    public food() {
    }

    private int food_id;
    private int restaurant_id;
    private String food_name;
    private String food_application;
    private double food_single_price;
    private Integer food_rest;

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_application() {
        return food_application;
    }

    public void setFood_application(String food_application) {
        this.food_application = food_application;
    }

    public double getFood_single_price() {
        return food_single_price;
    }

    public void setFood_single_price(double food_single_price) {
        this.food_single_price = food_single_price;
    }

    public Integer getFood_rest() {
        return food_rest;
    }

    public void setFood_rest(Integer food_rest) {
        this.food_rest = food_rest;
    }

    @Override
    public String toString() {
        return "food{" +
                "food_id=" + food_id +
                ", restaurant_id=" + restaurant_id +
                ", food_name='" + food_name + '\'' +
                ", food_application='" + food_application + '\'' +
                ", food_single_price=" + food_single_price +
                ", food_rest=" + food_rest +
                '}';
    }
}
