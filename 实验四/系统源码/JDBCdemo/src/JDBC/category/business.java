package JDBC.category;

public class business {

    public business() {
    }

    private int user_id;
    private String user_name;
    private int business_id;
    private String business_name;
    private String business_application;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getBusiness_application() {
        return business_application;
    }

    public void setBusiness_application(String business_application) {
        this.business_application = business_application;
    }

    @Override
    public String toString() {
        return "business{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", business_id=" + business_id +
                ", business_name='" + business_name + '\'' +
                ", business_application='" + business_application + '\'' +
                '}';
    }
}
