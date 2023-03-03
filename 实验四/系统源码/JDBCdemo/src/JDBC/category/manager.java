package JDBC.category;

public class manager {

    public manager() {
    }

    private int user_id;
    private String user_name;
    private int manager_id;
    private String manager_name;

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

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    @Override
    public String toString() {
        return "manager{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", manager_id=" + manager_id +
                ", manager_name='" + manager_name + '\'' +
                '}';
    }
}
