package Node;

import Exception.*;

import Compiler.Parser;
import Compiler.TokenList;
import Runtime.*;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class CurrentTree implements Tree{
    public CurrentTree(){}

    //根据单词流生长语法树的 CurrentTree 的子树
    @Override
    public void grow(TokenList tokens) throws CompileException {
        if(!tokens.read().equals("current"))
            throw new CompileException("current format error");
    }

    //运行当前策略对应的语法树
    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        int current = localVal.get("current");
        int order = localVal.get("my");
        //FIRST_RUN = 1
        //玩家1的回合
        if (order == Program.FIRST_RUN){
            //MY_CURRENT = 1
            //在玩家1的回合取得玩家1对战过的回合数
            if (current == Program.MY_CURRENT){
                return ((List)GlobalValue.getGlobalVal("history1")).size();
            }
            //OPPONENT_CURRENT = 2
            //在玩家1的回合取得玩家2对战过的回合数
            if (current == Program.OPPONENT_CURRENT){
                return ((List)GlobalValue.getGlobalVal("history2")).size();
            }
            throw new RunningException("current val is invaild");
        }
        //SECOND_RUN = 2
        //玩家2的回合
        if (order == Program.SECOND_RUN){
            //MY_CURRENT = 1
            //在玩家2的回合获取玩家2自身对战过的回合数
            if (current == Program.MY_CURRENT){
                return ((List)GlobalValue.getGlobalVal("history2")).size();
            }
            //OPPONENT_CURRENT = 2
            //在玩家2的回合获取玩家1对战过的回合数
            if (current==Program.OPPONENT_CURRENT){
                return ((List)GlobalValue.getGlobalVal("history1")).size();
            }
            throw new RunningException("current val is invaild");
        }
        else {
            throw new RunningException("my-stmt cannot run in single mode");
        }
    }

    //输出语法树的单词Current
    @Override
    public void print(int deep) {
        Parser.printWord(deep,"current");
    }
}
