import JDBC.category.food;
import JDBC.category.orders;
import JDBC.category.restaurant;
import JDBC.categoryDAO.foodDAO;
import JDBC.categoryDAO.ordersDAO;
import JDBC.categoryDAO.restaurantDAO;
import JDBC.jdbcDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class BusinessRestaurantGUI {
    public JPanel MainPanel;
    private JPanel TopPanel;
    private JPanel BottomPanel;
    private JPanel OrderPanel;
    private JScrollPane OrderScrollPanel;
    private JTable OrderTable;
    private JLabel headerLabel;
    private JButton ConfirmButton;
    private JPanel FoodPanel;
    private JScrollPane FoodScrollPanel;
    private JButton ChangeFoodButton;
    private JButton ADDFoodButton;
    private JTextField LocationField;
    private JTextField ApplicationField;
    private JButton ChangeRestaurantButton;
    private JTextField NameField;
    private JLabel RestaurantIdLabel;
    private JTable FoodTable;

    private restaurantDAO restaurantdao = new restaurantDAO();
    private List<restaurant> restaurantList;
    private restaurant r;

    private ordersDAO ordersdao = new ordersDAO();
    private List<orders> ordersList;

    private foodDAO fooddao = new foodDAO();
    private List<food> foodList;

    int ordersMax;
    String[] ordersColumnName = {"订单编号","电话号码", "收餐地址","订单价格","订单支付状态","商家接单状态","订单完成状态","订单生成时间","订单完成时间",};
    String[][] ordersTableData;
    DefaultTableModel ordersTableModel;

    int foodMax;
    String[] foodColumnName = {"食物编号","食物名字", "食物单价","食物介绍","食物剩余",};
    String[][] foodTableData;
    DefaultTableModel foodTableModel;



    public BusinessRestaurantGUI() throws SQLException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        RestaurantIdLabel.setText(BusinessGUI.business_restaurant_id + "");
        restaurantInit();
        ordersInit();
        foodInit();

        ChangeRestaurantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = NameField.getText();
                String location = LocationField.getText();
                String application = ApplicationField.getText();
                if(name.isEmpty()  || name.isBlank() || location.isEmpty()  || location.isBlank() || application.isEmpty() || application.isBlank()){
                    JOptionPane.showMessageDialog(null, "修改栏中不能有空!", "",JOptionPane.WARNING_MESSAGE);
                }
                else{
                    Object[] options ={ "是", "否" };  //自定义按钮上的文字
                    int m = JOptionPane.showOptionDialog(null, "确定要更改餐厅信息吗？", "提示",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    boolean change = (m == 0);
                    if(change){
                        String sql = "update restaurant set restaurant_name = ? , restaurant_location = ? , restaurant_application = ? where restaurant_id = ? ;";
                        try {
                            System.out.println(jdbcDAO.update(sql,name,location,application,BusinessGUI.business_restaurant_id) + " rows");
                        } catch (SQLException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        ConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = OrderTable.getSelectedRow();
                System.out.println(row + 1);
                if (row != -1) {
                    if(ordersTableData[row][4].equals(String.valueOf(true))){
                        if(ordersTableData[row][5].equals(String.valueOf(false))){
                            Object[] options ={ "是", "否" };  //自定义按钮上的文字
                            int m = JOptionPane.showOptionDialog(null, "要确认订单吗？", "提示",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                            boolean change = (m == 0);
                            if(change){
                                ordersTableData[row][5] = String.valueOf(true);
                                String sql = "update orders set order_confirm = ? where order_id = ? ;";
                                try {
                                    System.out.println(jdbcDAO.update(sql,true,ordersTableData[row][0]) + " rows");
                                } catch (SQLException | ClassNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                                //表格模型
                                ordersTableModel = new DefaultTableModel(ordersTableData, ordersColumnName) {
                                    @Override
                                    public boolean isCellEditable(int row, int col) {
                                        return false;
                                    }
                                };

                                //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
                                OrderTable.setModel(ordersTableModel);
                                OrderScrollPanel.setViewportView(OrderTable);
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "订单已经确认!", "",JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "客户未支付不能确认!", "",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        ChangeFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = FoodTable.getSelectedRow();
                System.out.println(row + 1);
                if (row != -1) {
                    int food_id = Integer.parseInt(foodTableData[row][0]);
                    String food_name = foodTableData[row][1];
                    double food_price = Double.parseDouble(foodTableData[row][2]);
                    String food_application = foodTableData[row][3];
                    Integer food_rest = Integer.valueOf(foodTableData[row][4]);
                    BusinessFoodGUI businessFoodGUI = new BusinessFoodGUI(food_name,food_price,food_application,food_rest);

                    Object[] options ={ "是", "否" };  //自定义按钮上的文字
                    int m = JOptionPane.showOptionDialog(null, businessFoodGUI.MainPanel, "要修改食物信息吗?",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    boolean change = (m == 0);
                    if(change){
                        food food = businessFoodGUI.getFood();
                        foodTableData[row][1] = food.getFood_name();
                        foodTableData[row][2] = food.getFood_single_price() + "";
                        foodTableData[row][3] = food.getFood_application();
                        foodTableData[row][4] = food.getFood_rest() + "";
                        String sql = "update food set food_name = ? , food_single_price = ? , food_application = ? , food_rest = ? where food_id = ? ;";
                        try {
                            System.out.println(jdbcDAO.update(sql,food.getFood_name(),food.getFood_single_price(),food.getFood_application(),food.getFood_rest(),food_id) + " rows");
                        } catch (SQLException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        //表格模型
                        foodTableModel = new DefaultTableModel(foodTableData, foodColumnName) {
                            @Override
                            public boolean isCellEditable(int row, int col) {
                                return false;
                            }
                        };

                        //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
                        FoodTable.setModel(foodTableModel);
                        FoodScrollPanel.setViewportView(FoodTable);
                    }
                }
            }
        });

        ADDFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BusinessFoodGUI businessFoodGUI = new BusinessFoodGUI("",0,"",0);

                Object[] options ={ "是", "否" };  //自定义按钮上的文字
                int m = JOptionPane.showOptionDialog(null, businessFoodGUI.MainPanel, "要添加食物信息吗?",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                boolean change = (m == 0);
                if(change){
                    food food = businessFoodGUI.getFood();
                    String insert = "insert into food values (?,?,?,?,?,?);";
                    try {
                        System.out.println(jdbcDAO.update(insert,null,BusinessGUI.business_restaurant_id,food.getFood_name(),
                                food.getFood_application(),food.getFood_single_price(),food.getFood_rest()) + " rows");
                    } catch (SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    List<food> foods = null;
                    String sql = "select * from food where restaurant_id = ?;";
                    try {
                        foods = fooddao.getList(sql,BusinessGUI.business_restaurant_id);
                    } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchFieldException ex) {
                        ex.printStackTrace();
                    }
                    food f = foods.get(foods.size() - 1);

                    foodMax++;
                    String[][] TableData = new String[foodMax][5];
                    for(int i = 0; i < foodMax - 1; i++){
                        TableData[i] = foodTableData[i];
                    }
                    TableData[foodMax - 1][0] = f.getFood_id() + "";
                    TableData[foodMax - 1][1] = f.getFood_name();
                    TableData[foodMax - 1][2] = f.getFood_application();
                    TableData[foodMax - 1][3] = f.getFood_single_price() + "";
                    TableData[foodMax - 1][4] = f.getFood_rest() + "";
                    foodTableData = TableData;

                    //表格模型
                    foodTableModel = new DefaultTableModel(foodTableData, foodColumnName) {
                        @Override
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };

                    //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
                    FoodTable.setModel(foodTableModel);
                    FoodScrollPanel.setViewportView(FoodTable);
                }
            }
        });
    }

    private void restaurantInit() throws SQLException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "select * from restaurant where restaurant_id = ? ;";
        restaurantList = restaurantdao.getList(sql,BusinessGUI.business_restaurant_id);
        r = restaurantList.get(0);
        if(!r.getRestaurant_name().isEmpty()){
            NameField.setText(r.getRestaurant_name());
        }
        if(!r.getRestaurant_location().isEmpty()){
            LocationField.setText(r.getRestaurant_location());
        }
        if(!r.getRestaurant_application().isEmpty()){
            ApplicationField.setText(r.getRestaurant_application());
        }
    }

    private void ordersInit() throws SQLException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "select * from orders where restaurant_id = ?;";
        ordersList = ordersdao.getList(sql,BusinessGUI.business_restaurant_id);
        ordersMax = ordersList.size();
        ordersTableData = new String[ordersMax][9];

        int num = 0;
        for (orders order : ordersList) {
            ordersTableData[num][0] = order.getOrder_id() + "";
            ordersTableData[num][1] = order.getClient_phone_number();
            ordersTableData[num][2] = order.getOrder_address();
            ordersTableData[num][3] = order.getOrder_price() + "";
            ordersTableData[num][4] = order.isOrder_paid() + "";
            ordersTableData[num][5] = order.isOrder_confirm() + "";
            ordersTableData[num][6] = order.isOrder_finish() + "";
            ordersTableData[num][7] = order.getOrder_create_time().toString();
            if(order.getOrder_finish_time() != null){
                ordersTableData[num][8] = order.getOrder_finish_time().toString();
            }
            num = num + 1;
        }
        //表格模型
        ordersTableModel = new DefaultTableModel(ordersTableData, ordersColumnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
        OrderTable.setModel(ordersTableModel);
        OrderScrollPanel.setViewportView(OrderTable);
    }

    private void foodInit() throws SQLException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "select * from food where restaurant_id = ?;";
        foodList = fooddao.getList(sql,BusinessGUI.business_restaurant_id);
        foodMax = foodList.size();
        foodTableData = new String[foodMax][5];

        int num = 0;
        for (food food : foodList) {
            foodTableData[num][0] = food.getFood_id() + "";
            foodTableData[num][1] = food.getFood_name();
            foodTableData[num][2] = food.getFood_single_price() + "";
            if(food.getFood_application() != null){
                foodTableData[num][3] = food.getFood_application();
            }
            foodTableData[num][4] = food.getFood_rest() + "";
            num = num + 1;
        }
        //表格模型
        foodTableModel = new DefaultTableModel(foodTableData, foodColumnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
        FoodTable.setModel(foodTableModel);
        FoodScrollPanel.setViewportView(FoodTable);
    }

}
