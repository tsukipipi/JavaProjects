package Expression;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Expression {
	

    //存放每一个表达式
    static String [] Expressions;
    //文件名：expressions.txt
    static String filename = "expressions.txt";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        ExpressionTree tree = new ExpressionTree();
        //随机生成表达式
        tree.createExpression(filename);
        
        boolean flag = true;
        
        //读取文件中的表达式
        readExpression(filename);
        //去掉表达式中的空格、制表符等空白符号
        replaceBlank();
        
        ErrorDetection detect = new ErrorDetection();
        Postfix change = new Postfix();
        
        try{
        	
        	//中缀转后缀
            for(int i = 0; i< Expressions.length; i++)
            {
                System.out.println();
                
                System.out.println("中缀表达式：" + Expressions[i]);
                //判断表达式是否有错
                flag = detect.matchBrackets(Expressions[i]);
                if(flag) flag = detect.isExpression();
                else detect.isExpression();
                if(!flag) continue;
                //对于没有错误的表达式，中缀转后缀
                change.postfix(Expressions[i]);
            }
        	
        }catch(NullPointerException e){
        }
        
    }
    
    
    //读取文件中的表达式
    static void readExpression(String fileName){
        //存放读入的所有表达式
        String AllExpressions;
        
        try{
            //读入所有的表达式
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedreader = new BufferedReader(fileReader);
            AllExpressions = bufferedreader.readLine();
            System.out.println("读文件：" + AllExpressions);
            //分割表达式
            Expressions = AllExpressions.split(";");
            fileReader.close();
        }
        catch(Exception e){
        }
    }
    
    
    //去掉表达式中的空格、制表符等空白符号
    static void replaceBlank(){
    	try{
    		
    		for(int i = 0; i< Expressions.length; i++)
            {
                if(Expressions[i] != null){
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    //matcher()创建匹配给定输入与此模式的匹配器
                    Matcher m = p.matcher(Expressions[i]);  
                    //将所有的空格、制表符等空白符号替换为""
                    Expressions[i] = m.replaceAll("");  
                }
            }
    		
    	}catch(NullPointerException e){
    	}
    }
	

}
