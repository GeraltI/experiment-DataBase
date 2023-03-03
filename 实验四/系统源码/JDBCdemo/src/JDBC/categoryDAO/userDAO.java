package JDBC.categoryDAO;

import JDBC.jdbcDAO;
import JDBC.category.user;

public class userDAO extends jdbcDAO<user> {
    public userDAO() {
        super(user.class);
    }

}