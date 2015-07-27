import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimerTask;

/**
 * Created by root on 7/23/15.
 */
public class URLPingTask extends TimerTask{

    public interface URLUpdate{
        public void isAlive(boolean b);
    }
    URL url;
    URLUpdate updater;

    public URLPingTask(URL url){
        this(url, null);
    }
    public URLPingTask(URL url, URLUpdate urlUpdate){
        this.url = url;
        updater= urlUpdate;

    }
    @Override
    public void run() {
        if(false/*System.currentTimeMillis()-scheduledExecutionTime() > 5000*/){
            //let the next task do it
            System.out.println("cannot run");
            return;
        }
        try {
            System.out.println("calling httpURLConnection :" + Thread.currentThread().getName());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            int code = httpURLConnection.getResponseCode();
            if(updater != null){
                updater.isAlive(true);
            }
        } catch (IOException e) {
            if(updater !=null){
                updater.isAlive(false);
                System.out.println("io exception url could not be found:" + Thread.currentThread().getName());
            }

        }
    }
}
