package Node;

import Exception.*;
import Compiler.TokenList;
import Compiler.Parser;

import java.util.Map;

import static Compiler.TokenJudge.isId;

@SuppressWarnings("all")
public class AssignTree implements Tree{
    private Leaf id;
    private ExpTree exp;

    public AssignTree(){}

    //根据单词流生长语法树的 AssignTree 的子树
    @Override
    public void grow(TokenList tokens) throws CompileException {
        //assign-stmt -> id is exp
        if(isId(tokens.watch())) id = new Leaf(tokens.read());
        else throw new CompileException("assign-stmt is not started with id");
        if(!tokens.read().equals("is"))
            throw new CompileException("assign-stmt format error");
        exp = new ExpTree();
        exp.grow(tokens);

    }

    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        localVal.put(id.getValue(), exp.run(localVal));
        return null;
    }

    //输出语法树 Assign 子树的单词流
    @Override
    public void print(int deep) {
        id.print(deep);
        Parser.printWord(deep,"is");
        exp.print(deep);
    }
}