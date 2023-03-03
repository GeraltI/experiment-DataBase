package JDBC.category;

import java.time.LocalDateTime;

public class orders {

    public orders() {
    }

    private long order_id;
    private int client_id;
    private int restaurant_id;
    private String client_phone_number;
    private String order_address;
    private double order_price;
    private boolean order_paid;
    private boolean order_confirm;
    private boolean order_finish;
    private LocalDateTime order_create_time;
    private LocalDateTime order_finish_time;

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getClient_phone_number() {
        return client_phone_number;
    }

    public void setClient_phone_number(String client_phone_number) {
        this.client_phone_number = client_phone_number;
    }

    public String getOrder_address() {
        return order_address;
    }

    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }

    public boolean isOrder_paid() {
        return order_paid;
    }

    public void setOrder_paid(boolean order_paid) {
        this.order_paid = order_paid;
    }

    public boolean isOrder_confirm() {
        return order_confirm;
    }

    public void setOrder_confirm(boolean order_confirm) {
        this.order_confirm = order_confirm;
    }

    public boolean isOrder_finish() {
        return order_finish;
    }

    public void setOrder_finish(boolean order_finish) {
        this.order_finish = order_finish;
    }

    public LocalDateTime getOrder_create_time() {
        return order_create_time;
    }

    public void setOrder_create_time(LocalDateTime order_create_time) {
        this.order_create_time = order_create_time;
    }

    public LocalDateTime getOrder_finish_time() {
        return order_finish_time;
    }

    public void setOrder_finish_time(LocalDateTime order_finish_time) {
        this.order_finish_time = order_finish_time;
    }

    @Override
    public String toString() {
        return "orders{" +
                "order_id=" + order_id +
                ", client_id=" + client_id +
                ", restaurant_id=" + restaurant_id +
                ", client_phone='" + client_phone_number + '\'' +
                ", order_address='" + order_address + '\'' +
                ", order_price=" + order_price +
                ", order_paid=" + order_paid +
                ", order_confirm=" + order_confirm +
                ", order_finish=" + order_finish +
                ", order_create_time=" + order_create_time +
                ", order_finish_time=" + order_finish_time +
                '}';
    }
}
