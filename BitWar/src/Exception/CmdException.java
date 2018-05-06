package Exception;

public class CmdException  extends Exception {

    private static final String tips = "\n error input format！";

    public CmdException(String message){
        super("error: " + message + tips);
    }

}
