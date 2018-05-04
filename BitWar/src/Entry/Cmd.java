package Entry;

import Exception.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cmd {
    //选项可能由空格隔开（如：show t1.txt）
    private List<String> cmd;

    public Cmd(String cmd){
        //根据空符号分割开
        String [] list = cmd.split("\\s");
        this.cmd = new ArrayList<String>(Arrays.asList(list));
    }

    //根据不同命令运行程序
    public void execute() throws CompileException, CmdException, RunningException{
        switch (cmd.get(0)){
            case "load":load();break;
            case "list":list();break;
            case "show":show();break;
            case "run":run();break;
            //battle t1.txt t2.txt 10
            case "battle":battle();break;
            case "exit":break;
            case "":break;
            default:throw new CmdException("'"+cmd.get(0)+"' is not a command");
        }
    }

    //载入策略文件生成相应的语法树
    private void load() throws CompileException, CmdException{
        //load：载入全部策略文件
        if(cmd.size()==1){

        }
        //载入特定的文件
        else if(cmd.size()==2){

        }
        //目录不存在，抛出异常
        else throw new CmdException("目录 '" + cmd.get(1) + "' 不存在");
    }

    private void list() throws CmdException{

    }

    private void show() throws CmdException{

    }

    private void run() throws CmdException, RunningException{

    }

    private void battle() throws CmdException, RunningException{

    }
}
