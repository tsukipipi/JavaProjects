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
    //起始符号
    static String startSymbol;
    //存放终结符
    static List<String> Terminal;
    //存放非终结符
    static List<String> Nonterminal;
    //存储每一条产生式的每个符号
    static ArrayList<ArrayList<String>> Production;
    //存放非终结符号的first集合，使用HashSet保证不会存在重复的终结符号
    static HashMap<String,HashSet<String>> FirstSet;
    //存放非终结符号的follow集合，使用HashSet保证不会存在重复的终结符号
    static HashMap<String,HashSet<String>> FollowSet;
    //存放预测分析表，填写产生式的序号，用-1表示error
    static int [][]AnalyzeTable;
    //要进行语法分析的单词流
    static List<String> Sentence;
    //语法分析栈
    static Stack<String> SyntaxAnalyzeStack;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        init();
        //读取10个文法
        for(int i = 1; i <= 10; i++)
        {
            clearBNF();
            System.out.println("testcase " + i +"\nresult:");
            //读取BNF语法定义
            readBNF("testcases/testcase" + i + "/input.bnf");
            //求取First集
            getFirstSet();
            //求取Follow集
            getFollowSet();
            //创建预测分析表
            AnalyzeTable = new int[Nonterminal.size()][Terminal.size() + 1];
            //填写预测分析表
            boolean result = fillAnalyzeTable();
            //符合LL(1)文法
            if(result){
            	outputBNF();
                //读取10个单词流
                for(int j = 1; j <= 10; j++)
                {
                    clearSentence();
                    //读入单词流
                    readSentence("testcases/testcase" + i + "/tokenstream" + j + ".tok");
                    outputSentence();
                    //进行语法分析
                    syntaxAnalyze();
                }
            }
            //不符合LL(1)文法
            else System.out.println("不符合LL(1)文法");
            System.out.println();
        }
        
    }
    
    //初始化
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
    
    //清空上一次BNF保留的结果
    static void clearBNF(){
        Terminal.clear();
        Nonterminal.clear();
        Production.clear();
        FirstSet.clear();
        FollowSet.clear();
    }
    
    //清空单词流和栈
    static void clearSentence(){
        Sentence.clear();
        SyntaxAnalyzeStack.clear();
    }
    
    //读入并存储BNF定义
    static void readBNF(String fileName){
        
        //保存读入的每一行
        String line;
        //保存一个终结符号、非终结符号
        String symbol;
        //保存每个产生式的左侧的非终结符号
        String nonterminal="";
        //记录产生式的数目
        int count = -1;
        
        //标识在产生式的左边还是右边
        //true:左边  false:右边
        boolean flag;
        
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine().trim()) != null) {
                //产生式左边
                flag = true;
                //对读入的一行产生式进行处理（可能不止一条产生式）
                for(int i = 0; i<line.length(); i++){
                    //遇到::=进入产生式右边
                    if(line.charAt(i)==':' && line.charAt(i+1)==':' && line.charAt(i+2)=='=')
                    {
                        flag = false;
                        //另 i = '='
                        i = i+2;
                    }
                
                    //遇到|添加一条新的产生式
                    else if(line.charAt(i)=='|'){
                        //产生式数目加1
                        count++;
                        Production.add(new ArrayList<String>());
                        //将产生式左端放入
                        Production.get(count).add(nonterminal);
                    }
                
                    //处理<>中的符号，判断是终结符号or非终结符号
                    else if(line.charAt(i)=='<'){
                        //j为i的下一个符号
                        int j = i+1;
                        while(line.charAt(j)!='>'){
                            j++;
                        }
                        //substring(beginIndex,endIndex)返回一个子字符串，从beginIndex开始到endIndex - 1
                        symbol = line.substring(i,j+1);
                        //起始符号
                        if(count == -1)startSymbol = symbol;
                    
                        //产生式左边，非终结符，flag为true
                        if(flag){
                            nonterminal = symbol;
                            //如果非终结符中没有这个符号，则把它添加到非终结符集
                            if(!Nonterminal.contains(symbol)) Nonterminal.add(symbol);
                        
                            //进入产生式左端，添加一条新的产生式
                            count++;
                            Production.add(new ArrayList<String>());
                            Production.get(count).add(nonterminal);
                        }
                    
                        //产生式右边，疑似终结符号。添加为终结符号，flag为false
                        else{
                            if(!Terminal.contains(symbol)) Terminal.add(symbol);
                            //无论是否为终结符号，都放到产生式中
                            Production.get(count).add(symbol);
                        }
                        //另 i = '>'
                        i=j;
                    }//end if:read symbol <…>
                
                    //处理""中的终结符号
                    else if(line.charAt(i)=='\"'){
                    //j为i的下一个符号
                        int j = i+1;
                        while(line.charAt(j)!='\"'){
                            j++;
                        }
                        //substring(beginIndex,endIndex)返回一个子字符串，从beginIndex开始到endIndex - 1
                        symbol = line.substring(i, j+1);
                        if(!Terminal.contains(symbol)) Terminal.add(symbol);
                        Production.get(count).add(symbol);
                        //System.out.println(">>>"+symbol);
                        //另 i = '"'
                        i=j;
                    }//end if:read symbol"…"
                }//end for:read one line
                //从终结符号集合里面去除非终结符号
                Iterator iterator = Nonterminal.iterator();
                while (iterator.hasNext()) {  
                    String tempnonterminal = (String)iterator.next();
                    if(Terminal.contains(tempnonterminal))Terminal.remove(tempnonterminal);
                }
                //如果存在空串，将其移除
                if(Terminal.contains("\"\""))Terminal.remove("\"\"");
            }//end while:read a BNF
            //关闭bufferedReader
            bufferedReader.close();
        } catch (Exception e) {
            //  System.err.println("read errors :" + e);
        }
        
    }//end ReadBNF
    
    //计算非终结符号的First集
    static void getFirstSet(){
        //非终结符号
        Iterator iterator1 = Nonterminal.iterator();
        while (iterator1.hasNext()) { 
            FirstSet.put((String)iterator1.next(),new HashSet());
        }
        //计算非终结符的First集
        Set setFirst = FirstSet.keySet();
        for(Iterator iter = setFirst.iterator(); iter.hasNext();)
        {
            String key = (String)iter.next();
            FirstFunction(key);
        } 
    }//end static void getFirstSet
    
    //参数是要计算First集的非终结符号
    static void FirstFunction(String nonterminal){
        boolean haveEmptySymbol;
        //找到非终结符对应的产生式（可能存在多条产生式）
        for(int i = 0; i < Production.size(); i++)
        {
            //产生式的第一个符号即为该非终结符
            if(Production.get(i).get(0).equals(nonterminal))
            {
                //从产生式的右边的第一个符号开始
                for(int j = 1; j<Production.get(i).size(); j++)
                {
                    haveEmptySymbol = false;
                    String symbol = Production.get(i).get(j);
                    
                    //如果是空串，将其加入到First集并继续求解下一个符号
                    if(symbol.equals("\"\"")){
                        FirstSet.get(nonterminal).add(symbol);
                    }
                    //如果是终结符号，将其加入到First集，并结束对这条产生式的计算
                    else if(Terminal.contains(symbol)){
                        FirstSet.get(nonterminal).add(symbol);
                        break;
                    }
                    //非终结符号
                    else{
                        //递归求出该非终结符的First集
                        FirstFunction(symbol);
                        //将产生式右边的非终结符的First集加入到产生式左边的非终结符的First集中
                        Iterator iterator = FirstSet.get(symbol).iterator();
                        while (iterator.hasNext()) { 
                            String symbolToAdd = (String)iterator.next();
                            FirstSet.get(nonterminal).add(symbolToAdd);
                            //有空串
                            if(symbolToAdd.equals("\"\""))haveEmptySymbol = true;
                        }
                        //没有空串就终止
                        if(!haveEmptySymbol)break;
                    }
                }//end for 求解非终结符号一条产生式的First集合
            }//end if 判断产生式是否属于要求解的非终结符
        }//end for 找到要求解的非终结符号对应的产生式
    }//end FirstFunction
    
    //计算非终结符号的Follow集
    static void getFollowSet(){
        
        //找出非终结符号
        Iterator iterator1 = Nonterminal.iterator();
        while (iterator1.hasNext()) { 
            FollowSet.put((String)iterator1.next(),new HashSet());
        }
        try{
        	//起始符号的follow集添加$
            FollowSet.get(Nonterminal.get(0)).add("$");
        }catch(IndexOutOfBoundsException e){
        	
        }
        
        
        //保存每个非终结符号follow集的长度，用于判断是否需要再次求解Follow集
        //默认初始化为0
        int []Length = new int[Nonterminal.size()];
        Set setFollow = FollowSet.keySet();
        
        //计算非终结符的Follow集
        //多轮求解，每次判断与上一次的集合长度是否一致，一致则说明求解结束
        while(true){
            FollowFunction();
            boolean flag = true;
            int i = 0;
            //判断与上一次求解结果是否一致
            for(Iterator iter = setFollow.iterator(); iter.hasNext();)
            {
                //获取集合长度
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
    
    //一轮Follow集的计算
    static void FollowFunction(){
        boolean haveEmptySymbol;
        //求出所有产生式右侧的非终结符的Follow集
        for(int i = 0; i < Production.size(); i++)
        {
            //从产生式的右边的第一个符号开始
            for(int j = 1; j < Production.get(i).size(); j++)
            {
                String symbol = Production.get(i).get(j);
                //如果是非终结符
                if(Nonterminal.contains(symbol))
                {
                    //如果是产生式最后一个字符
                    if(j == Production.get(i).size()-1){
                        Iterator iterator1 = FollowSet.get(Production.get(i).get(0)).iterator();
                        while (iterator1.hasNext()) {
                            FollowSet.get(symbol).add((String)iterator1.next());
                        }
                    }
                    //该终结符后面的串
                    for(int k = j+1; k < Production.get(i).size(); k++)
                    {
                        haveEmptySymbol = false;
                        String nextSymbol = Production.get(i).get(k);
                        //如果是空串，不做处理
                        if(nextSymbol.equals("\"\"")){}
                        //如果是终结符
                        else if(Terminal.contains(nextSymbol)){
                            FollowSet.get(symbol).add(nextSymbol);
                            break;
                        }
                        //是非终结符
                        else{
                            //获取该非终结符的First集
                            Iterator iterator = FirstSet.get(nextSymbol).iterator();
                            while (iterator.hasNext()) {
                                String addSymbol = (String)iterator.next();
                                //是空串，不加入Follow集
                                if(addSymbol.equals("\"\"")){
                                    haveEmptySymbol = true;
                                    //如果是最后一个非终结符，将产生式左侧的非终结符的Follow集加入
                                    if(k == Production.get(i).size()-1){
                                        Iterator iterator1 = FollowSet.get(Production.get(i).get(0)).iterator();
                                        while (iterator1.hasNext()) {
                                            FollowSet.get(symbol).add((String)iterator1.next());
                                        }
                                    }
                                }
                                //不是空串，加入Follow集
                                else FollowSet.get(symbol).add(addSymbol);
                            }
                        }
                        //如果First集没有空串就可以结束
                        if(!haveEmptySymbol)break;
                    }//end for：求解该终结符后面的串的First集
                }//end if：处理一个非终结符号
            }//end for：处理一条产生式
        }//end for：处理所有产生式
    }//end FollowFunction
    
    //填写预测分析表
    static boolean fillAnalyzeTable(){
        //初始化预测分析表
        for(int i = 0; i < AnalyzeTable.length; i++)
            for(int j = 0; j < AnalyzeTable[i].length; j++)
                AnalyzeTable[i][j] = -1;
        
        boolean haveEmptySymbol;
        
        for(int i = 0; i < Production.size(); i++)
        {
            String rowSymbol = Production.get(i).get(0);
            //从产生式的右边的第一个符号开始
            for(int j = 1; j<Production.get(i).size(); j++)
            {
                haveEmptySymbol = false;
                String columnSymbol = Production.get(i).get(j);
                //如果是空串，不做处理
                if(columnSymbol.equals("\"\"")){
                    haveEmptySymbol = true;
                }
                //如果是终结符号，将该产生式加入到终结符号的预测分析表的表项中
                else if(Terminal.contains(columnSymbol)){
                    int row = Nonterminal.indexOf(rowSymbol);
                    int column = Terminal.indexOf(columnSymbol);
                    //在预测分析表填入相应的产生式序号
                    if(AnalyzeTable[row][column] == -1 || AnalyzeTable[row][column] == i)
                        AnalyzeTable[row][column] = i;
                    else return false;
                }
                //是非终结符号，将该产生式加入到非终结符号First集的预测分析表的表项中
                else {
                    Iterator iterator1 = FirstSet.get(columnSymbol).iterator();
                    while (iterator1.hasNext()) { 
                        String symbol = (String)iterator1.next();
                        int row = Nonterminal.indexOf(rowSymbol);
                        int column = Terminal.indexOf(symbol);
                        //如果包含空串
                        if(symbol.equals("\"\"")) haveEmptySymbol =true;
                        //在预测分析表填入相应的产生式序号
                        else {
                            if(AnalyzeTable[row][column] == -1 || AnalyzeTable[row][column] == i)
                                AnalyzeTable[row][column] = i;
                            else return false;
                        }
                    }
                }//end else 对非终结符号的处理
                
                //如果产生式左边可能为空，将该产生式加入到右侧非终结符的Follow集的预测分析表的表项中
                //左边为空：计算到最后一个符号并且它可为空
                if(haveEmptySymbol && j == Production.get(i).size()-1){
                    Iterator iterator2 = FollowSet.get(rowSymbol).iterator();
                    while (iterator2.hasNext()) { 
                        String symbol = (String)iterator2.next();
                        int row = Nonterminal.indexOf(rowSymbol);
                        int column;
                        if(symbol.equals("$"))//最后一列
                            column = Terminal.size();
                        else column = Terminal.indexOf(symbol);
                        //在预测分析表填入相应的产生式序号
                        if(AnalyzeTable[row][column] == -1 || AnalyzeTable[row][column] == i)
                            AnalyzeTable[row][column] = i;
                        else return false;
                    }
                }
                //如果不存在空串，则对本产生式的计算结束
                if(!haveEmptySymbol) break;
            }//end for 对一条产生式的处理
        }//end for 对所有产生式的处理
        return true;
    }// end fillForecastTable
    
    //读入要分析的单词流
    static void readSentence(String fileName){
        
        String line;
        
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //对读入句子进行处理
            while ((line = bufferedReader.readLine().trim()) != null) {
                Sentence.add(line);
            }//end while:read sentence
            //关闭bufferedReader
            bufferedReader.close();
        } catch (Exception e) {
            //  System.err.println("read errors :" + e);
        }
        
        //如果输入有空串，将其移除
        while(Sentence.contains("\"\"")){
            Sentence.remove("\"\"");
        }
        
        //在输入串末尾添加$
        Sentence.add("$");
        
    }//end readSentence
    
    //进行语法分析
    static void syntaxAnalyze(){
        boolean flag = true;
        int pointer = 0;
        //初始化栈，将起始符号和$压入栈中
        SyntaxAnalyzeStack.push("$");
        SyntaxAnalyzeStack.push(startSymbol);
        //栈顶符号
        String stackTop;
        int column;
        int row = 0;
        while(!SyntaxAnalyzeStack.empty())
        {
            stackTop = SyntaxAnalyzeStack.peek();
            String nextInput = Sentence.get(pointer);
            //System.out.println(stackTop + nextInput);
            
            //如果当前输入符号是$，则处于最后一列
            if(nextInput.equals("$"))
               column = Terminal.size();
            else column = Terminal.indexOf(nextInput);
            //当栈顶元素为非终结符号时更新行值
            if(Nonterminal.contains(stackTop))
                row = Nonterminal.indexOf(stackTop);
            
            
            try{
            	//System.out.println(stackTop + ":" + row + "        " + nextInput + ":" + column);
                //栈顶元素是当前输入符号（终结符），栈顶符号出栈，处理下一个输入符号
                if(stackTop.equals(Sentence.get(pointer))){
                    SyntaxAnalyzeStack.pop();
                    pointer++;
                }
                //栈顶符号是其他终结符，表示句子不符合本文法
                else if(Terminal.contains(stackTop)){
                    flag = false;
                    break;
                }
            	
            	//栈顶是非终结符，处于报错的条目，不符合本文法
                else if(AnalyzeTable[row][column] == -1){
                    flag = false;
                    break;
                }
                
                //栈顶是非终结符，找到对应的产生式，将栈顶符号弹出，替换为产生式右侧的符号
                else {
                    //获得对应的产生式序号
                    int productionNum = AnalyzeTable[row][column];
                    SyntaxAnalyzeStack.pop();
                    //将产生式右侧的符号依次压入栈中，注意顺序
                    for(int j = Production.get(productionNum).size()-1; j >= 1; j--)
                    {
                        String symbol = Production.get(productionNum).get(j);
                        //将非空串压栈
                        if(!symbol.equals("\"\"")) SyntaxAnalyzeStack.push(symbol);
                    }
                }
            }catch(ArrayIndexOutOfBoundsException e){
            	
            }
        }//end while
        
        if(flag) System.out.println("yes");
        else System.out.println("no");
    }//end syntaxAnalyze
    
  //输出非终结符、终结符、产生式、First集、Follow集、预测分析表
    static void outputBNF(){
        System.out.println("\n非终结符：");
        Iterator iterator1 = Nonterminal.iterator();
        while (iterator1.hasNext()) {  
            System.out.println(iterator1.next());
        }
        
        System.out.println("\n终结符：");
        Iterator iterator2 = Terminal.iterator();
        while (iterator2.hasNext()) {  
            System.out.println(iterator2.next());
        }
        
        //输出产生式
        System.out.println("\n产生式数目：" + Production.size());
        System.out.println("产生式：");
        for(int i = 0; i < Production.size(); i++)
        {
            for(int j =0; j < Production.get(i).size(); j++)
            {
                System.out.print(Production.get(i).get(j)+" ");
            }
            System.out.println();
        }
        
        //输出非终结符号的First集
        Set setFirst = FirstSet.keySet();
        System.out.println("\nFirst集：");
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
        
        //输出非终结符号的Follow集
        Set setFollow = FollowSet.keySet();
        System.out.println("\nFollow集：");
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
        
        //输出预测分析表的表项
        System.out.println("\n预测分析表：");
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
    
    //读入的符号串
    static void outputSentence(){
        //输出读入的符号串
        System.out.println("\n读入的符号串：");
        for(int i = 0; i < Sentence.size(); i++)
            System.out.print(Sentence.get(i));
        System.out.println();
    }//end outputoutputSentence
    
}
