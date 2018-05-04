package Exception;

public class RunningException  extends Exception {

    public RunningException(String message) {
        super("runtime error: " + message);
    }

}
