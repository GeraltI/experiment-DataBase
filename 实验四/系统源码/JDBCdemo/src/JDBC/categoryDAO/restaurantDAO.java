package JDBC.categoryDAO;

import JDBC.category.restaurant;
import JDBC.jdbcDAO;

public class restaurantDAO extends jdbcDAO<restaurant> {
    public restaurantDAO() {
        super(restaurant.class);
    }

}
