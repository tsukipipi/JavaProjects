package Node;

import java.util.Map;
import Exception.*;
import Compiler.*;

public interface Tree {

    void grow(TokenList tokens) throws CompileException;

    Integer run(Map<String, Integer> localVal) throws RunningException;

    void print(int deep);

}
