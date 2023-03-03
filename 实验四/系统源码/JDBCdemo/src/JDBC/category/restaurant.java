package JDBC.category;

public class restaurant {

    public restaurant() {
    }

    private int restaurant_id;
    private int canteen_id;
    private int business_id;
    private String restaurant_name;
    private String restaurant_location;
    private String restaurant_application;

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getCanteen_id() {
        return canteen_id;
    }

    public void setCanteen_id(int canteen_id) {
        this.canteen_id = canteen_id;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_location() {
        return restaurant_location;
    }

    public void setRestaurant_location(String restaurant_location) {
        this.restaurant_location = restaurant_location;
    }

    public String getRestaurant_application() {
        return restaurant_application;
    }

    public void setRestaurant_application(String restaurant_application) {
        this.restaurant_application = restaurant_application;
    }

    @Override
    public String toString() {
        return "restaurant{" +
                "restaurant_id=" + restaurant_id +
                ", canteen_id=" + canteen_id +
                ", business_id=" + business_id +
                ", restaurant_name='" + restaurant_name + '\'' +
                ", restaurant_location='" + restaurant_location + '\'' +
                ", restaurant_application='" + restaurant_application + '\'' +
                '}';
    }
}
