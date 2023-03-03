package JDBC.category;

import java.time.LocalDateTime;

public class user {
    private int user_id;
    private String user_name;
    private String user_password;
    private boolean user_active;
    private LocalDateTime user_create_time;

    public user() {
    }

    public int getId() {
        return user_id;
    }

    public void setId(int id) {
        this.user_id = id;
    }

    public String getName() {
        return user_name;
    }

    public void setName(String name) {
        this.user_name = name;
    }

    public String getPassword() {
        return user_password;
    }

    public void setPassword(String password) {
        this.user_password = password;
    }

    public boolean isActive() {
        return user_active;
    }

    public void setActive(boolean active) {
        this.user_active = active;
    }

    public LocalDateTime getCreate_time() {
        return user_create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.user_create_time = create_time;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + user_id +
                ", name='" + user_name + '\'' +
                ", password='" + user_password + '\'' +
                ", active=" + user_active +
                ", create_time=" + user_create_time +
                '}';
    }
}
