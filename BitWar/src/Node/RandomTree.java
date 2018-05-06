package Node;

import Exception.*;
import Compiler.TokenList;
import Compiler.Parser;

import java.util.Map;
import java.util.Random;

@SuppressWarnings("all")
public class RandomTree implements Tree{
    private ExpTree exp;

    public RandomTree(){}

    //根据单词流生长语法树的 RandomTree 的子树
    @Override
    public void grow(TokenList tokens) throws CompileException {
        //random -> random(exp)
        if(!tokens.read().equals("random"))
            throw new CompileException("random-stat is not started with 'random'");
        if(!tokens.read().equals("("))
            throw new CompileException("random format error");
        exp = new ExpTree();
        exp.grow(tokens);
        if(!tokens.read().equals(")"))
            throw new CompileException("random format error");
    }

    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        int max = exp.run(localVal);
        //求出一个随机值
        Random r = new Random();
        return r.nextInt(max);
    }

    //输出语法树 Random 子树的单词流
    @Override
    public void print(int deep) {
        Parser.printWord(deep,"random");
        exp.print(deep+1);
        //表示 random 部分的结束
        Parser.printWord(deep,"-random");
    }
}
