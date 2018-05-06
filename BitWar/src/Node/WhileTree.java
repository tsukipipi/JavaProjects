package Node;

import Exception.*;
import Compiler.TokenList;
import Compiler.Parser;
import java.util.Map;

@SuppressWarnings("all")
public class WhileTree implements Tree{
    private ExpTree exp;
    private StmtListTree stmtlist;

    //根据单词流生长语法树的 WhileTree 的子树
    @Override
    public void grow(TokenList tokens) throws CompileException {
        //while-stmt -> while exp do stmt-list endw
        if(!tokens.read().equals("while"))
            throw new CompileException("while-stmt is not started with 'while'");
        exp = new ExpTree();
        exp.grow(tokens);
        if(!tokens.read().equals("do"))
            throw new CompileException("without do part in while-stmt");
        stmtlist = new StmtListTree();
        stmtlist.grow(tokens);
        if(!tokens.read().equals("endw"))
            throw new CompileException("while-stmt is not finished by 'endw'");
    }

    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        while (exp.run(localVal)!=0){
            Integer val = stmtlist.run(localVal);
            if(val!=null) return val;
        }
        return null;
    }

    //输出语法树 While 子树的单词流
    @Override
    public void print(int deep) {
        Parser.printWord(deep,"while");
        exp.print(deep+1);
        Parser.printWord(deep+1,"do");
        stmtlist.print(deep+2);
        //表示 do 部分的结束
        Parser.printWord(deep+1,"-do");
        Parser.printWord(deep,"endw");
    }
}
