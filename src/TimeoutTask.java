import java.util.concurrent.Callable;

/**
 * Created by root on 7/27/15.
 */
public class TimeoutTask implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        return new Integer(0);
    }
}
