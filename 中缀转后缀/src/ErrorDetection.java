package Expression;


import static Expression.Postfix.isDigit;
import static Expression.Postfix.isOpChar;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ErrorDetection {

	//不带括号的表达式，用于分析是否符合四则运算的规则
    String withoutBrackets;
    
    
    //判断表达式的括号是否匹配并剔除其中的括号
    boolean matchBrackets(String expression){
        
        withoutBrackets = "";
        
        //存放左括号的栈
        Stack sbra = new Stack();
        //括号数目
        int bracketNum = 0;
        //存放表达式的当前符号
        char ch;
        //标识表达式是否有错误
        boolean flag = true;
        
        for(int i = 0; i <expression.length(); i++){
            //左括号压栈
            ch = expression.charAt(i);
            if(ch == '('){
                bracketNum++;
                sbra.push(bracketNum);
            }
            //右括号，左括号出栈
            else if(ch == ')'){
                bracketNum++;
                //栈中不为空
                if(sbra.empty() != true)sbra.pop();
                //右括号多余
                else {
                    flag = false;
                    System.out.println("括号冗余，第" + bracketNum + "个括号多余");
                }
            }
            //表达式中非()的符号
            else withoutBrackets = withoutBrackets + ch;
        }
        //多余左括号
        while(sbra.empty() != true){
            flag = false;
            System.out.println("括号冗余，第" + sbra.peek() + "个括号多余");
            sbra.pop();
        }
        return flag;
    }
    
    
    //判断是否符合算数表达式的规则
    boolean isExpression(){
        //浮点数的正则表达式：^(-?\\d+)(\\.\\d+)?$    ^ 匹配开始位置    $ 匹配结束位置
        //四则运算的正则表达式：(-?\\d+)(\\.\\d+)?((\\+|\\-|\\*|\\/)(-?\\d+)(\\.\\d+)?)*
        Pattern p = Pattern.compile("(-?\\d+)(\\.\\d+)?((\\+|\\-|\\*|\\/)(-?\\d+)(\\.\\d+)?)*");
        Matcher m = p.matcher(withoutBrackets);
        //不符合算数表达式的规则
        if(!m.matches()){
            findError(withoutBrackets);
            return false;
        }
        return true;
    }
    
    
    //找出算数表达式中的错误
    void findError(String expression){
        //判断表达式的下一个符号应该是数字还是运算符
        boolean flag = true;
        //标识当前读到的数字/运算符的位置
        int num = 0;
        int op = 0;
        
        //中缀表达式中当前读取到的字符
        int i = 0;
        //存放中缀表达式的当前字符
        char ch = expression.charAt(i);
        
        expression = expression + '#';
        
        while(ch != '#'){
            //数字
            if(flag){
                //负数，跳过负号
                if(ch == '-'){
                    i++;
                    ch = expression.charAt(i);
                }
                //当前符号是数字
                if(isDigit(ch)){
                    num++;
                    //考虑数字有多位以及存在小数点的情况
                    while(isDigit(ch))
                    {
                        i++;
                        //判断越界
                        if(i >= expression.length())break;
                        ch = expression.charAt(i);
                        //跳过小数点
                        if(ch == '.'){
                            i++;
                            ch = expression.charAt(i);
                        }
                    }
                    flag = false;
                }
                else {
                    if(op == 0 && flag) System.out.println("第1个运算符前面缺少操作数！");
                    else System.out.println("第" + op + "个运算符后面缺少操作数！");
                    break;
                }
            }
            //运算符
            else {
                if(isOpChar(ch)){
                    i++;
                    //判断越界
                    if(i >= expression.length())break;
                    ch = expression.charAt(i);
                    flag = true;
                    op++;
                }
                else {
                    System.out.println("第" + num + "个操作数后面缺少运算符");
                    break;
                }
            }
        }
        if(ch == '#' && flag) System.out.println("第" + op + "个运算符后面缺少操作数！");
    }
	
}
