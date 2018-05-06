package Node;

import Exception.*;
import Compiler.TokenList;

import java.util.Map;

import static Compiler.TokenJudge.isLogic;

@SuppressWarnings("all")
public class ExpTree implements Tree{
    private LogicExpTree left;
    private Leaf logic;
    private LogicExpTree right;
    private String condition;

    public ExpTree(){}

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    //根据单词流生长语法树的 ExpTree 的子树
    @Override
    public void grow(TokenList tokens) throws CompileException {
        //exp -> logic-exp [logic logic-exp]
        left = new LogicExpTree();
        left.grow(tokens);
        //还有逻辑判断部分 and、or
        if(isLogic(tokens.watch())) {
            logic = new Leaf(tokens.read());
            setCondition("hasLogic");
            right = new LogicExpTree();
            right.grow(tokens);
        }
        else setCondition("noLogic");
    }

    //运行当前策略对应的语法树
    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        if(getCondition().equals("hasLogic")){
            if(logic.getValue().equals("and"))
                if(left.run(localVal)!=0 && right.run(localVal)!=0) return 1;
                else return 0;
            else if (logic.getValue().equals("or"))
                if(left.run(localVal)!=0 || right.run(localVal)!=0) return 1;
                else return 0;
            else throw new RunningException("exp grammar error");
        }
        else {
            return left.run(localVal);
        }
    }

    //输出语法树 exp 子树的单词流
    @Override
    public void print(int deep) {
        left.print(deep);
        //存在右侧的逻辑部分
        if(getCondition().equals("hasLogic")){
            logic.print(deep);
            right.print(deep);
        }
    }
}
