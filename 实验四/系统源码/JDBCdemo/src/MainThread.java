import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainThread implements Runnable{
    public static boolean sign = false;
    public static boolean log = false;
    public static boolean client = false;
    public static boolean business = false;
    public static boolean manager = false;
    public static int user_id;

    private JFrame frame;
    @Override
    public void run() {
        SignLogGUI signlogGUI = new SignLogGUI();
        frame.add(signlogGUI.MainPanel);
        frame.setVisible(true);

        synchronized (Main.LOCK){
            while(signlogGUI.MainPanel.isVisible()){
                try{
                    Main.LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        frame.remove(signlogGUI.MainPanel);

        if(sign){
            SignGUI signGUI = new SignGUI();
            frame.add(signGUI.MainPanel);
            frame.setVisible(true);
            synchronized (Main.LOCK){
                while(signGUI.MainPanel.isVisible()){
                    try{
                        Main.LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            frame.remove(signGUI.MainPanel);
        }

        else if(log){
            LogGUI logGUI = new LogGUI();
            frame.add(logGUI.MainPanel);
            frame.setVisible(true);
            synchronized (Main.LOCK){
                while(logGUI.MainPanel.isVisible()){
                    try{
                        Main.LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            frame.remove(logGUI.MainPanel);
        }

        if(client){

            frame.setTitle("网上订单客户系统");
            ClientGUI clientGUI;
            try {
                clientGUI = new ClientGUI();
                frame.add(clientGUI.MainPanel);
                frame.setVisible(true);
                synchronized (Main.LOCK){
                    while(clientGUI.MainPanel.isVisible()){
                        try{
                            Main.LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (SQLException | NoSuchFieldException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        else if(business){
            frame.setTitle("网上订单商家系统");
            BusinessGUI businessGUI;
            try {
                businessGUI = new BusinessGUI();
                frame.add(businessGUI.MainPanel);
                frame.setVisible(true);
                synchronized (Main.LOCK){
                    while(businessGUI.MainPanel.isVisible()){
                        try{
                            Main.LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (SQLException | NoSuchFieldException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        else if(manager){
            frame.setTitle("网上订单管理系统");
        }





    }

    public MainThread(){

        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("网上餐厅系统");
        frame.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - Main.WINDOW_WIDTH) / 2, ((int) screenSize.getHeight() - Main.WINDOW_HEIGHT) / 2,
                Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
