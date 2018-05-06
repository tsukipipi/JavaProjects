package Node;

import Exception.*;
import Compiler.TokenList;
import Compiler.Parser;

import java.util.Map;

@SuppressWarnings("all")
public class IfTree implements Tree{
    //if 语句的判断条件 exp
    private ExpTree ifPart;
    //if exp then 执行的动作
    private StmtListTree thenPart;
    //else 语句后面执行的动作
    private StmtListTree elsePart;
    //记录这个if else语句是否有else部分
    private String condition;

    public IfTree(){}

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    //根据单词流生长语法树的 IfTree 的子树
    @Override
    public void grow(TokenList tokens) throws CompileException {
        // if-stmt -> if exp then stmt-list [else stmt-list] endi
        // if 子树以if开头
        if(!tokens.read().equals("if"))
            throw new CompileException("if-stmt is not started with if");
        ifPart = new ExpTree();
        ifPart.grow(tokens);
        //if exp then 执行的动作
        if(tokens.read().equals("then")){
            thenPart = new StmtListTree();
            thenPart.grow(tokens);
            setCondition("then");
            //else 语句后面执行的动作，不一定有else部分
            if(tokens.watch().equals("else")){
                tokens.read();
                elsePart = new StmtListTree();
                elsePart.grow(tokens);
                setCondition("else");
            }
        }
        else throw new CompileException("without then part in if-stmt");
        //以 endi 结尾
        if(!tokens.read().equals("endi"))
            throw new CompileException("if-stmt is not finished by endi");
    }

    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        if(ifPart.run(localVal)!=0){

            return thenPart.run(localVal);
        }
        else if(getCondition().equals("else")){
            return elsePart.run(localVal);
        }
        return null;
    }

    //输出语法树 if 子树的单词流
    @Override
    public void print(int deep) {
        Parser.printWord(deep,"if");
        ifPart.print(deep+1);
        Parser.printWord(deep+1,"then");
        thenPart.print(deep+2);
        //表示 then 部分的结束
        Parser.printWord(deep+1,"-then");
        if(getCondition().equals("else")){
            Parser.printWord(deep+1,"else");
            elsePart.print(deep+2);
            //表示 else 部分的结束
            Parser.printWord(deep+1,"-else");
        }
        Parser.printWord(deep,"endi");
    }
}
