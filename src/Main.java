import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;

public class Main {

    public static void main(String[] args) throws MalformedURLException {

        //System.out.println("Hello World!");
        JFrame jFrame = new JFrame("URL Monitor");
        Container c = jFrame.getContentPane();
        c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));
        Timer timer = new Timer();
        for (int i = 0; i< args.length; i++){
             c.add(new URLMonitorPanel(args[i],timer));
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