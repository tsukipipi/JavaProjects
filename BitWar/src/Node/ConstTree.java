package Node;

import Exception.*;
import Compiler.TokenList;

import java.util.Map;

@SuppressWarnings("all")
public class ConstTree implements Tree{
    private CurrentTree current;
    private MyTree my;
    private OpponentTree opponent;
    private String condition;

    public ConstTree(){}

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    //根据单词流生长语法树的 ConstTree 的子树
    @Override
    public void grow(TokenList tokens) throws CompileException {
        //const -> current | my | opponent
        //current
        if(tokens.watch().equals("current")){
            setCondition("current");
            current = new CurrentTree();
            current.grow(tokens);
        }
        //my
        else if(tokens.watch().equals("my")){
            setCondition("my");
            my = new MyTree();
            my.grow(tokens);
        }
        //opponent
        else if(tokens.watch().equals("opponent")){
            setCondition("opponent");
            opponent = new OpponentTree();
            opponent.grow(tokens);
        }
        else {
            throw new CompileException("'"+tokens.watch()+"' is not a const value");
        }
    }

    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        if(getCondition().equals("current")) return current.run(localVal);
        if(getCondition().equals("my")) return my.run(localVal);
        if(getCondition().equals("opponent")) return opponent.run(localVal);
        else throw new RunningException("const grammar error");
    }

    //输出语法树 Const 子树的单词流
    @Override
    public void print(int deep) {
        if(getCondition().equals("current")) current.print(deep);
        if(getCondition().equals("my")) my.print(deep);
        if(getCondition().equals("opponent")) opponent.print(deep);
    }
}
