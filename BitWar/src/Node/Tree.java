package Node;

import java.util.Map;
import Exception.*;

public interface Tree {

    void grow(Compiler.TokenList tokens) throws CompileException;

    Integer run(Map<String, Integer> localVal) throws RunningException;

    void print(int deep);

}
