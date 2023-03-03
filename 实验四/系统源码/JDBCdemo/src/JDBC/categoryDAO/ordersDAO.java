package JDBC.categoryDAO;

import JDBC.category.orders;
import JDBC.jdbcDAO;

public class ordersDAO extends jdbcDAO<orders> {
    public ordersDAO() {
        super(orders.class);
    }

}
