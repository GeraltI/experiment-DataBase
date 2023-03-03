package JDBC.category;

public class canteen {

    public canteen() {
    }

    private int canteen_id;
    private String canteen_name;
    private String canteen_address;
    private String canteen_application;

    public int getCanteen_id() {
        return canteen_id;
    }

    public void setCanteen_id(int canteen_id) {
        this.canteen_id = canteen_id;
    }

    public String getCanteen_name() {
        return canteen_name;
    }

    public void setCanteen_name(String canteen_name) {
        this.canteen_name = canteen_name;
    }

    public String getCanteen_address() {
        return canteen_address;
    }

    public void setCanteen_address(String canteen_address) {
        this.canteen_address = canteen_address;
    }

    public String getCanteen_application() {
        return canteen_application;
    }

    public void setCanteen_application(String canteen_application) {
        this.canteen_application = canteen_application;
    }

    @Override
    public String toString() {
        return "canteen{" +
                "canteen_id=" + canteen_id +
                ", canteen_name='" + canteen_name + '\'' +
                ", canteen_address='" + canteen_address + '\'' +
                ", canteen_application='" + canteen_application + '\'' +
                '}';
    }
}
