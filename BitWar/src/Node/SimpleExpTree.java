package Node;

import Exception.*;
import Compiler.TokenList;
import Compiler.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Compiler.TokenJudge.isAddop;

@SuppressWarnings("all")
public class SimpleExpTree implements Tree{
    //保存由 + - 符号连接的表达式子树
    private List<TermTree> terms;
    //保存表达式的 + - 符号
    private List<String> addops;

    public SimpleExpTree(){
        terms = new ArrayList<>();
        addops = new ArrayList<>();
    }

    //根据单词流生长语法树的 SimpleExpTree 的子树
    @Override
    public void grow(TokenList tokens) throws CompileException {
        //simple-exp -> term {addop term}
        TermTree tree = new TermTree();
        tree.grow(tokens);
        terms.add(tree);
        //表达式存在 + - 符号
        while (isAddop(tokens.watch())){
            addops.add(tokens.read());
            tree = new TermTree();
            tree.grow(tokens);
            terms.add(tree);
        }
    }

    //运行当前策略对应的语法树
    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        int result = terms.get(0).run(localVal);
        for(int i=0;i<addops.size();i++){
            if(addops.get(i).equals("+"))
                result += terms.get(i+1).run(localVal);
            else if(addops.get(i).equals("-"))
                result -= terms.get(i+1).run(localVal);
            else throw new RunningException("simple-exp grammar error");
        }
        return result;
    }

    //输出语法树 SimpleExp 子树的单词流
    @Override
    public void print(int deep) {
        terms.get(0).print(deep);
        for(int i=0;i<addops.size();i++){
            Parser.printWord(deep,addops.get(i));
            terms.get(i+1).print(deep);
        }
    }
}
