import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.concurrent.*;

/**
 * Created by root on 7/23/15.
 */
public class URLMonitorPanel extends JPanel implements URLPingTask.URLUpdate {

     Future<Integer> futureTaskResult;
    static volatile boolean done = false;
    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    ScheduledFuture scheduledFuture;
    URL url;
    URLPingTask urlPingTask;
    JPanel status;
    JButton startButton, stopButton;

    public URLMonitorPanel(String url, ScheduledThreadPoolExecutor se,Future<Integer> future)
    throws MalformedURLException{
        setLayout(new BorderLayout());
        this.futureTaskResult = future;
        this.scheduledThreadPoolExecutor =se;
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
               scheduledFuture.cancel(true);
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
        System.out.println("makeTask");
        urlPingTask = new URLPingTask(url,this);
        scheduledFuture = scheduledThreadPoolExecutor.scheduleAtFixedRate( urlPingTask, 0L, 5L, TimeUnit.SECONDS);
    }
    @Override
    public void isAlive(final boolean b) {

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                   // checkLicense();
                    if(done){
                        scheduledFuture.cancel(true);
                        startButton.setEnabled(false);
                        stopButton.setEnabled(false);
                        return;
                    }
                }
            });
        }catch (Exception ex){}
    }

    private void checkLicense(){
        if(done) return;
        try
        {
            Integer i = futureTaskResult.get(1000L, TimeUnit.MILLISECONDS);
            //if we got a result, we know that the license has expired
            JOptionPane.showMessageDialog(null, "Evaluation time period has expired", "Expired",JOptionPane.INFORMATION_MESSAGE);
            done = true;
        }catch (InterruptedException interruptedEx){
             } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
