package Expression;


import java.util.Stack;


public class Postfix {

	//栈内运算符的优先级
    static int isp(char op){
        switch (op)
        {
            case '#':return 0;
            case '(':return 1;
            case '*':case '/':return 5;
            case '+':case '-':return 3;
            case ')':return 6;
        }
        return -1;
    }
    
    
    //栈外运算符的优先级
    static int icp(char op){
        switch (op)
        {
            case '#':return 0;
            case '(':return 6;
            case '*':case '/':return 4;
            case '+':case '-':return 2;
            case ')':return 1;
        }
        return -1;
    }
    
    
    //判断字符是否为数字
    static boolean isDigit(char ch){
        if(ch >= '0' && ch <= '9')
            return true;
        return false;
    }
    
    
    //判断是否为运算符
    static boolean isOpChar(char ch){
        if(ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='('||ch==')'||ch=='#')
            return true;
        return false;
    }
    
    
    //中缀转后缀
    static void postfix(String expression){
        String postExpression = "";
        //存放运算符的栈
        Stack sop = new Stack();
        //中缀表达式中当前读取到的字符
        int i = 0;
        //存放从栈顶弹出的操作符
        char op;
        //存放中缀表达式的当前字符
        char ch;
        
        expression = expression + '#';
        
        //初始化栈，先把优先级别最低的结束符#放入栈中
        sop.push('#');
        ch = expression.charAt(i);

        //当栈顶为#并且表达式读取到#时结束
        while((char)sop.peek() != '#' || ch != '#')
        {
            //判断是不是负号
            if(ch == '-'){
                //前一个符号是运算符，则是负数
                if(isOpChar(expression.charAt(i-1))){
                    i++;
                    postExpression = postExpression + '-';
                    ch = expression.charAt(i);
                }
            }
            //当前符号是数字
            if(isDigit(ch))
            {
                //考虑数字有多位以及存在小数点的情况
                while(isDigit(ch) || ch == '.')
                {
                    //System.out.println(expression.charAt(i));
                    postExpression = postExpression + ch;
                    i++;
                    //判断越界
                    if(i >= expression.length())break;
                    ch = expression.charAt(i);
                }
                postExpression = postExpression + ' ';
            }
            //当前符号是运算符
            else
            {
                //取得栈顶操作符
                op = (char)sop.peek();

                //操作符优先级 栈外＞栈内 压栈
                if(icp(ch) > isp(op))
                {
                    sop.push(ch);
                    //读取中缀表达式下一个符号
                    i++;
                    ch = expression.charAt(i);
                }
                //操作符优先级 栈外＜栈内 出栈
                else if(icp(ch) < isp(op))
                {
                    postExpression = postExpression + op + ' ';
                    sop.pop();
                }
                //op为 ( ，此时栈内的 ( 与栈外的 ) 优先级相同
                else
                {
                    //不需要将括号加入后缀表达式
                    sop.pop();
                    //读取中缀表达式下一个符号
                    i++;
                    ch = expression.charAt(i);
                }
            }//end else
        }//end while
        //输出后缀表达式
        System.out.println("后缀表达式：" + postExpression);
        
    }
	
}
