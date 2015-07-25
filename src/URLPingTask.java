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
        if(System.currentTimeMillis()-scheduledExecutionTime() > 5000){
            //let the next task do it
            return;
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(1000);
            httpURLConnection.setReadTimeout(1000);
            int code = httpURLConnection.getResponseCode();
            if(updater != null){
                updater.isAlive(true);
            }
        } catch (IOException e) {
            if(updater !=null){
                updater.isAlive(false);
            }
            e.printStackTrace();
        }
    }
}
