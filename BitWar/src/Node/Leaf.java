package Node;

import Exception.*;
import Compiler.TokenList;
import Compiler.Parser;

import java.util.Map;

import static Compiler.TokenJudge.isId;
import static Compiler.TokenJudge.isNum;

@SuppressWarnings("all")
public class Leaf implements Tree{
    //叶子上的值
    private String value;
    private String condition;

    public Leaf(){setValue(null);}

    public Leaf(String value) throws CompileException{
        setValue(value);
        grow(null);
    }

    //获得叶子的值 id / num
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    //根据单词流来确定叶子的值是 id 或者 num
    @Override
    public void grow(TokenList tokens) throws CompileException {
        if(isId(getValue())) setCondition("id");
        else if(isNum(getValue())) setCondition("num");
    }

    //运行当前策略对应的语法树的叶子
    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        switch (getCondition()){
            case "id":
                if(localVal.containsKey(getValue())) return localVal.get(getValue());
                else throw new RunningException("id '"+getValue()+"' is not defined");
            case "num":
                return Integer.parseInt(getValue());
            default: throw new RunningException("this leaf can't run");
        }
    }

    //输出语法树 Leaf 上的单词
    @Override
    public void print(int deep) {
        Parser.printWord(deep,getValue());
    }
}
