import JDBC.category.food;
import JDBC.category.orders;
import JDBC.categoryDAO.foodDAO;
import JDBC.categoryDAO.ordersDAO;
import JDBC.jdbcDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ClientOrderFoodGUI {
    public JPanel MainPanel;
    private JPanel topPanel;
    private JScrollPane RestaurantPane;
    private JTable RestaurantTable;
    private JLabel headerLabel;
    private JScrollPane OrderFoodPanel;
    private JTable OrderFoodTable;
    private JButton OrderFoodButton;
    private JButton DeleteFoodButton;
    private JLabel PriceLabel;
    private JButton TakeOrderButton;
    private JTextField AddressField;
    private JTextField PhoneField;
    private JLabel 订单地址;

    String[] restaurantColumnName = {"食品编号","食品名字", "食品单价","食品介绍","食品剩余"};
    String[] foodColumnName = {"食品编号","食品名字", "食品单价","食品数量"};
    String[][] restaurantTableData;
    String[][] foodTableData;
    int max;
    int num;

    DefaultTableModel restaurantTableModel;
    DefaultTableModel foodTableModel;

    private foodDAO dao = new foodDAO();
    private List<food> foodList;
    private JPanel parentPanel;

    private void RestaurantPanelInit() throws SQLException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "select * from food where restaurant_id = ?;";
        foodList = dao.getList(sql,ClientGUI.orderFoodRestaurant_id);
        max = foodList.size();
        String[][] tableData = new String[max][5];

        int num = 0;
        for (food food : foodList) {
            tableData[num][0] = food.getFood_id() + "";
            tableData[num][1] = food.getFood_name();
            tableData[num][2] = food.getFood_single_price() + "";
            tableData[num][3] = food.getFood_application();
            tableData[num][4] = food.getFood_rest() + "";
            num = num + 1;
        }

        restaurantTableData = tableData;

        //表格模型
        restaurantTableModel = new DefaultTableModel(restaurantTableData, restaurantColumnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
        RestaurantTable.setModel(restaurantTableModel);
        RestaurantPane.setViewportView(RestaurantTable);

    }

    private void OrderFoodPanelInit() throws SQLException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //表格模型
        foodTableData = new String[max][5];

        foodTableModel = new DefaultTableModel(foodTableData, foodColumnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
        OrderFoodTable.setModel(foodTableModel);
        OrderFoodPanel.setViewportView(OrderFoodTable);

    }

    public ClientOrderFoodGUI(JPanel parentPanel) throws SQLException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.parentPanel = parentPanel;
        RestaurantPanelInit();
        OrderFoodPanelInit();

        PriceLabel.setText("0");
        OrderFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = RestaurantTable.getSelectedRow();
                System.out.println(row + 1);
                if (row != -1) {
                    if(Integer.parseInt(restaurantTableData[row][4]) > 0){
                        restaurantTableData[row][4] = String.valueOf(Integer.parseInt(restaurantTableData[row][4]) - 1);
                        String food_id = restaurantTableData[row][0];
                        boolean find = false;
                        int findNum = 0;
                        for(int i = 0; i < num; i++){
                            if(foodTableData[i][0].equals(food_id)){
                                find = true;
                                findNum = i;
                                break;
                            }
                        }
                        if(find){
                            foodTableData[findNum][3] = String.valueOf(Integer.parseInt(foodTableData[findNum][3]) + 1);
                        }
                        else{
                            findNum = num;
                            foodTableData[findNum][0] = restaurantTableData[row][0];
                            foodTableData[findNum][1] = restaurantTableData[row][1];
                            foodTableData[findNum][2] = restaurantTableData[row][2];
                            foodTableData[findNum][3] = 1 + "";
                            num++;
                        }
                        //表格模型
                        restaurantTableModel = new DefaultTableModel(restaurantTableData, restaurantColumnName) {
                            @Override
                            public boolean isCellEditable(int row, int col) {
                                return false;
                            }
                        };
                        RestaurantTable.setModel(restaurantTableModel);
                        RestaurantPane.setViewportView(RestaurantTable);

                        //表格模型
                        foodTableModel = new DefaultTableModel(foodTableData, foodColumnName) {
                            @Override
                            public boolean isCellEditable(int row, int col) {
                                return false;
                            }
                        };
                        OrderFoodTable.setModel(foodTableModel);
                        OrderFoodPanel.setViewportView(OrderFoodTable);

                        double sum = 0;
                        for(int i = 0; i < num; i++){
                            sum += Double.parseDouble(foodTableData[i][2]) * Integer.parseInt(foodTableData[i][3]);
                        }
                        PriceLabel.setText(sum + "");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "商品销售完了!", "",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        DeleteFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = OrderFoodTable.getSelectedRow();
                System.out.println(row + 1);
                if (row != -1) {
                    String food_id = foodTableData[row][0];
                    Integer food_num = Integer.valueOf(foodTableData[row][3]);
                    for(int i = row; i < num - 1; i++){
                        foodTableData[i][0] = foodTableData[i + 1][0];
                        foodTableData[i][1] = foodTableData[i + 1][1];
                        foodTableData[i][2] = foodTableData[i + 1][2];
                        foodTableData[i][3] = foodTableData[i + 1][3];
                    }
                    foodTableData[num - 1][0] = null;
                    foodTableData[num - 1][1] = null;
                    foodTableData[num - 1][2] = null;
                    foodTableData[num - 1][3] = null;
                    num--;

                    for(int i = 0; i < max; i++){
                        if(restaurantTableData[i][0].equals(food_id)){
                            restaurantTableData[i][4] = Integer.parseInt(restaurantTableData[i][4]) + food_num + "";
                            break;
                        }
                    }
                    //表格模型
                    restaurantTableModel = new DefaultTableModel(restaurantTableData, restaurantColumnName) {
                        @Override
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    RestaurantTable.setModel(restaurantTableModel);
                    RestaurantPane.setViewportView(RestaurantTable);

                    //表格模型
                    foodTableModel = new DefaultTableModel(foodTableData, foodColumnName) {
                        @Override
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    OrderFoodTable.setModel(foodTableModel);
                    OrderFoodPanel.setViewportView(OrderFoodTable);

                    double sum = 0;
                    for(int i = 0; i < num; i++){
                        sum += Double.parseDouble(foodTableData[i][2]) * Integer.parseInt(foodTableData[i][3]);
                    }
                    PriceLabel.setText(sum + "");
                    }
                }
        });

        TakeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Address = AddressField.getText();
                String Phone = PhoneField.getText();
                double price = Double.parseDouble(PriceLabel.getText());
                if(price == 0){
                    JOptionPane.showMessageDialog(null, "至少下单一件商品", "",JOptionPane.WARNING_MESSAGE);
                }
                else if(Address.length() == 0 || Phone.length() == 0){
                    JOptionPane.showMessageDialog(null, "送货地址和电话号码不能为空", "",JOptionPane.WARNING_MESSAGE);
                }
                else{
                    Object[] options ={ "是", "否" };  //自定义按钮上的文字
                    int m = JOptionPane.showOptionDialog(null, "确定要提交订单吗？", "提示",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    boolean change = (m == 0);
                    if(change){
                        String sql = "insert into orders values (?,?,?,?,?,?,?,?,?,?,?);";
                        System.out.println("restaurant "+ClientGUI.orderFoodRestaurant_id);
                        try {
                            System.out.println(jdbcDAO.update(sql,null,ClientGUI.clientId,ClientGUI.orderFoodRestaurant_id,Phone,Address,price,false,false,false,null,null) + " rows");
                        } catch (SQLException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        sql = "select * from orders where client_id = ? and restaurant_id = ?";
                        ordersDAO dao = new ordersDAO();
                        List<orders> ordersList = null;
                        try {
                            ordersList = dao.getList(sql,ClientGUI.clientId,ClientGUI.orderFoodRestaurant_id);
                        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchFieldException ex) {
                            ex.printStackTrace();
                        }
                        long order_id = ordersList.get(ordersList.size() - 1).getOrder_id();
                        for(int i = 0; i < num; i++){
                            sql = "insert into orders_food values (?,?,?);";
                            try {
                                System.out.println(jdbcDAO.update(sql,order_id,foodTableData[i][0],foodTableData[i][3]) + " rows");
                            } catch (SQLException | ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }
                        for(int i = 0; i < max; i++){
                            sql = "update food set food_rest = ? where food_id = ?";
                            try {
                                System.out.println(jdbcDAO.update(sql,restaurantTableData[i][4],restaurantTableData[i][0]) + " rows");
                            } catch (SQLException | ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }
                        JOptionPane.showMessageDialog(null, "下单成功", "",JOptionPane.PLAIN_MESSAGE);
                        MainPanel.setVisible(false);
                        parentPanel.removeAll();
                        ClientOrderGUI clientOrderGUI = null;
                        try {
                            clientOrderGUI = new ClientOrderGUI();
                        } catch (SQLException | NoSuchFieldException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                            ex.printStackTrace();
                        }
                        parentPanel.setLayout(new BorderLayout());
                        parentPanel.add(clientOrderGUI.MainPanel,BorderLayout.CENTER);
                        parentPanel.validate();
                        parentPanel.setVisible(true);

                    }
                }
            }
        });

    }
}
