package Node;

import java.util.Map;
import Exception.*;
import Compiler.*;

public interface Tree {

    //根据单词流生长语法树
    void grow(TokenList tokens) throws CompileException;

    //运行当前策略对应的语法树
    Integer run(Map<String, Integer> localVal) throws RunningException;

    //输出语法树
    void print(int deep);

}
