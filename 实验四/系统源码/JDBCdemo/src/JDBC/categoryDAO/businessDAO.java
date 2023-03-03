package JDBC.categoryDAO;

import JDBC.category.business;
import JDBC.jdbcDAO;

public class businessDAO extends jdbcDAO<business> {
    public businessDAO(){
        super(business.class);
    }
}
