package JDBC.categoryDAO;

import JDBC.category.canteen;
import JDBC.jdbcDAO;

public class canteenDAO extends jdbcDAO<canteen> {
    public canteenDAO(){
        super(canteen.class);
    }
}
