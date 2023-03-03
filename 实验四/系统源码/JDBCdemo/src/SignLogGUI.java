
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SignLogGUI {
    JPanel MainPanel;
    private JButton LogButton;
    private JButton SignButton;

    public SignLogGUI() {
        LogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainThread.log = true;
                MainPanel.setVisible(false);
                synchronized (Main.LOCK){
                    Main.LOCK.notify();
                }
            }
        });

        SignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainThread.sign = true;
                MainPanel.setVisible(false);
                synchronized (Main.LOCK){
                    Main.LOCK.notify();
                }
            }
        });


    }
}
