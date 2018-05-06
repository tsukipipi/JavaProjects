package Node;

import Exception.*;
import Compiler.TokenList;
import Compiler.Parser;
import Runtime.*;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class MyTree implements Tree{
    private ExpTree exp;

    public MyTree(){}

    //根据单词流生长语法树的 MyTree 的子树
    @Override
    public void grow(TokenList tokens) throws CompileException {
        //my -> my(exp)
        if(!tokens.read().equals("my"))
            throw new CompileException("my-stat is not started with 'my'");
        if(!tokens.read().equals("("))
            throw new CompileException("my format error");
        exp = new ExpTree();
        exp.grow(tokens);
        if(!tokens.read().equals(")"))
            throw new CompileException("my format error");
    }

    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        localVal.put("current", Program.MY_CURRENT);
        int arg = exp.run(localVal);
        int index = localVal.get("my");
        if(index==Program.FIRST_RUN){
            if (arg == 0) return ((List) GlobalValue.getGlobalVal("history1")).size();
            if (arg<0 || arg >= ((List)GlobalValue.getGlobalVal("history1")).size()){
                throw new RunningException("current index out of array 'history1'");
            }
            return (Integer) ((List)GlobalValue.getGlobalVal("history1")).get(arg);
        }
        if(index==Program.SECOND_RUN){
            if (arg == 0) return ((List) GlobalValue.getGlobalVal("history2")).size();
            if (arg<0 || arg >= ((List)GlobalValue.getGlobalVal("history2")).size()){
                throw new RunningException("current index out of array 'history2'");
            }
            return (Integer) ((List)GlobalValue.getGlobalVal("history2")).get(arg);
        }
        else {
            throw new RunningException("my-stmt cannot run in single mode");
        }
    }

    //输出语法树 My 子树的单词流
    @Override
    public void print(int deep) {
        Parser.printWord(deep,"my");
        exp.print(deep+1);
        //表示 my 部分的结束
        Parser.printWord(deep,"-my");
    }
}
