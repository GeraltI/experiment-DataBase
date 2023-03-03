package JDBC.categoryDAO;

import JDBC.category.food;
import JDBC.category.user;
import JDBC.jdbcDAO;

public class foodDAO extends jdbcDAO<food> {
    public foodDAO() {
        super(food.class);
    }

}
