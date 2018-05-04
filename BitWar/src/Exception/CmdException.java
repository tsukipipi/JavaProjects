package Exception;

public class CmdException  extends Exception {

    private static final String tips = "\n 输入格式出错！";

    public CmdException(String message){
        super("error: " + message + tips);
    }

}
