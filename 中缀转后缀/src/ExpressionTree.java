package Expression;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ExpressionTree {

	ExpressionNode head = null;
    char[] operator = new char[]{'+', '-', '*', '/'};
    String expression = "";
    //运算符的数目
    int opNum = 1;
    //正确和错误的表达式的数目
    int trueNum = 0;
    int falseNum = 0;

    
    //建立表达式二叉树
    void createTree() {
        head = new ExpressionNode();

        Random random = new Random();
        int op = random.nextInt(4);
        head.data = operator[op] + "";

        head.leftChild = new ExpressionNode();
        head.rightChild = new ExpressionNode();
        createTree(head.leftChild);
        createTree(head.rightChild);

    }

    
    //递归建立表达式二叉树
    void createTree(ExpressionNode subNode) {

        Random random = new Random();
        //0：操作符，继续生成子树    1：数字，作为叶子结点
        int type = random.nextInt(2);
        //把运算符的个数限制在10个及10个以内
        if (opNum >= 20) {
            type = 1;
        }
        //生成子树
        if (type == 0) {
            opNum++;
            int op = random.nextInt(4);
            subNode.data = subNode.data + operator[op];
            subNode.leftChild = new ExpressionNode();
            subNode.rightChild = new ExpressionNode();
            createTree(subNode.leftChild);
            createTree(subNode.rightChild);
        }//end if:create operator
        //生成数字
        else {
            //0：整数    1：两位的浮点数
            int numType = random.nextInt(2);
            //0：正数    1：负数
            int plus_minus = random.nextInt(2);
            if(plus_minus == 1) subNode.data = subNode.data + "-";
            //生成整数
            if (numType == 0) {
                int num = random.nextInt(50);
                subNode.data = subNode.data + num;
            } 
            else {
                double num = random.nextInt(50) * random.nextDouble();
                //将浮点数转化为两位小数
                String number = String.valueOf(num);
                if (number.contains(".")) {
                    int point = number.indexOf(".");
                    if ((point + 3) <= number.length()) {
                        number = number.substring(0, point + 3);
                    }
                }
                subNode.data = subNode.data + number;
            }
            subNode.leftChild = null;
            subNode.rightChild = null;
        }//end else:create number
    }//end create expression tree

    
    //中序遍历表达式二叉树
    String inOrder() {
        expression = "";
        if ("*".equals(head.data) || "/".equals(head.data))
            expression = inOrder(head, true);
        else expression = inOrder(head, false);
        
        //System.out.println(expression);
        return expression;
    }

    
    //中序遍历表达式二叉树
    String inOrder(ExpressionNode subNode, boolean flag) {

        //+ - 需要考虑加括号
        if("+".equals(subNode.data) || "-".equals(subNode.data)) {
            //父节点是 * 或 /
            if(flag) return "(" + inOrder(subNode.leftChild, false) + subNode.data + inOrder(subNode.rightChild, false) + ")";
            //父节点是 + 或 -
            else return inOrder(subNode.leftChild, false) + subNode.data + inOrder(subNode.rightChild, false);
        }
        else if ("*".equals(subNode.data) || "/".equals(subNode.data))
             return inOrder(subNode.leftChild, true) + subNode.data + inOrder(subNode.rightChild, true);
        //数字
        else {
            if(subNode.data.contains("-"))return "(" + subNode.data + ")";
            else return subNode.data;
        }
    }
    
    
    //生成正确的表达式
    String trueExpression(){
        createTree();
        String exp = inOrder();
        System.out.println(exp);
        return exp;
    }
    
    
    //生成错误的表达式
    String falseExpression(){
        //先生成正确的表达式
        createTree();
        String exp = inOrder();
        
        Random random = new Random();
        //错误类型选择
        int type = random.nextInt(3);
        
        //找到所有符号的位置
        List op_pos = new ArrayList();
        int temp_pos = exp.indexOf("+");
        while(temp_pos != -1){
            op_pos.add(temp_pos);
            temp_pos = exp.indexOf("+",temp_pos + 1);
        }
        temp_pos = exp.indexOf("-");
        while(temp_pos != -1){
            op_pos.add(temp_pos);
            temp_pos = exp.indexOf("-",temp_pos + 1);
        }
        temp_pos = exp.indexOf("*");
        while(temp_pos != -1){
            op_pos.add(temp_pos);
            temp_pos = exp.indexOf("*",temp_pos + 1);
        }
        temp_pos = exp.indexOf("/");
        while(temp_pos != -1){
            op_pos.add(temp_pos);
            temp_pos = exp.indexOf("/",temp_pos + 1);
        }
        
        //插入运算符
        if(type == 0){
            StringBuilder sbexp = new StringBuilder(exp);
            //随机插入的符号
            int op = random.nextInt(4);
            //随机插入的位置
            int pos = (int)op_pos.get(random.nextInt(op_pos.size()));
            sbexp.insert(pos, operator[op]);
            System.out.println(sbexp.toString());
            return sbexp.toString();
        }
        //替换掉运算符
        else if(type == 1){
            StringBuilder sbexp = new StringBuilder(exp);
            //随机替换的位置
            int pos = (int)op_pos.get(random.nextInt(op_pos.size()));
            sbexp.deleteCharAt(pos);
            sbexp.insert(pos, "a");
            System.out.println(sbexp.toString());
            return sbexp.toString();
        }
        else {
            StringBuilder sbexp = new StringBuilder(exp);
            //随机插入的位置
            int pos = (int)op_pos.get(random.nextInt(op_pos.size()));
            int t = random.nextInt(2);
            if(t == 0)sbexp.insert(pos, ")");
            else sbexp.insert(pos, "(");
            System.out.println(sbexp.toString());
            return sbexp.toString();
        }
    }
    
    
    //随机生成n个表达式
    void createExpression(String filename){
        //用于保存输入的字符串
        String st = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要生成的正确的表达式的数目：");
        st = sc.nextLine();
        //正确表达式的个数
        trueNum = Integer.parseInt(st);
        System.out.println("请输入要生成的错误的表达式的数目：");
        st = sc.nextLine();
        //错误表达式的个数
        falseNum = Integer.parseInt(st);
        
        System.out.println("生成如下表达式：");
        try {
        	//先清空文件中的内容
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write("");
            fileWriter.flush();
            //向文件追加内容
            fileWriter = new FileWriter(filename,true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for(int i = 0; i < trueNum; i++){
                printWriter.print(trueExpression() + ";");
                printWriter.flush();
            }
            for(int i = 0; i < falseNum; i++){
                printWriter.print(falseExpression() + ";");
                printWriter.flush();
            }
            fileWriter.close();
            printWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Expression.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println();
    }
	
}


//树节点
class ExpressionNode {

  //树节点存放的内容
  String data;
  ExpressionNode leftChild;
  ExpressionNode rightChild;
  
  ExpressionNode(){
      data = "";
      leftChild = null;
      rightChild = null;
  }
  
}

