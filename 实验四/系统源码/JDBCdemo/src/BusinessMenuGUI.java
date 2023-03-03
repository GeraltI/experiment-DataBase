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

public class BusinessMenuGUI {
    public JPanel MainPanel;
    private JPanel MyPanel;
    private JPanel bottomPanel;
    private JButton RestaurantButton;
    private JLabel BusinessIdLabel;
    private JTextField BusinessNameField;
    private JTextField BusinessApplicationField;
    private JButton ChangeButton;
    private JPanel topPanel;
    private JScrollPane RestaurantPane;
    private JTable RestaurantTable;
    private JLabel headerLabel;

    private restaurantDAO restaurantdao = new restaurantDAO();
    private List<restaurant> restaurantList;

    private JPanel parentPanel;

    public BusinessMenuGUI(JPanel parentPanel) throws SQLException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.parentPanel = parentPanel;
        init();

        ChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = BusinessNameField.getText();
                String application = BusinessApplicationField.getText();
                if(name.isEmpty() || application.isEmpty()){
                    JOptionPane.showMessageDialog(null, "名称和介绍不能为空", "",JOptionPane.WARNING_MESSAGE);
                }
                else{
                    String sql = "update business set business_name = ? , business_application = ? where business_id = ?;";
                    try {
                        System.out.println(jdbcDAO.update(sql,name,application,BusinessGUI.business_id) + " rows");
                    } catch (SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "修改成功", "",JOptionPane.PLAIN_MESSAGE);
                    try {
                        init();
                    } catch (SQLException | NoSuchFieldException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        RestaurantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = RestaurantTable.getSelectedRow();
                System.out.println(row + 1);
                if (row != -1) {
                    //进入该餐厅
                    BusinessGUI.business_restaurant_id = Integer.parseInt(RestaurantTable.getValueAt(RestaurantTable.getSelectedRow(), 0).toString());
                    MainPanel.removeAll();
                    MainPanel.setVisible(false);
                    parentPanel.removeAll();
                    BusinessRestaurantGUI businessRestaurantGUI = null;
                    try {
                        businessRestaurantGUI = new BusinessRestaurantGUI();
                    } catch (SQLException | NoSuchFieldException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                    parentPanel.setLayout(new BorderLayout());
                    parentPanel.add(businessRestaurantGUI.MainPanel,BorderLayout.CENTER);
                    parentPanel.validate();
                    parentPanel.setVisible(true);
                }
            }
        });
    }

    public void init() throws SQLException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        BusinessIdLabel.setText(BusinessGUI.business_id + "");
        BusinessNameField.setText(BusinessGUI.business_name);
        BusinessApplicationField.setText(BusinessGUI.business_application);

        String[] columnName = {"餐厅编号","餐厅名字", "餐厅位置","餐厅介绍"};

        String sql = "select * from restaurant where business_id = ? ;";
        restaurantList = restaurantdao.getList(sql,BusinessGUI.business_id);

        String[][] tableData = new String[restaurantList.size()][4];

        int num = 0;
        for (JDBC.category.restaurant restaurant : restaurantList) {
            tableData[num][0] = restaurant.getRestaurant_id() + "";
            tableData[num][1] = restaurant.getRestaurant_name();
            tableData[num][2] = restaurant.getRestaurant_location();
            tableData[num][3] = restaurant.getRestaurant_application();
            num = num + 1;
        }

        //表格模型
        DefaultTableModel model = new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
        RestaurantTable.setModel(model);
        RestaurantPane.setViewportView(RestaurantTable);
    }
}
