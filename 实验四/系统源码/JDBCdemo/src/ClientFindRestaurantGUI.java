import JDBC.category.canteen;
import JDBC.category.restaurant;
import JDBC.categoryDAO.canteenDAO;
import JDBC.categoryDAO.restaurantDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ClientFindRestaurantGUI {
    private JPanel topPanel;
    private JScrollPane RestaurantPane;
    private JTable RestaurantTable;
    private JLabel headerLabel;
    private JPanel bottomPanel;
    private JButton OrderButton;
    public JPanel MainPanel;

    private restaurantDAO dao = new restaurantDAO();
    private List<restaurant> restaurantList;

    private JPanel parentPanel;

    public ClientFindRestaurantGUI(JPanel parentPanel) throws SQLException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.parentPanel = parentPanel;
        String[] columnName = {"餐厅编号","餐厅名字", "餐厅位置","餐厅介绍"};

        String sql = "select * from restaurant;";
        restaurantList = dao.getList(sql);

        String[][] tableData = new String[restaurantList.size()][4];

        int num = 0;
        for (restaurant restaurant : restaurantList) {
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

        OrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = RestaurantTable.getSelectedRow();
                System.out.println(row + 1);
                if (row != -1) {
                    //进入该餐厅
                    ClientGUI.orderFoodRestaurant_id = Integer.parseInt(RestaurantTable.getValueAt(RestaurantTable.getSelectedRow(), 0).toString());
                    MainPanel.removeAll();
                    MainPanel.setVisible(false);
                    parentPanel.removeAll();
                    ClientOrderFoodGUI clientOrderFoodGUI = null;
                    try {
                        clientOrderFoodGUI = new ClientOrderFoodGUI(parentPanel);
                    } catch (SQLException | NoSuchFieldException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                    parentPanel.setLayout(new BorderLayout());
                    parentPanel.add(clientOrderFoodGUI.MainPanel,BorderLayout.CENTER);
                    parentPanel.validate();
                    parentPanel.setVisible(true);
                }
            }
        });
    }
}
