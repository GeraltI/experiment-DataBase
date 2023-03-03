package JDBC.categoryDAO;

import JDBC.category.manager;
import JDBC.jdbcDAO;

public class managerDAO extends jdbcDAO<manager> {
    public managerDAO() {
        super(manager.class);
    }
}
