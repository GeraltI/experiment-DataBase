package JDBC.category;

import java.util.Date;

public class client {

    public client() {
    }

    private int user_id;
    private String user_name;
    private int client_id;
    private String client_name;
    /**
     * 性别 1 表示男性 2 表示女性
     */
    private Integer client_sex;
    private Date client_birth;
    private String client_hobby;
    private String client_address;
    private String client_contact;

    @Override
    public String toString() {
        String client_sex_toString;
        switch (client_sex){
            case 1:
                client_sex_toString = "男";
                break;
            case 2:
                client_sex_toString = "女";
                break;
            default:
                client_sex_toString = "未知";
                break;
        }
        return "client{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", client_id=" + client_id +
                ", client_name='" + client_name + '\'' +
                ", client_sex=" + client_sex_toString +
                ", client_birth=" + client_birth +
                ", client_hobby='" + client_hobby + '\'' +
                ", client_address='" + client_address + '\'' +
                ", client_contact='" + client_contact + '\'' +
                '}';
    }

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

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public Integer getClient_sex() {
        return client_sex;
    }

    public void setClient_sex(Integer client_sex) {
        this.client_sex = client_sex;
    }

    public Date getClient_birth() {
        return client_birth;
    }

    public void setClient_birth(Date client_birth) {
        this.client_birth = client_birth;
    }

    public String getClient_hobby() {
        return client_hobby;
    }

    public void setClient_hobby(String client_hobby) {
        this.client_hobby = client_hobby;
    }

    public String getClient_address() {
        return client_address;
    }

    public void setClient_address(String client_address) {
        this.client_address = client_address;
    }

    public String getClient_contact() {
        return client_contact;
    }

    public void setClient_contact(String client_contact) {
        this.client_contact = client_contact;
    }
}
