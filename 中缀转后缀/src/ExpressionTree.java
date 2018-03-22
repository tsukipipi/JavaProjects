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
    //���������Ŀ
    int opNum = 1;
    //��ȷ�ʹ���ı��ʽ����Ŀ
    int trueNum = 0;
    int falseNum = 0;

    
    //�������ʽ������
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

    
    //�ݹ齨�����ʽ������
    void createTree(ExpressionNode subNode) {

        Random random = new Random();
        //0����������������������    1�����֣���ΪҶ�ӽ��
        int type = random.nextInt(2);
        //��������ĸ���������10����10������
        if (opNum >= 20) {
            type = 1;
        }
        //��������
        if (type == 0) {
            opNum++;
            int op = random.nextInt(4);
            subNode.data = subNode.data + operator[op];
            subNode.leftChild = new ExpressionNode();
            subNode.rightChild = new ExpressionNode();
            createTree(subNode.leftChild);
            createTree(subNode.rightChild);
        }//end if:create operator
        //��������
        else {
            //0������    1����λ�ĸ�����
            int numType = random.nextInt(2);
            //0������    1������
            int plus_minus = random.nextInt(2);
            if(plus_minus == 1) subNode.data = subNode.data + "-";
            //��������
            if (numType == 0) {
                int num = random.nextInt(50);
                subNode.data = subNode.data + num;
            } 
            else {
                double num = random.nextInt(50) * random.nextDouble();
                //��������ת��Ϊ��λС��
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

    
    //����������ʽ������
    String inOrder() {
        expression = "";
        if ("*".equals(head.data) || "/".equals(head.data))
            expression = inOrder(head, true);
        else expression = inOrder(head, false);
        
        //System.out.println(expression);
        return expression;
    }

    
    //����������ʽ������
    String inOrder(ExpressionNode subNode, boolean flag) {

        //+ - ��Ҫ���Ǽ�����
        if("+".equals(subNode.data) || "-".equals(subNode.data)) {
            //���ڵ��� * �� /
            if(flag) return "(" + inOrder(subNode.leftChild, false) + subNode.data + inOrder(subNode.rightChild, false) + ")";
            //���ڵ��� + �� -
            else return inOrder(subNode.leftChild, false) + subNode.data + inOrder(subNode.rightChild, false);
        }
        else if ("*".equals(subNode.data) || "/".equals(subNode.data))
             return inOrder(subNode.leftChild, true) + subNode.data + inOrder(subNode.rightChild, true);
        //����
        else {
            if(subNode.data.contains("-"))return "(" + subNode.data + ")";
            else return subNode.data;
        }
    }
    
    
    //������ȷ�ı��ʽ
    String trueExpression(){
        createTree();
        String exp = inOrder();
        System.out.println(exp);
        return exp;
    }
    
    
    //���ɴ���ı��ʽ
    String falseExpression(){
        //��������ȷ�ı��ʽ
        createTree();
        String exp = inOrder();
        
        Random random = new Random();
        //��������ѡ��
        int type = random.nextInt(3);
        
        //�ҵ����з��ŵ�λ��
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
        
        //���������
        if(type == 0){
            StringBuilder sbexp = new StringBuilder(exp);
            //�������ķ���
            int op = random.nextInt(4);
            //��������λ��
            int pos = (int)op_pos.get(random.nextInt(op_pos.size()));
            sbexp.insert(pos, operator[op]);
            System.out.println(sbexp.toString());
            return sbexp.toString();
        }
        //�滻�������
        else if(type == 1){
            StringBuilder sbexp = new StringBuilder(exp);
            //����滻��λ��
            int pos = (int)op_pos.get(random.nextInt(op_pos.size()));
            sbexp.deleteCharAt(pos);
            sbexp.insert(pos, "a");
            System.out.println(sbexp.toString());
            return sbexp.toString();
        }
        else {
            StringBuilder sbexp = new StringBuilder(exp);
            //��������λ��
            int pos = (int)op_pos.get(random.nextInt(op_pos.size()));
            int t = random.nextInt(2);
            if(t == 0)sbexp.insert(pos, ")");
            else sbexp.insert(pos, "(");
            System.out.println(sbexp.toString());
            return sbexp.toString();
        }
    }
    
    
    //�������n�����ʽ
    void createExpression(String filename){
        //���ڱ���������ַ���
        String st = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("������Ҫ���ɵ���ȷ�ı��ʽ����Ŀ��");
        st = sc.nextLine();
        //��ȷ���ʽ�ĸ���
        trueNum = Integer.parseInt(st);
        System.out.println("������Ҫ���ɵĴ���ı��ʽ����Ŀ��");
        st = sc.nextLine();
        //������ʽ�ĸ���
        falseNum = Integer.parseInt(st);
        
        System.out.println("�������±��ʽ��");
        try {
        	//������ļ��е�����
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write("");
            fileWriter.flush();
            //���ļ�׷������
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


//���ڵ�
class ExpressionNode {

  //���ڵ��ŵ�����
  String data;
  ExpressionNode leftChild;
  ExpressionNode rightChild;
  
  ExpressionNode(){
      data = "";
      leftChild = null;
      rightChild = null;
  }
  
}

