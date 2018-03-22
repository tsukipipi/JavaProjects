package Expression;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Expression {
	

    //���ÿһ�����ʽ
    static String [] Expressions;
    //�ļ�����expressions.txt
    static String filename = "expressions.txt";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        ExpressionTree tree = new ExpressionTree();
        //������ɱ��ʽ
        tree.createExpression(filename);
        
        boolean flag = true;
        
        //��ȡ�ļ��еı��ʽ
        readExpression(filename);
        //ȥ�����ʽ�еĿո��Ʊ���ȿհ׷���
        replaceBlank();
        
        ErrorDetection detect = new ErrorDetection();
        Postfix change = new Postfix();
        
        try{
        	
        	//��׺ת��׺
            for(int i = 0; i< Expressions.length; i++)
            {
                System.out.println();
                
                System.out.println("��׺���ʽ��" + Expressions[i]);
                //�жϱ��ʽ�Ƿ��д�
                flag = detect.matchBrackets(Expressions[i]);
                if(flag) flag = detect.isExpression();
                else detect.isExpression();
                if(!flag) continue;
                //����û�д���ı��ʽ����׺ת��׺
                change.postfix(Expressions[i]);
            }
        	
        }catch(NullPointerException e){
        }
        
    }
    
    
    //��ȡ�ļ��еı��ʽ
    static void readExpression(String fileName){
        //��Ŷ�������б��ʽ
        String AllExpressions;
        
        try{
            //�������еı��ʽ
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedreader = new BufferedReader(fileReader);
            AllExpressions = bufferedreader.readLine();
            System.out.println("���ļ���" + AllExpressions);
            //�ָ���ʽ
            Expressions = AllExpressions.split(";");
            fileReader.close();
        }
        catch(Exception e){
        }
    }
    
    
    //ȥ�����ʽ�еĿո��Ʊ���ȿհ׷���
    static void replaceBlank(){
    	try{
    		
    		for(int i = 0; i< Expressions.length; i++)
            {
                if(Expressions[i] != null){
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    //matcher()����ƥ������������ģʽ��ƥ����
                    Matcher m = p.matcher(Expressions[i]);  
                    //�����еĿո��Ʊ���ȿհ׷����滻Ϊ""
                    Expressions[i] = m.replaceAll("");  
                }
            }
    		
    	}catch(NullPointerException e){
    	}
    }
	

}
