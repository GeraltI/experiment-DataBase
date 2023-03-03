import JDBC.category.client;
import JDBC.categoryDAO.clientDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


public class ClientGUI {

    public JPanel MainPanel;
    private JButton MessegeButton;
    private JButton OrderButton;
    private JButton OrdersButton;
    public JPanel TopPanel;
    public JPanel BottonPanel;

    private client c;
    private List<client> clientList;
    private clientDAO dao = new clientDAO();

    public static String userName;
    public static int clientId;
    public static String clientName;
    public static int clientSex;
    public static int clientBirthYear;
    public static int clientBirthMonth;
    public static int clientBirthDay;
    public static String clientHobby;
    public static String clientAddress;
    public static String clientContact;

    public static int orderFoodRestaurant_id;

    private void ClientMessegeInit(){
        String sql = "select * from client where user_id = ?;";
        try {
            clientList = dao.getList(sql,MainThread.user_id);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
        c = clientList.get(0);
        //用户名
        userName = c.getUser_name();
        //客户id
        clientId = c.getClient_id();
        //昵称
        clientName = c.getClient_name();
        //性别
        clientSex = c.getClient_sex();
        //出生年
        clientBirthYear = Integer.parseInt(c.getClient_birth().toString().split("-")[0]);
        //出生月
        clientBirthMonth =Integer.parseInt(c.getClient_birth().toString().split("-")[1]);
        //出生日
        clientBirthDay = Integer.parseInt(c.getClient_birth().toString().split("-")[2]);
        //爱好
        if(c.getClient_hobby() != null){
            clientHobby = c.getClient_hobby();
        }
        if(c.getClient_address() != null){
            clientAddress = c.getClient_address();
        }
        if(c.getClient_contact() != null){
            clientContact = c.getClient_contact();
        }
    }


    public ClientGUI() throws SQLException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClientMessegeInit();
        MessegeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ClientMessegeInit();

                BottonPanel.removeAll();
                try {
                    ClientMessegeGUI messegeGUI = new ClientMessegeGUI();
                    BottonPanel.setLayout(new BorderLayout());
                    BottonPanel.add(messegeGUI.MessegePanel,BorderLayout.CENTER);
                    BottonPanel.validate();
                    BottonPanel.setVisible(true);

                } catch (SQLException | NoSuchFieldException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });

        OrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                BottonPanel.removeAll();
                try {
                    ClientFindRestaurantGUI clientFindRestaurantGUI = new ClientFindRestaurantGUI(BottonPanel);
                    BottonPanel.setLayout(new BorderLayout());
                    BottonPanel.add(clientFindRestaurantGUI.MainPanel,BorderLayout.CENTER);
                    BottonPanel.validate();
                    BottonPanel.setVisible(true);

                } catch (SQLException | NoSuchFieldException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }

            }
        });

        OrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BottonPanel.removeAll();
                ClientOrderGUI clientOrderGUI = null;
                try {
                    clientOrderGUI = new ClientOrderGUI();
                } catch (SQLException | NoSuchFieldException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
                BottonPanel.setLayout(new BorderLayout());
                BottonPanel.add(clientOrderGUI.MainPanel,BorderLayout.CENTER);
                BottonPanel.validate();
                BottonPanel.setVisible(true);

            }
        });


    }

}
