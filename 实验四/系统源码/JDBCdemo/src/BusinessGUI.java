import JDBC.category.business;
import JDBC.category.restaurant;
import JDBC.categoryDAO.businessDAO;
import JDBC.categoryDAO.restaurantDAO;
import JDBC.jdbcDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class BusinessGUI {
    public JPanel MainPanel;
    private JPanel BottomPanel;
    private JButton MenuButton;
    private JPanel TopPanel;

    public static int business_id;
    public static int business_restaurant_id;
    public static String business_name;
    public static String business_application;

    private businessDAO businessdao = new businessDAO();
    private List<business> businessList;
    private business b;

    public BusinessGUI() throws SQLException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "select * from business where user_id = ? ;";
        businessList = businessdao.getList(sql,MainThread.user_id);
        b = businessList.get(0);
        business_id = b.getBusiness_id();
        business_name = b.getBusiness_name();
        business_application = b.getBusiness_application();

        BusinessMenuGUI businessMenuGUI = new BusinessMenuGUI(BottomPanel);
        BottomPanel.setLayout(new BorderLayout());
        BottomPanel.add(businessMenuGUI.MainPanel,BorderLayout.CENTER);
        BottomPanel.setVisible(true);
        BottomPanel.validate();

        MenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BottomPanel.removeAll();
                BusinessMenuGUI businessMenuGUI = null;
                try {
                    businessMenuGUI = new BusinessMenuGUI(BottomPanel);
                } catch (SQLException | NoSuchFieldException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
                BottomPanel.setLayout(new BorderLayout());
                BottomPanel.add(businessMenuGUI.MainPanel,BorderLayout.CENTER);
                BottomPanel.setVisible(true);
                BottomPanel.validate();
            }
        });
    }

}
