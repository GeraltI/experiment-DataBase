import JDBC.categoryDAO.clientDAO;
import JDBC.jdbcDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class ClientMessegeGUI {
    public JPanel MessegePanel;
    private JButton ChangeButton;
    private JLabel userNameLabel;
    private JTextField NameField;
    private JComboBox SexBox;
    private JComboBox YearBox;
    private JComboBox MonthBox;
    private JComboBox DayBox;
    private JTextField HobbyField;
    private JTextField AddressField;
    private JTextField ContactField;

    private clientDAO dao = new clientDAO();

    public ClientMessegeGUI() throws SQLException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientName = NameField.getText();
                int clientSex = SexBox.getSelectedIndex();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,YearBox.getSelectedIndex() + 1900);
                calendar.set(Calendar.MONTH,MonthBox.getSelectedIndex());
                calendar.set(Calendar.DATE,DayBox.getSelectedIndex() + 1);
                Date clientBirth = calendar.getTime();
                String clientHobby = HobbyField.getText();
                String clientAddress = AddressField.getText();
                String clientContact = ContactField.getText();
                Object[] options ={ "是", "否" };  //自定义按钮上的文字
                int m = JOptionPane.showOptionDialog(null, "确定要更改吗？", "提示",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                boolean change = (m == 0);
                if(change){
                    String sql = "update client set client_name = ? , client_sex = ? , client_birth = ? , client_hobby = ? , client_address = ? , client_contact = ? where user_id = ? ;";
                    try {
                        System.out.println(jdbcDAO.update(sql,clientName,clientSex,clientBirth,clientHobby,clientAddress,clientContact,MainThread.user_id) + " rows");
                    } catch (SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        //用户名
        userNameLabel.setText(ClientGUI.userName);
        //昵称
        NameField.setText(ClientGUI.clientName);
        //性别
        if(ClientGUI.clientSex == 0){
            SexBox.setSelectedItem("无性别");
        }
        if(ClientGUI.clientSex == 1){
            SexBox.setSelectedItem("男");
        }
        if(ClientGUI.clientSex == 2){
            SexBox.setSelectedItem("女");
        }
        //出生年
        YearBox.setSelectedIndex(ClientGUI.clientBirthYear - 1900);
        //出生月
        MonthBox.setSelectedIndex(ClientGUI.clientBirthMonth - 1);
        //出生日
        DayBox.setSelectedIndex(ClientGUI.clientBirthDay - 1);
        //爱好
        if(ClientGUI.clientHobby != null){
            HobbyField.setText(ClientGUI.clientHobby);
        }
        if(ClientGUI.clientAddress != null){
            AddressField.setText(ClientGUI.clientAddress);
        }
        if(ClientGUI.clientContact != null){
            ContactField.setText(ClientGUI.clientContact);
        }

    }
}
