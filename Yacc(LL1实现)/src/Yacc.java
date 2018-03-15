/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author pipi
 */

public class Yacc {
    //��ʼ����
    static String startSymbol;
    //����ս��
    static List<String> Terminal;
    //��ŷ��ս��
    static List<String> Nonterminal;
    //�洢ÿһ������ʽ��ÿ������
    static ArrayList<ArrayList<String>> Production;
    //��ŷ��ս���ŵ�first���ϣ�ʹ��HashSet��֤��������ظ����ս����
    static HashMap<String,HashSet<String>> FirstSet;
    //��ŷ��ս���ŵ�follow���ϣ�ʹ��HashSet��֤��������ظ����ս����
    static HashMap<String,HashSet<String>> FollowSet;
    //���Ԥ���������д����ʽ����ţ���-1��ʾerror
    static int [][]AnalyzeTable;
    //Ҫ�����﷨�����ĵ�����
    static List<String> Sentence;
    //�﷨����ջ
    static Stack<String> SyntaxAnalyzeStack;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        init();
        //��ȡ10���ķ�
        for(int i = 1; i <= 10; i++)
        {
            clearBNF();
            System.out.println("testcase " + i +"\nresult:");
            //��ȡBNF�﷨����
            readBNF("testcases/testcase" + i + "/input.bnf");
            //��ȡFirst��
            getFirstSet();
            //��ȡFollow��
            getFollowSet();
            //����Ԥ�������
            AnalyzeTable = new int[Nonterminal.size()][Terminal.size() + 1];
            //��дԤ�������
            boolean result = fillAnalyzeTable();
            //����LL(1)�ķ�
            if(result){
            	outputBNF();
                //��ȡ10��������
                for(int j = 1; j <= 10; j++)
                {
                    clearSentence();
                    //���뵥����
                    readSentence("testcases/testcase" + i + "/tokenstream" + j + ".tok");
                    outputSentence();
                    //�����﷨����
                    syntaxAnalyze();
                }
            }
            //������LL(1)�ķ�
            else System.out.println("������LL(1)�ķ�");
            System.out.println();
        }
        
    }
    
    //��ʼ��
    static void init(){
    	startSymbol = "";
        Terminal = new ArrayList<>();
        Nonterminal = new ArrayList<>();
        Production = new ArrayList<>();
        FirstSet = new HashMap<>();
        FollowSet = new HashMap<>();
        Sentence = new ArrayList<>();
        SyntaxAnalyzeStack = new Stack<>();
    }
    
    //�����һ��BNF�����Ľ��
    static void clearBNF(){
        Terminal.clear();
        Nonterminal.clear();
        Production.clear();
        FirstSet.clear();
        FollowSet.clear();
    }
    
    //��յ�������ջ
    static void clearSentence(){
        Sentence.clear();
        SyntaxAnalyzeStack.clear();
    }
    
    //���벢�洢BNF����
    static void readBNF(String fileName){
        
        //��������ÿһ��
        String line;
        //����һ���ս���š����ս����
        String symbol;
        //����ÿ������ʽ�����ķ��ս����
        String nonterminal="";
        //��¼����ʽ����Ŀ
        int count = -1;
        
        //��ʶ�ڲ���ʽ����߻����ұ�
        //true:���  false:�ұ�
        boolean flag;
        
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine().trim()) != null) {
                //����ʽ���
                flag = true;
                //�Զ����һ�в���ʽ���д������ܲ�ֹһ������ʽ��
                for(int i = 0; i<line.length(); i++){
                    //����::=�������ʽ�ұ�
                    if(line.charAt(i)==':' && line.charAt(i+1)==':' && line.charAt(i+2)=='=')
                    {
                        flag = false;
                        //�� i = '='
                        i = i+2;
                    }
                
                    //����|���һ���µĲ���ʽ
                    else if(line.charAt(i)=='|'){
                        //����ʽ��Ŀ��1
                        count++;
                        Production.add(new ArrayList<String>());
                        //������ʽ��˷���
                        Production.get(count).add(nonterminal);
                    }
                
                    //����<>�еķ��ţ��ж����ս����or���ս����
                    else if(line.charAt(i)=='<'){
                        //jΪi����һ������
                        int j = i+1;
                        while(line.charAt(j)!='>'){
                            j++;
                        }
                        //substring(beginIndex,endIndex)����һ�����ַ�������beginIndex��ʼ��endIndex - 1
                        symbol = line.substring(i,j+1);
                        //��ʼ����
                        if(count == -1)startSymbol = symbol;
                    
                        //����ʽ��ߣ����ս����flagΪtrue
                        if(flag){
                            nonterminal = symbol;
                            //������ս����û��������ţ��������ӵ����ս����
                            if(!Nonterminal.contains(symbol)) Nonterminal.add(symbol);
                        
                            //�������ʽ��ˣ����һ���µĲ���ʽ
                            count++;
                            Production.add(new ArrayList<String>());
                            Production.get(count).add(nonterminal);
                        }
                    
                        //����ʽ�ұߣ������ս���š����Ϊ�ս���ţ�flagΪfalse
                        else{
                            if(!Terminal.contains(symbol)) Terminal.add(symbol);
                            //�����Ƿ�Ϊ�ս���ţ����ŵ�����ʽ��
                            Production.get(count).add(symbol);
                        }
                        //�� i = '>'
                        i=j;
                    }//end if:read symbol <��>
                
                    //����""�е��ս����
                    else if(line.charAt(i)=='\"'){
                    //jΪi����һ������
                        int j = i+1;
                        while(line.charAt(j)!='\"'){
                            j++;
                        }
                        //substring(beginIndex,endIndex)����һ�����ַ�������beginIndex��ʼ��endIndex - 1
                        symbol = line.substring(i, j+1);
                        if(!Terminal.contains(symbol)) Terminal.add(symbol);
                        Production.get(count).add(symbol);
                        //System.out.println(">>>"+symbol);
                        //�� i = '"'
                        i=j;
                    }//end if:read symbol"��"
                }//end for:read one line
                //���ս���ż�������ȥ�����ս����
                Iterator iterator = Nonterminal.iterator();
                while (iterator.hasNext()) {  
                    String tempnonterminal = (String)iterator.next();
                    if(Terminal.contains(tempnonterminal))Terminal.remove(tempnonterminal);
                }
                //������ڿմ��������Ƴ�
                if(Terminal.contains("\"\""))Terminal.remove("\"\"");
            }//end while:read a BNF
            //�ر�bufferedReader
            bufferedReader.close();
        } catch (Exception e) {
            //  System.err.println("read errors :" + e);
        }
        
    }//end ReadBNF
    
    //������ս���ŵ�First��
    static void getFirstSet(){
        //���ս����
        Iterator iterator1 = Nonterminal.iterator();
        while (iterator1.hasNext()) { 
            FirstSet.put((String)iterator1.next(),new HashSet());
        }
        //������ս����First��
        Set setFirst = FirstSet.keySet();
        for(Iterator iter = setFirst.iterator(); iter.hasNext();)
        {
            String key = (String)iter.next();
            FirstFunction(key);
        } 
    }//end static void getFirstSet
    
    //������Ҫ����First���ķ��ս����
    static void FirstFunction(String nonterminal){
        boolean haveEmptySymbol;
        //�ҵ����ս����Ӧ�Ĳ���ʽ�����ܴ��ڶ�������ʽ��
        for(int i = 0; i < Production.size(); i++)
        {
            //����ʽ�ĵ�һ�����ż�Ϊ�÷��ս��
            if(Production.get(i).get(0).equals(nonterminal))
            {
                //�Ӳ���ʽ���ұߵĵ�һ�����ſ�ʼ
                for(int j = 1; j<Production.get(i).size(); j++)
                {
                    haveEmptySymbol = false;
                    String symbol = Production.get(i).get(j);
                    
                    //����ǿմ���������뵽First�������������һ������
                    if(symbol.equals("\"\"")){
                        FirstSet.get(nonterminal).add(symbol);
                    }
                    //������ս���ţ�������뵽First��������������������ʽ�ļ���
                    else if(Terminal.contains(symbol)){
                        FirstSet.get(nonterminal).add(symbol);
                        break;
                    }
                    //���ս����
                    else{
                        //�ݹ�����÷��ս����First��
                        FirstFunction(symbol);
                        //������ʽ�ұߵķ��ս����First�����뵽����ʽ��ߵķ��ս����First����
                        Iterator iterator = FirstSet.get(symbol).iterator();
                        while (iterator.hasNext()) { 
                            String symbolToAdd = (String)iterator.next();
                            FirstSet.get(nonterminal).add(symbolToAdd);
                            //�пմ�
                            if(symbolToAdd.equals("\"\""))haveEmptySymbol = true;
                        }
                        //û�пմ�����ֹ
                        if(!haveEmptySymbol)break;
                    }
                }//end for �����ս����һ������ʽ��First����
            }//end if �жϲ���ʽ�Ƿ�����Ҫ���ķ��ս��
        }//end for �ҵ�Ҫ���ķ��ս���Ŷ�Ӧ�Ĳ���ʽ
    }//end FirstFunction
    
    //������ս���ŵ�Follow��
    static void getFollowSet(){
        
        //�ҳ����ս����
        Iterator iterator1 = Nonterminal.iterator();
        while (iterator1.hasNext()) { 
            FollowSet.put((String)iterator1.next(),new HashSet());
        }
        try{
        	//��ʼ���ŵ�follow�����$
            FollowSet.get(Nonterminal.get(0)).add("$");
        }catch(IndexOutOfBoundsException e){
        	
        }
        
        
        //����ÿ�����ս����follow���ĳ��ȣ������ж��Ƿ���Ҫ�ٴ����Follow��
        //Ĭ�ϳ�ʼ��Ϊ0
        int []Length = new int[Nonterminal.size()];
        Set setFollow = FollowSet.keySet();
        
        //������ս����Follow��
        //������⣬ÿ���ж�����һ�εļ��ϳ����Ƿ�һ�£�һ����˵��������
        while(true){
            FollowFunction();
            boolean flag = true;
            int i = 0;
            //�ж�����һ��������Ƿ�һ��
            for(Iterator iter = setFollow.iterator(); iter.hasNext();)
            {
                //��ȡ���ϳ���
                int size = FollowSet.get((String)iter.next()).size();
                if(size != Length[i])
                {
                    flag = false;
                    Length[i] = size;
                }
                i++;
            }
            if(flag)break;
        }
    }//end getFollowSet
    
    //һ��Follow���ļ���
    static void FollowFunction(){
        boolean haveEmptySymbol;
        //������в���ʽ�Ҳ�ķ��ս����Follow��
        for(int i = 0; i < Production.size(); i++)
        {
            //�Ӳ���ʽ���ұߵĵ�һ�����ſ�ʼ
            for(int j = 1; j < Production.get(i).size(); j++)
            {
                String symbol = Production.get(i).get(j);
                //����Ƿ��ս��
                if(Nonterminal.contains(symbol))
                {
                    //����ǲ���ʽ���һ���ַ�
                    if(j == Production.get(i).size()-1){
                        Iterator iterator1 = FollowSet.get(Production.get(i).get(0)).iterator();
                        while (iterator1.hasNext()) {
                            FollowSet.get(symbol).add((String)iterator1.next());
                        }
                    }
                    //���ս������Ĵ�
                    for(int k = j+1; k < Production.get(i).size(); k++)
                    {
                        haveEmptySymbol = false;
                        String nextSymbol = Production.get(i).get(k);
                        //����ǿմ�����������
                        if(nextSymbol.equals("\"\"")){}
                        //������ս��
                        else if(Terminal.contains(nextSymbol)){
                            FollowSet.get(symbol).add(nextSymbol);
                            break;
                        }
                        //�Ƿ��ս��
                        else{
                            //��ȡ�÷��ս����First��
                            Iterator iterator = FirstSet.get(nextSymbol).iterator();
                            while (iterator.hasNext()) {
                                String addSymbol = (String)iterator.next();
                                //�ǿմ���������Follow��
                                if(addSymbol.equals("\"\"")){
                                    haveEmptySymbol = true;
                                    //��������һ�����ս����������ʽ���ķ��ս����Follow������
                                    if(k == Production.get(i).size()-1){
                                        Iterator iterator1 = FollowSet.get(Production.get(i).get(0)).iterator();
                                        while (iterator1.hasNext()) {
                                            FollowSet.get(symbol).add((String)iterator1.next());
                                        }
                                    }
                                }
                                //���ǿմ�������Follow��
                                else FollowSet.get(symbol).add(addSymbol);
                            }
                        }
                        //���First��û�пմ��Ϳ��Խ���
                        if(!haveEmptySymbol)break;
                    }//end for�������ս������Ĵ���First��
                }//end if������һ�����ս����
            }//end for������һ������ʽ
        }//end for���������в���ʽ
    }//end FollowFunction
    
    //��дԤ�������
    static boolean fillAnalyzeTable(){
        //��ʼ��Ԥ�������
        for(int i = 0; i < AnalyzeTable.length; i++)
            for(int j = 0; j < AnalyzeTable[i].length; j++)
                AnalyzeTable[i][j] = -1;
        
        boolean haveEmptySymbol;
        
        for(int i = 0; i < Production.size(); i++)
        {
            String rowSymbol = Production.get(i).get(0);
            //�Ӳ���ʽ���ұߵĵ�һ�����ſ�ʼ
            for(int j = 1; j<Production.get(i).size(); j++)
            {
                haveEmptySymbol = false;
                String columnSymbol = Production.get(i).get(j);
                //����ǿմ�����������
                if(columnSymbol.equals("\"\"")){
                    haveEmptySymbol = true;
                }
                //������ս���ţ����ò���ʽ���뵽�ս���ŵ�Ԥ�������ı�����
                else if(Terminal.contains(columnSymbol)){
                    int row = Nonterminal.indexOf(rowSymbol);
                    int column = Terminal.indexOf(columnSymbol);
                    //��Ԥ�������������Ӧ�Ĳ���ʽ���
                    if(AnalyzeTable[row][column] == -1 || AnalyzeTable[row][column] == i)
                        AnalyzeTable[row][column] = i;
                    else return false;
                }
                //�Ƿ��ս���ţ����ò���ʽ���뵽���ս����First����Ԥ�������ı�����
                else {
                    Iterator iterator1 = FirstSet.get(columnSymbol).iterator();
                    while (iterator1.hasNext()) { 
                        String symbol = (String)iterator1.next();
                        int row = Nonterminal.indexOf(rowSymbol);
                        int column = Terminal.indexOf(symbol);
                        //��������մ�
                        if(symbol.equals("\"\"")) haveEmptySymbol =true;
                        //��Ԥ�������������Ӧ�Ĳ���ʽ���
                        else {
                            if(AnalyzeTable[row][column] == -1 || AnalyzeTable[row][column] == i)
                                AnalyzeTable[row][column] = i;
                            else return false;
                        }
                    }
                }//end else �Է��ս���ŵĴ���
                
                //�������ʽ��߿���Ϊ�գ����ò���ʽ���뵽�Ҳ���ս����Follow����Ԥ�������ı�����
                //���Ϊ�գ����㵽���һ�����Ų�������Ϊ��
                if(haveEmptySymbol && j == Production.get(i).size()-1){
                    Iterator iterator2 = FollowSet.get(rowSymbol).iterator();
                    while (iterator2.hasNext()) { 
                        String symbol = (String)iterator2.next();
                        int row = Nonterminal.indexOf(rowSymbol);
                        int column;
                        if(symbol.equals("$"))//���һ��
                            column = Terminal.size();
                        else column = Terminal.indexOf(symbol);
                        //��Ԥ�������������Ӧ�Ĳ���ʽ���
                        if(AnalyzeTable[row][column] == -1 || AnalyzeTable[row][column] == i)
                            AnalyzeTable[row][column] = i;
                        else return false;
                    }
                }
                //��������ڿմ�����Ա�����ʽ�ļ������
                if(!haveEmptySymbol) break;
            }//end for ��һ������ʽ�Ĵ���
        }//end for �����в���ʽ�Ĵ���
        return true;
    }// end fillForecastTable
    
    //����Ҫ�����ĵ�����
    static void readSentence(String fileName){
        
        String line;
        
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //�Զ�����ӽ��д���
            while ((line = bufferedReader.readLine().trim()) != null) {
                Sentence.add(line);
            }//end while:read sentence
            //�ر�bufferedReader
            bufferedReader.close();
        } catch (Exception e) {
            //  System.err.println("read errors :" + e);
        }
        
        //��������пմ��������Ƴ�
        while(Sentence.contains("\"\"")){
            Sentence.remove("\"\"");
        }
        
        //�����봮ĩβ���$
        Sentence.add("$");
        
    }//end readSentence
    
    //�����﷨����
    static void syntaxAnalyze(){
        boolean flag = true;
        int pointer = 0;
        //��ʼ��ջ������ʼ���ź�$ѹ��ջ��
        SyntaxAnalyzeStack.push("$");
        SyntaxAnalyzeStack.push(startSymbol);
        //ջ������
        String stackTop;
        int column;
        int row = 0;
        while(!SyntaxAnalyzeStack.empty())
        {
            stackTop = SyntaxAnalyzeStack.peek();
            String nextInput = Sentence.get(pointer);
            //System.out.println(stackTop + nextInput);
            
            //�����ǰ���������$���������һ��
            if(nextInput.equals("$"))
               column = Terminal.size();
            else column = Terminal.indexOf(nextInput);
            //��ջ��Ԫ��Ϊ���ս����ʱ������ֵ
            if(Nonterminal.contains(stackTop))
                row = Nonterminal.indexOf(stackTop);
            
            
            try{
            	//System.out.println(stackTop + ":" + row + "        " + nextInput + ":" + column);
                //ջ��Ԫ���ǵ�ǰ������ţ��ս������ջ�����ų�ջ��������һ���������
                if(stackTop.equals(Sentence.get(pointer))){
                    SyntaxAnalyzeStack.pop();
                    pointer++;
                }
                //ջ�������������ս������ʾ���Ӳ����ϱ��ķ�
                else if(Terminal.contains(stackTop)){
                    flag = false;
                    break;
                }
            	
            	//ջ���Ƿ��ս�������ڱ������Ŀ�������ϱ��ķ�
                else if(AnalyzeTable[row][column] == -1){
                    flag = false;
                    break;
                }
                
                //ջ���Ƿ��ս�����ҵ���Ӧ�Ĳ���ʽ����ջ�����ŵ������滻Ϊ����ʽ�Ҳ�ķ���
                else {
                    //��ö�Ӧ�Ĳ���ʽ���
                    int productionNum = AnalyzeTable[row][column];
                    SyntaxAnalyzeStack.pop();
                    //������ʽ�Ҳ�ķ�������ѹ��ջ�У�ע��˳��
                    for(int j = Production.get(productionNum).size()-1; j >= 1; j--)
                    {
                        String symbol = Production.get(productionNum).get(j);
                        //���ǿմ�ѹջ
                        if(!symbol.equals("\"\"")) SyntaxAnalyzeStack.push(symbol);
                    }
                }
            }catch(ArrayIndexOutOfBoundsException e){
            	
            }
        }//end while
        
        if(flag) System.out.println("yes");
        else System.out.println("no");
    }//end syntaxAnalyze
    
  //������ս�����ս��������ʽ��First����Follow����Ԥ�������
    static void outputBNF(){
        System.out.println("\n���ս����");
        Iterator iterator1 = Nonterminal.iterator();
        while (iterator1.hasNext()) {  
            System.out.println(iterator1.next());
        }
        
        System.out.println("\n�ս����");
        Iterator iterator2 = Terminal.iterator();
        while (iterator2.hasNext()) {  
            System.out.println(iterator2.next());
        }
        
        //�������ʽ
        System.out.println("\n����ʽ��Ŀ��" + Production.size());
        System.out.println("����ʽ��");
        for(int i = 0; i < Production.size(); i++)
        {
            for(int j =0; j < Production.get(i).size(); j++)
            {
                System.out.print(Production.get(i).get(j)+" ");
            }
            System.out.println();
        }
        
        //������ս���ŵ�First��
        Set setFirst = FirstSet.keySet();
        System.out.println("\nFirst����");
        for(Iterator iter = setFirst.iterator(); iter.hasNext();)
        {
            String key = (String)iter.next();
            System.out.print(key + ":");
            Iterator iterator = FirstSet.get(key).iterator();
            while (iterator.hasNext()) {  
                System.out.print(iterator.next() + " ");              
            }
            System.out.println();
        }
        
        //������ս���ŵ�Follow��
        Set setFollow = FollowSet.keySet();
        System.out.println("\nFollow����");
        for(Iterator iter = setFollow.iterator(); iter.hasNext();)
        {
            String key = (String)iter.next();
            System.out.print(key + ":");
            Iterator iterator = FollowSet.get(key).iterator();
            while (iterator.hasNext()) {  
                System.out.print(iterator.next() + " ");              
            }
            System.out.println();
        }
        
        //���Ԥ�������ı���
        System.out.println("\nԤ�������");
        System.out.print("      ");
        for(int i = 0; i < Terminal.size(); i++)
            System.out.print(Terminal.get(i)+ "   ");
        System.out.print("$"+ "    ");
        System.out.println();
        for(int i = 0; i < AnalyzeTable.length; i++)
        {
            System.out.print(Nonterminal.get(i)+ "    ");
            for(int j = 0; j < AnalyzeTable[i].length; j++)
                System.out.print(AnalyzeTable[i][j] + "    ");
            System.out.println();
        }
        
    }//end outputBNF
    
    //����ķ��Ŵ�
    static void outputSentence(){
        //�������ķ��Ŵ�
        System.out.println("\n����ķ��Ŵ���");
        for(int i = 0; i < Sentence.size(); i++)
            System.out.print(Sentence.get(i));
        System.out.println();
    }//end outputoutputSentence
    
}
