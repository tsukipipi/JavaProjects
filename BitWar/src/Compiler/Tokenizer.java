package Compiler;

import Exception.*;

import java.util.ArrayList;
import java.util.List;
import static Compiler.TokenJudge.*;

@SuppressWarnings("all")
public class Tokenizer {

    //根据输入的策略文件来获取单词(Token)序列
    public static TokenList getTokens(String code) throws CompileException{
        List<String> tokens = new ArrayList<>();
        String token = "";
        for(int i=0;i<code.length();i++){
            //判断当前字符是否为空格或者是否为运算符
            if((code.charAt(i) == ' '|| isOp(code.charAt(i)))){
                //判断是不是一个单词(Token)
                if(isToken(token)) {
                    tokens.add(token);
                    token = "";
                }
                //如果没有单词而是一个空串表明出错
                else if(!token.matches("[\\s]?")){
                    throw new CompileException(token + " isn't a token");
                }
            }
            //将不是运算符和空格的字符连接成为一个token
            else token += code.charAt(i);
            //如果是运算符直接将其作为一个token
            if(isOp(code.charAt(i))) tokens.add(String.valueOf(code.charAt(i)));
        }
        //最终返回得到的单词序列
        return new TokenList(tokens);
    }

    public static void showTokens(TokenList list){
        List<String> tokens = list.getTokens();
        for(String token:tokens){
            System.out.println(token);
        }
    }

}
