package JDBC.categoryDAO;

import JDBC.category.client;
import JDBC.jdbcDAO;

public class clientDAO extends jdbcDAO<client> {
    public clientDAO(){
        super(client.class);
    }
}
