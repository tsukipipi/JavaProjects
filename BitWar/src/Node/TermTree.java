package Node;

import Exception.*;
import Compiler.TokenList;
import Compiler.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Compiler.TokenJudge.isMulop;

@SuppressWarnings("all")
public class TermTree implements Tree{
    //保存由 * / 符号连接的表达式子树
    private List<FactorTree> factors;
    //保存表达式的 * / 符号
    private List<String> mulops;

    public TermTree(){
        factors = new ArrayList<>();
        mulops = new ArrayList<>();
    }

    //根据单词流生长语法树的 Term 的子树
    @Override
    public void grow(TokenList tokens) throws CompileException {
        //term -> factor {mulop factor}
        FactorTree tree = new FactorTree();
        tree.grow(tokens);
        factors.add(tree);
        //判断表达式是否有 * / 符号
        while (isMulop(tokens.watch())){
            mulops.add(tokens.read());
            tree = new FactorTree();
            tree.grow(tokens);
            factors.add(tree);
        }
    }

    //运行当前策略对应的语法树
    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        int result = factors.get(0).run(localVal);
        for(int i=0;i<mulops.size();i++){
            if(mulops.get(i).equals("*"))
                result *= factors.get(i+1).run(localVal);
            else if(mulops.get(i).equals("/")){
                int val = factors.get(i+1).run(localVal);
                if (val == 0) throw new RunningException("0 cannot follow '/'");
                result /= val;
            }
            else throw new RunningException("term grammar error");
        }
        return result;
    }

    //输出语法树 Term 子树的单词流
    @Override
    public void print(int deep) {
        factors.get(0).print(deep);
        for(int i=0;i<mulops.size();i++){
            Parser.printWord(deep,mulops.get(i));
            factors.get(i+1).print(deep);
        }
    }
}
