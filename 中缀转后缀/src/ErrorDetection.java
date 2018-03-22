package Expression;


import static Expression.Postfix.isDigit;
import static Expression.Postfix.isOpChar;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ErrorDetection {

	//�������ŵı��ʽ�����ڷ����Ƿ������������Ĺ���
    String withoutBrackets;
    
    
    //�жϱ��ʽ�������Ƿ�ƥ�䲢�޳����е�����
    boolean matchBrackets(String expression){
        
        withoutBrackets = "";
        
        //��������ŵ�ջ
        Stack sbra = new Stack();
        //������Ŀ
        int bracketNum = 0;
        //��ű��ʽ�ĵ�ǰ����
        char ch;
        //��ʶ���ʽ�Ƿ��д���
        boolean flag = true;
        
        for(int i = 0; i <expression.length(); i++){
            //������ѹջ
            ch = expression.charAt(i);
            if(ch == '('){
                bracketNum++;
                sbra.push(bracketNum);
            }
            //�����ţ������ų�ջ
            else if(ch == ')'){
                bracketNum++;
                //ջ�в�Ϊ��
                if(sbra.empty() != true)sbra.pop();
                //�����Ŷ���
                else {
                    flag = false;
                    System.out.println("�������࣬��" + bracketNum + "�����Ŷ���");
                }
            }
            //���ʽ�з�()�ķ���
            else withoutBrackets = withoutBrackets + ch;
        }
        //����������
        while(sbra.empty() != true){
            flag = false;
            System.out.println("�������࣬��" + sbra.peek() + "�����Ŷ���");
            sbra.pop();
        }
        return flag;
    }
    
    
    //�ж��Ƿ�����������ʽ�Ĺ���
    boolean isExpression(){
        //��������������ʽ��^(-?\\d+)(\\.\\d+)?$    ^ ƥ�俪ʼλ��    $ ƥ�����λ��
        //���������������ʽ��(-?\\d+)(\\.\\d+)?((\\+|\\-|\\*|\\/)(-?\\d+)(\\.\\d+)?)*
        Pattern p = Pattern.compile("(-?\\d+)(\\.\\d+)?((\\+|\\-|\\*|\\/)(-?\\d+)(\\.\\d+)?)*");
        Matcher m = p.matcher(withoutBrackets);
        //�������������ʽ�Ĺ���
        if(!m.matches()){
            findError(withoutBrackets);
            return false;
        }
        return true;
    }
    
    
    //�ҳ��������ʽ�еĴ���
    void findError(String expression){
        //�жϱ��ʽ����һ������Ӧ�������ֻ��������
        boolean flag = true;
        //��ʶ��ǰ����������/�������λ��
        int num = 0;
        int op = 0;
        
        //��׺���ʽ�е�ǰ��ȡ�����ַ�
        int i = 0;
        //�����׺���ʽ�ĵ�ǰ�ַ�
        char ch = expression.charAt(i);
        
        expression = expression + '#';
        
        while(ch != '#'){
            //����
            if(flag){
                //��������������
                if(ch == '-'){
                    i++;
                    ch = expression.charAt(i);
                }
                //��ǰ����������
                if(isDigit(ch)){
                    num++;
                    //���������ж�λ�Լ�����С��������
                    while(isDigit(ch))
                    {
                        i++;
                        //�ж�Խ��
                        if(i >= expression.length())break;
                        ch = expression.charAt(i);
                        //����С����
                        if(ch == '.'){
                            i++;
                            ch = expression.charAt(i);
                        }
                    }
                    flag = false;
                }
                else {
                    if(op == 0 && flag) System.out.println("��1�������ǰ��ȱ�ٲ�������");
                    else System.out.println("��" + op + "�����������ȱ�ٲ�������");
                    break;
                }
            }
            //�����
            else {
                if(isOpChar(ch)){
                    i++;
                    //�ж�Խ��
                    if(i >= expression.length())break;
                    ch = expression.charAt(i);
                    flag = true;
                    op++;
                }
                else {
                    System.out.println("��" + num + "������������ȱ�������");
                    break;
                }
            }
        }
        if(ch == '#' && flag) System.out.println("��" + op + "�����������ȱ�ٲ�������");
    }
	
}
