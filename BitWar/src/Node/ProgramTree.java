package Node;

import java.util.Map;
import Exception.*;
import Compiler.*;
import static Compiler.TokenJudge.isId;

public class ProgramTree  implements Tree {

    private Leaf name;
    private StmtListTree stmtList;

    public ProgramTree() {}

    //从树根开始根据单词流生长语法树
    @Override
    public void grow(TokenList tokens) throws CompileException{
        //句子以 func 为开始符号
        if(!tokens.read().equals("func")) throw new CompileException("not start with 'func'");
        //tokens.read() 读取下一个单词
        String value = tokens.read();
        //判断是不是一个 id
        if(isId(value)) name = new Leaf(value);
        else throw new CompileException("function name is not an id");
        //根据中间的 stmtlist 单词流继续生长语法树
        stmtList = new StmtListTree();
        stmtList.grow(tokens);
        //句子应该以 endf 结尾
        if(!tokens.read().equals("endf")) throw new CompileException("function is not finished by 'endf'");
    }

    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        return stmtList.run(localVal);
    }

    @Override
    public void print(int deep) {
        Compiler.Parser.printWord(deep, "func " + name.getValue());
        stmtList.print(deep+1);
        Compiler.Parser.printWord(deep, "endf");
    }

}
