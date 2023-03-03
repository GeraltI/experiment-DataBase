import javax.swing.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final Main LOCK = new Main();

    public static final int WINDOW_WIDTH = 1100;
    public static final int WINDOW_HEIGHT = 800;

    public static void main(String[] args) {

        MainThread mainThread = new MainThread();
        Runnable r = () -> {
            try {
                mainThread.run();
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        // 启动线程
        new Thread(r, "jdbcDemo").start();
    }

}
