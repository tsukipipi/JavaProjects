package Compiler;

import java.util.Arrays;
import java.util.List;

//关于Token的一些判断
public class TokenJudge {

    private static final List<String> limits
            = Arrays.asList("func","endf", "if","then","else",
            "endi","while","do","endw","is","return", "and",
            "or", "random", "current", "my", "opponent");

    private static final List<String> ops
            = Arrays.asList("+","-","*","/","=","<",">","(",")");

    public static boolean isNum(String token){
        return token.matches("[0-9]+");
    }

    public static boolean isId(String token){
        return !limits.contains(token) && token.matches("[a-zA-Z][a-zA-Z0-9]*");
    }

    public static boolean isLogic(String token){
        return token.equals("and")||token.equals("or");
    }

    public static boolean isComp(String token){
        return token.equals("<")||token.equals(">")||token.equals("=");
    }

    public static boolean isAddop(String token){
        return token.equals("+")||token.equals("-");
    }

    public static boolean isMulop(String token){
        return token.equals("*")||token.equals("/");
    }

    public static boolean isOp(char op){
        return ops.contains(String.valueOf(op));
    }

    public static boolean isConst(String token){
        return token.equals("current")||token.equals("my")||token.equals("opponent");
    }

    public static boolean isLimit(String token){
        return limits.contains(token);
    }

    //判断是不是一个单词(Token)
    public static boolean isToken(String token) {
        return isId(token)||isLimit(token)||isNum(token);
    }

}
