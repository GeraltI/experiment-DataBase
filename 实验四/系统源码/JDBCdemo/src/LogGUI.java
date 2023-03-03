import JDBC.category.business;
import JDBC.category.client;
import JDBC.category.manager;
import JDBC.category.user;
import JDBC.categoryDAO.businessDAO;
import JDBC.categoryDAO.clientDAO;
import JDBC.categoryDAO.managerDAO;
import JDBC.categoryDAO.userDAO;
import com.mysql.cj.xdevapi.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class LogGUI {
    public JPanel MainPanel;
    private JButton LogButton;
    private JTextField userField;
    private JPasswordField passwordField;

    List<user> userList;
    userDAO dao = new userDAO();
    private String user_name;
    private String user_password;

    public LogGUI(){
        LogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user_name = userField.getText();
                user_password = String.valueOf(passwordField.getPassword());
                String sql = "select * from user where user_name = ? and user_password = ?";
                try {
                    userList = dao.getList(sql,user_name,user_password);
                } catch (SQLException | NoSuchFieldException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                    ex.printStackTrace();
                }
                if(userList.isEmpty()){
                    JOptionPane.showMessageDialog(null, "账号和密码不匹配", "",JOptionPane.WARNING_MESSAGE);
                }
                else{
                    user u = userList.get(0);
                    MainThread.user_id = u.getId();

                    clientDAO DAO1 = new clientDAO();
                    List<client> clientList = null;
                    sql = "select * from client where user_id = ?";
                    try {
                        clientList = DAO1.getList(sql, MainThread.user_id);
                    } catch (SQLException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException | InstantiationException ex) {
                        ex.printStackTrace();
                    }
                    if(!clientList.isEmpty()){
                        JOptionPane.showMessageDialog(null, "登录成功", "",JOptionPane.PLAIN_MESSAGE);
                        MainThread.client = true;
                        MainPanel.setVisible(false);
                        synchronized (Main.LOCK){
                            Main.LOCK.notify();
                        }
                    }
                    else{
                        businessDAO DAO2 = new businessDAO();
                        List<business> businessList = null;
                        sql = "select * from business where user_id = ?";
                        try {
                            businessList = DAO2.getList(sql,MainThread.user_id);
                        } catch (SQLException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException | InstantiationException ex) {
                            ex.printStackTrace();
                        }
                        if(!businessList.isEmpty()){
                            JOptionPane.showMessageDialog(null, "登录成功", "",JOptionPane.PLAIN_MESSAGE);
                            MainThread.business = true;
                            MainPanel.setVisible(false);
                            synchronized (Main.LOCK){
                                Main.LOCK.notify();
                            }
                        }
                        else{
                            managerDAO DAO3 = new managerDAO();
                            List<manager> managerList = null;
                            sql = "select * from manager where user_id = ?";
                            try {
                                managerList = DAO3.getList(sql,MainThread.user_id);
                            } catch (SQLException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException | InstantiationException ex) {
                                ex.printStackTrace();
                            }
                            if(!managerList.isEmpty()){
                                JOptionPane.showMessageDialog(null, "登录成功", "",JOptionPane.PLAIN_MESSAGE);
                                MainThread.manager = true;
                                MainPanel.setVisible(false);
                                synchronized (Main.LOCK){
                                    Main.LOCK.notify();
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
