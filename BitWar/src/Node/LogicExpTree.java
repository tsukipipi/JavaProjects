package Node;

import Exception.*;
import Compiler.TokenList;

import java.util.Map;

import static Compiler.TokenJudge.isComp;

@SuppressWarnings("all")
public class LogicExpTree implements Tree{
    //逻辑表达式左边部分
    private SimpleExpTree left;
    //逻辑符号
    private Leaf comp;
    //逻辑表达式右边部分
    private SimpleExpTree right;
    //记录是否存在逻辑符号及右边部分
    private String condition;

    public LogicExpTree(){}

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    //根据单词流生长语法树的 LogicExpTree 的子树
    @Override
    public void grow(TokenList tokens) throws CompileException {
        //logic-exp -> simple-exp [comp simple-exp]
        //逻辑表达式左边部分
        left = new SimpleExpTree();
        left.grow(tokens);
        //判断是否存在逻辑判断符号
        if(isComp(tokens.watch())){
            comp = new Leaf(tokens.read());
            setCondition("hasComp");
            //逻辑表达式右边部分
            right = new SimpleExpTree();
            right.grow(tokens);
        }
        else setCondition("noComp");
    }

    //运行当前策略对应的语法树
    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        if(getCondition().equals("hasComp")){
            if(comp.getValue().equals("<")){
                if(left.run(localVal)<right.run(localVal))
                    return 1;
                else return 0;
            }
            if(comp.getValue().equals(">")){
                if(left.run(localVal)>right.run(localVal))
                    return 1;
                else return 0;
            }
            if(comp.getValue().equals("=")){
                if(left.run(localVal)==right.run(localVal))
                    return 1;
                else return 0;
            }
        }
        if(getCondition().equals("noComp")){
            return left.run(localVal);
        }
        else throw new RunningException("logic-exp grammar error");
    }

    //输出语法树 LogicExp 子树的单词流
    @Override
    public void print(int deep) {
        left.print(deep);
        if(getCondition().equals("hasComp")){
            comp.print(deep);
            right.print(deep);
        }
    }
}

