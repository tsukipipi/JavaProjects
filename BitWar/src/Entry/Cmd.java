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
        else throw new CmdException("directory '" + cmd.get(1) + "' is not exist");
    }

    //列出已经编译好的语法树对应的文件名
    private void list() throws CmdException{
        if(cmd.size()!=1) throw new CmdException("wrong args for ls command");
        //输出programs保存的编译好的语法树对应的文件名
        GlobalValue.printAllPrograms();
    }

    //输出编译好的某一棵语法树
    private void show() throws CmdException{
        if(cmd.size()!=2) throw new CmdException("wrong args for show command");
        Parser.printTree(cmd.get(1),GlobalValue.getProgram(cmd.get(1)));
    }

    //单次运行某个策略(这个策略不包含对手的数据)
    private void run() throws CmdException, RunningException{
        if(cmd.size()!=2) throw new CmdException("wrong args for run command");
        String target = cmd.get(1);
        //判断是否存在这颗语法树
        if(!GlobalValue.hasProgram(target)) throw new CmdException("'"+target+"' is not loaded");
        System.out.println("result: "+runner.run(target));
    }

    //进行对战
    private void battle() throws CmdException, RunningException{
        //两个策略对战
        if(cmd.size()==4){
            //获取两个策略和对战次数
            String target1 = cmd.get(1);
            String target2 = cmd.get(2);
            String round = cmd.get(3);
            //对战前的一些检查
            if(!round.matches("[0-9]+"))
                throw new CmdException("'"+round+"' is not a number over zero");
            int round2 = Integer.parseInt(round);
            if(round2<=0)
                throw new CmdException("'" + round2 + "' is not a number over zero");
            if(!GlobalValue.hasProgram(target1))
                throw new CmdException("'" + target1 + "' is not loaded");
            if(!GlobalValue.hasProgram(target2))
                throw new CmdException("'" + target2 + "' is not loaded");
            //没有异常则进行两个策略的对战
            BattleEntry.battle(runner,target1,target2,round2,false);
        }
        //全部策略对战
        else if(cmd.size()==2){
            if(GlobalValue.getPrograms().size()<2)
                throw new CmdException("there are no enough programs to battle");
            String round = cmd.get(1);
            if(!round.matches("[0-9]+"))
                throw new CmdException("'"+round+"' is not a number over zero");
            int round2 = Integer.parseInt(round);
            if(round2<=0)
                throw new CmdException("'"+round2+"' is not a number over zero");
            //全部策略进行对战
            BattleEntry.battle(runner,round2);
        }
        //格式出错
        else throw new CmdException("wrong args for battle command");
    }
}
