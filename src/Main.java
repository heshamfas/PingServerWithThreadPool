import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws MalformedURLException {

        //System.out.println("Hello World!");
        Future<Integer> futureTaskResult;
        JFrame jFrame = new JFrame("URL Monitor");
        Container c = jFrame.getContentPane();
        c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));
     /*   Timer timer = new Timer();*/
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(args.length + 1 / 2);
        TimeoutTask timeoutTask = new TimeoutTask();
        futureTaskResult = scheduledThreadPoolExecutor.schedule(timeoutTask,120, TimeUnit.SECONDS);
        for (int i = 0; i< args.length; i++){
             c.add(new URLMonitorPanel(args[i],scheduledThreadPoolExecutor, futureTaskResult));
                    }
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
