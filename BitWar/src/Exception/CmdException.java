package Exception;

public class CmdException  extends Exception {

    public CmdException(String message){
        super("error input format: " + message);
    }

}
