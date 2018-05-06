package Entry;

import Exception.*;
import Runtime.*;
import Compiler.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class Cmd {
    //选项可能由空格隔开（如：show t1.txt）
    private List<String> cmd;
    private static Runner runner;

    static {
        runner = new Runner();
    }

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
            if(runner.load("strategy"))
                System.out.println("successfully load strategies from default 'strategy'");
            else throw new CmdException("default directory 'strategy' is not exist");
        }
        //载入特定的文件
        else if(cmd.size()==2){
            if(runner.load("strategy/" + cmd.get(1)))
                System.out.println("successfully load strategy '"+cmd.get(1)+"'");
            else throw new CmdException("directory '"+cmd.get(1)+"' is not exist");
        }
        //目录不存在，抛出异常
        else throw new CmdException("目录 '" + cmd.get(1) + "' 不存在");
    }

    private void list() throws CmdException{
        if(cmd.size()!=1) throw new CmdException("wrong args for ls command");
        //输出programs保存的编译好的语法树对应的文件名
        GlobalValue.printAllPrograms();
    }

    private void show() throws CmdException{
        //输出编译好的某一棵语法树
        if(cmd.size()!=2) throw new CmdException("wrong args for show command");
        Parser.printTree(cmd.get(1),GlobalValue.getProgram(cmd.get(1)));
    }

    private void run() throws CmdException, RunningException{

    }

    private void battle() throws CmdException, RunningException{

    }
}
