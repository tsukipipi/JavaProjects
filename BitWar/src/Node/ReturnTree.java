package Node;

import Exception.*;
import Compiler.TokenList;
import Compiler.Parser;

import java.util.Map;

@SuppressWarnings("all")
public class ReturnTree implements Tree{
    private ExpTree exp;

    public ReturnTree(){}

    //根据单词流生长语法树的 ReturnTree 的子树
    @Override
    public void grow(TokenList tokens) throws CompileException {
        //return-stmt -> return exp
        if(!tokens.read().equals("return"))
            throw new CompileException("return-stmt is not started by 'return'");
        exp = new ExpTree();
        exp.grow(tokens);
    }

    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        return exp.run(localVal);
    }

    //输出语法树 Return 子树的单词流
    @Override
    public void print(int deep) {
        Parser.printWord(deep,"return");
        exp.print(deep+1);
        //表示 return 部分的结束
        Parser.printWord(deep,"-return");
    }
}
