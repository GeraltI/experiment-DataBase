import JDBC.jdbcDAO;
import JDBC.category.user;
import JDBC.categoryDAO.userDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class SignGUI {
    JPanel MainPanel;
    private JPasswordField passwordConfirmField;
    private JPasswordField passwordField;
    private JTextField userField;
    private JButton SignButton;

    private String user_name;
    private String user_password;
    private String user_password_confirm;

    List<user> userList;
    userDAO dao = new userDAO();
    public SignGUI(){
        SignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user_name = userField.getText();
                user_password = String.valueOf(passwordField.getPassword());
                user_password_confirm = String.valueOf(passwordConfirmField.getPassword());
                String sql = "select * from user where user_name = ?;";
                try {
                    userList = dao.getList(sql,user_name);
                } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException | NoSuchFieldException ex) {
                    ex.printStackTrace();
                }

                if(!user_password.equals(user_password_confirm)){
                    JOptionPane.showMessageDialog(null, "密码和确认密码不一致", "",JOptionPane.WARNING_MESSAGE);
                }
                else if(!userList.isEmpty()){
                    JOptionPane.showMessageDialog(null, "用户名已经被使用", "",JOptionPane.WARNING_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null, "注册成功", "",JOptionPane.PLAIN_MESSAGE);
                    sql = "insert into user values (?,?,?,?,?);";
                    try {
                        System.out.println(jdbcDAO.update(sql,null,user_name,user_password,true,LocalDateTime.now()) + " rows");
                    } catch (SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    sql = "select * from user where user_name = ?";
                    try {
                        userList = dao.getList(sql, user_name);
                    } catch (SQLException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException | InstantiationException ex) {
                        ex.printStackTrace();
                    }

                    user u = userList.get(0);
                    int user_id = u.getId();

                    sql = "insert into client values (?,?,?,?,?,?,?,?,?);";
                    try {
                        System.out.println(jdbcDAO.update(sql,user_id,user_name,null,user_name,0,null,null,null,null) + " rows");
                    } catch (SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }


                    MainPanel.setVisible(false);
                    synchronized (Main.LOCK){
                        Main.LOCK.notify();
                    }
                }
            }
        });
    }

}
