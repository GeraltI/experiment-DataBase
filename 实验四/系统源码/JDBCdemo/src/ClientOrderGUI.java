import JDBC.category.food;
import JDBC.category.orders;
import JDBC.categoryDAO.foodDAO;
import JDBC.categoryDAO.ordersDAO;
import JDBC.jdbcDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ClientOrderGUI {
    public JPanel MainPanel;
    private JPanel topPanel;
    private JScrollPane OrderPanel;
    private JTable OrderTable;
    private JLabel headerLabel;
    private JButton PayButton;
    private JButton FinishButton;

    int max;

    String[] ColumnName = {"订单编号","电话号码", "收餐地址","订单价格","订单支付状态","商家接单状态","订单完成状态","订单生成时间","订单完成时间",};
    String[][] TableData;
    DefaultTableModel TableModel;

    private ordersDAO dao = new ordersDAO();
    private List<orders> ordersList;
    private JPanel parentPanel;

    public ClientOrderGUI() throws SQLException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "select * from orders where client_id = ?;";
        ordersList = dao.getList(sql,ClientGUI.clientId);
        max = ordersList.size();
        String[][] tableData = new String[max][9];

        int num = 0;
        for (orders order : ordersList) {
            tableData[num][0] = order.getOrder_id() + "";
            tableData[num][1] = order.getClient_phone_number();
            tableData[num][2] = order.getOrder_address();
            tableData[num][3] = order.getOrder_price() + "";
            tableData[num][4] = order.isOrder_paid() + "";
            tableData[num][5] = order.isOrder_confirm() + "";
            tableData[num][6] = order.isOrder_finish() + "";
            tableData[num][7] = order.getOrder_create_time().toString();
            if(order.getOrder_finish_time() != null){
                tableData[num][8] = order.getOrder_finish_time().toString();
            }
            num = num + 1;
        }

        TableData = tableData;

        //表格模型
        TableModel = new DefaultTableModel(TableData, ColumnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
        OrderTable.setModel(TableModel);
        OrderPanel.setViewportView(OrderTable);

        PayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = OrderTable.getSelectedRow();
                System.out.println(row + 1);
                if (row != -1) {
                    if(tableData[row][4].equals(String.valueOf(true))){
                        JOptionPane.showMessageDialog(null, "订单已经完成!", "",JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        Object[] options ={ "是", "否" };  //自定义按钮上的文字
                        int m = JOptionPane.showOptionDialog(null, "确定支付订单吗？价格为" + tableData[row][3], "提示",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        boolean change = (m == 0);
                        if(change){
                            tableData[row][4] = String.valueOf(true);
                            String sql = "update orders set order_paid = ? where order_id = ?;";
                            try {
                                System.out.println(jdbcDAO.update(sql,true,Long.parseLong(tableData[row][0])) + " rows");
                            } catch (SQLException | ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                            //表格模型
                            TableModel = new DefaultTableModel(TableData, ColumnName) {
                                @Override
                                public boolean isCellEditable(int row, int col) {
                                    return false;
                                }
                            };

                            //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
                            OrderTable.setModel(TableModel);
                            OrderPanel.setViewportView(OrderTable);
                        }
                    }
                }
            }
        });

        FinishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = OrderTable.getSelectedRow();
                System.out.println(row + 1);
                if (row != -1) {
                    if(tableData[row][5].equals(String.valueOf(false))){
                        JOptionPane.showMessageDialog(null, "商家未确认订单!", "",JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        Object[] options ={ "是", "否" };  //自定义按钮上的文字
                        int m = JOptionPane.showOptionDialog(null, "确定完成订单吗？", "提示",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        boolean change = (m == 0);
                        if(change){
                            tableData[row][6] = String.valueOf(true);
                            tableData[row][8] = String.valueOf(LocalDateTime.now());
                            String sql = "update orders set order_finish = ? , order_finish_time = ? where order_id = ?;";
                            try {
                                System.out.println(jdbcDAO.update(sql,true, LocalDateTime.now(),Long.parseLong(tableData[row][0])) + " rows");
                            } catch (SQLException | ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                            //表格模型
                            TableModel = new DefaultTableModel(TableData, ColumnName) {
                                @Override
                                public boolean isCellEditable(int row, int col) {
                                    return false;
                                }
                            };

                            //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
                            OrderTable.setModel(TableModel);
                            OrderPanel.setViewportView(OrderTable);
                        }
                    }
                }
            }
        });
    }
}
