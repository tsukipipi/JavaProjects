package Runtime;

import Node.ProgramTree;
import Exception.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class Program {
    //语法树
    private ProgramTree programTree;
    private Map<String, Integer> localVal;
    //单人模式(不需要对手的数据即可运行)
    public static final int SINGLE_RUN = 0;
    //自己的回合
    public static final int FIRST_RUN = 1;
    //对手的回合
    public static final int SECOND_RUN = 2;
    //要取得自己的历史回合数
    public static final int MY_CURRENT = 1;
    //要取得对手的历史回合数
    public static final int OPPONENT_CURRENT = 2;

    //构造函数
    public Program(ProgramTree programTree) {
        this.programTree = programTree;
        //保存当前进行对战的玩家信息
        this.localVal = new HashMap<>();
    }

    //运行当前策略
    public Integer run(int order) throws RunningException{
        clearLocalVal();
        setLocalVal("my", order);
        setLocalVal("current", MY_CURRENT);
        return programTree.run(this.localVal);
    }

    //single_run 单次运行某一棵语法树
    public Integer run() throws RunningException{
        return run(SINGLE_RUN);
    }

    //输出语法树
    public void printTree(){
        programTree.print(0);
    }

    public Map<String, Integer> getLocalVal() {
        return localVal;
    }

    public void setLocalVal(String id, Integer value) {
        this.localVal.put(id, value);
    }

    public void clearLocalVal(){
        this.localVal.clear();
    }

}
