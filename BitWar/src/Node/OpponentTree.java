package Node;

import Exception.*;
import Compiler.TokenList;
import Compiler.Parser;
import Runtime.*;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class OpponentTree implements Tree{
    //opponent(exp)
    private ExpTree exp;

    public OpponentTree(){}

    //根据单词流生长语法树的 OpponentTree 的子树
    @Override
    public void grow(TokenList tokens) throws CompileException {
        //opponent -> opponent(exp)
        if(!tokens.read().equals("opponent"))
            throw new CompileException("opponent is not started with 'random'");
        if(!tokens.read().equals("("))
            throw new CompileException("opponent format error");
        exp = new ExpTree();
        exp.grow(tokens);
        if(!tokens.read().equals(")"))
            throw new CompileException("opponent format error");
    }

    //运行当前策略对应的语法树
    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        //OPPONENT_CURRENT = 2
        //更新 current 的值
        localVal.put("current", Program.OPPONENT_CURRENT);
        int arg = exp.run(localVal);
        int index = localVal.get("my");
        //FIRST_RUN = 1
        //玩家1的回合
        if(index == Program.FIRST_RUN){
            //arg = 0 获取玩家2的对战轮数
            if (arg == 0) return ((List) GlobalValue.getGlobalVal("history2")).size();
            if (arg < 0 || arg >= ((List) GlobalValue.getGlobalVal("history2")).size()){
                throw new RunningException("current index out of array 'history2'");
            }
            return (Integer) ((List) GlobalValue.getGlobalVal("history2")).get(arg);
        }
        //SECOND_RUN = 2
        //玩家2的回合
        if(index == Program.SECOND_RUN){
            //arg = 0 获取玩家1的对战轮数
            if (arg == 0) return ((List) GlobalValue.getGlobalVal("history1")).size();
            if (arg < 0 || arg >= ((List) GlobalValue.getGlobalVal("history1")).size()){
                throw new RunningException("current index out of array 'history1'");
            }
            return (Integer) ((List) GlobalValue.getGlobalVal("history1")).get(arg);
        }
        else {
            throw new RunningException("my-stmt cannot run in single mode");
        }
    }

    //输出语法树 Opponent 子树的单词流
    @Override
    public void print(int deep) {
        Parser.printWord(deep,"opponent");
        exp.print(deep+1);
        //表示 opponent 部分的结束
        Parser.printWord(deep,"-opponent");
    }
}
