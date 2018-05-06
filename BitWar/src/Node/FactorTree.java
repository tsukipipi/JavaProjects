package Node;

import Exception.*;
import Compiler.TokenList;

import java.util.Map;

import static Compiler.TokenJudge.isConst;
import static Compiler.TokenJudge.isId;
import static Compiler.TokenJudge.isNum;

@SuppressWarnings("all")
public class FactorTree implements Tree{
    private ExpTree exp;
    private Leaf value;
    private RandomTree random;
    private ConstTree con;
    //记录是哪一种情况的子树
    private String condition;

    public FactorTree(){}

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    //根据单词流生长语法树的 FactorTree 的子树
    @Override
    public void grow(TokenList tokens) throws CompileException {
        //factor -> (exp) | num | id | random | const
        //(exp)
        if(tokens.watch().equals("(")){
            setCondition("exp");
            tokens.read();
            exp = new ExpTree();
            exp.grow(tokens);
            if(!tokens.read().equals(")"))
                throw new CompileException("factor format error");
        }
        // Id 或者 Num
        else if(isId(tokens.watch())||isNum(tokens.watch())){
            setCondition("value");
            value = new Leaf(tokens.read());
        }
        //random
        else if (tokens.watch().equals("random")){
            setCondition("random");
            random = new RandomTree();
            random.grow(tokens);
        }
        //const
        else if (isConst(tokens.watch())){
            setCondition("const");
            con = new ConstTree();
            con.grow(tokens);
        }
        else {
            throw new CompileException("'"+tokens.watch()+"' is not a factor");
        }
    }

    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        switch (getCondition()){
            case "exp": return exp.run(localVal);
            case "value": return value.run(localVal);
            case "random": return random.run(localVal);
            case "const": return con.run(localVal);
            default:
                throw new RunningException("factor grammar error");
        }
    }

    //输出语法树 Factor 子树的单词流
    @Override
    public void print(int deep) {
        switch (getCondition()){
            case "exp": exp.print(deep+1);break;
            case "value": value.print(deep);break;
            case "random": random.print(deep);break;
            case "const": con.print(deep);break;
            default:;
        }
    }
}
