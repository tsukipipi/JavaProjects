package Expression;


import java.util.Stack;


public class Postfix {

	//ջ������������ȼ�
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
    
    
    //ջ������������ȼ�
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
    
    
    //�ж��ַ��Ƿ�Ϊ����
    static boolean isDigit(char ch){
        if(ch >= '0' && ch <= '9')
            return true;
        return false;
    }
    
    
    //�ж��Ƿ�Ϊ�����
    static boolean isOpChar(char ch){
        if(ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='('||ch==')'||ch=='#')
            return true;
        return false;
    }
    
    
    //��׺ת��׺
    static void postfix(String expression){
        String postExpression = "";
        //����������ջ
        Stack sop = new Stack();
        //��׺���ʽ�е�ǰ��ȡ�����ַ�
        int i = 0;
        //��Ŵ�ջ�������Ĳ�����
        char op;
        //�����׺���ʽ�ĵ�ǰ�ַ�
        char ch;
        
        expression = expression + '#';
        
        //��ʼ��ջ���Ȱ����ȼ�����͵Ľ�����#����ջ��
        sop.push('#');
        ch = expression.charAt(i);

        //��ջ��Ϊ#���ұ��ʽ��ȡ��#ʱ����
        while((char)sop.peek() != '#' || ch != '#')
        {
            //�ж��ǲ��Ǹ���
            if(ch == '-'){
                //ǰһ������������������Ǹ���
                if(isOpChar(expression.charAt(i-1))){
                    i++;
                    postExpression = postExpression + '-';
                    ch = expression.charAt(i);
                }
            }
            //��ǰ����������
            if(isDigit(ch))
            {
                //���������ж�λ�Լ�����С��������
                while(isDigit(ch) || ch == '.')
                {
                    //System.out.println(expression.charAt(i));
                    postExpression = postExpression + ch;
                    i++;
                    //�ж�Խ��
                    if(i >= expression.length())break;
                    ch = expression.charAt(i);
                }
                postExpression = postExpression + ' ';
            }
            //��ǰ�����������
            else
            {
                //ȡ��ջ��������
                op = (char)sop.peek();

                //���������ȼ� ջ�⣾ջ�� ѹջ
                if(icp(ch) > isp(op))
                {
                    sop.push(ch);
                    //��ȡ��׺���ʽ��һ������
                    i++;
                    ch = expression.charAt(i);
                }
                //���������ȼ� ջ�⣼ջ�� ��ջ
                else if(icp(ch) < isp(op))
                {
                    postExpression = postExpression + op + ' ';
                    sop.pop();
                }
                //opΪ ( ����ʱջ�ڵ� ( ��ջ��� ) ���ȼ���ͬ
                else
                {
                    //����Ҫ�����ż����׺���ʽ
                    sop.pop();
                    //��ȡ��׺���ʽ��һ������
                    i++;
                    ch = expression.charAt(i);
                }
            }//end else
        }//end while
        //�����׺���ʽ
        System.out.println("��׺���ʽ��" + postExpression);
        
    }
	
}
