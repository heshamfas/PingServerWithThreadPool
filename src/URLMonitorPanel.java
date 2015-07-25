import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Created by root on 7/23/15.
 */
public class URLMonitorPanel extends JPanel implements URLPingTask.URLUpdate {

    Timer timer;
    URL url;
    URLPingTask urlPingTask;
    JPanel status;
    JButton startButton, stopButton;
    public URLMonitorPanel(String url, Timer timer){
        setLayout(new BorderLayout());
        this.timer =timer;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        add(new JLabel(url),BorderLayout.CENTER);
        JPanel temp = new JPanel();
        status = new JPanel();
        status.setSize(20,20);
        temp.add(status);
        startButton = new JButton("start");
        startButton.setEnabled(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeTask();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
            }
        });
        stopButton = new JButton("stop");
        stopButton.setEnabled(true);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                urlPingTask.cancel();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
            }
        });
        temp.add(startButton);
        temp.add(stopButton);
        add(temp,BorderLayout.EAST);
        makeTask();
    }
    private void makeTask(){
        urlPingTask = new URLPingTask(url,this);
       timer.schedule(urlPingTask,0L, 5000L);
    }
    @Override
    public void isAlive(final boolean b) {
    SwingUtilities.invokeLater(new Runnable() {
    @Override
    public void run() {
        status.setBackground(b? Color.GREEN:Color.RED);
        status.repaint();
    }
            });
    }
}
