package Compiler;

import java.util.List;

@SuppressWarnings("all")
public class TokenList {

    //Token 的序号
    private static int tokenIndex;
    //存放每一个 Token
    private List<String> tokens;

    public TokenList(){}

    public TokenList(List<String> tokens) {
        this.tokens = tokens;
    }

    public boolean overflow(){
        return tokenIndex >= tokens.size();
    }

    //初始化Token的序号从0开始
    public static void init(){
        tokenIndex = 0;
    }

    //读取下一个单词
    public String read(){
        return overflow()?null:tokens.get(tokenIndex++);
    }

    public String watch(){
        return overflow()?null:tokens.get(tokenIndex);
    }

    public static int getTokenIndex() {
        return tokenIndex;
    }

    public List<String> getTokens() {
        return tokens;
    }

}
