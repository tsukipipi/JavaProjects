/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nfa;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author pipi
 */
public class NFA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //NFA的状态n、字母表符号数m、包括э
        int n, m;
        
	Scanner in = new Scanner(System.in);
	StringTokenizer st = new StringTokenizer(in.nextLine());
	n = Integer.parseInt(st.nextToken());
	m = Integer.parseInt(st.nextToken());
	
        while (n != 0)
	{
            //状态迁移函数
            //第三维的迁移到的状态数目不确定
            int[][][] move = new int[n][m][];
            //读入状态迁移函数（填写状态迁移表）
            for(int i=0; i<n; i++)
            {
		String line = in.nextLine();			
                
                int k = 0;
		for (int j=0; j<m; j++)
		{
                    while (line.charAt(k) != '{') k++;
                    k++;
                    String states = "";
                    while (line.charAt(k) != '}')
                    {
			states = states + line.charAt(k);
			k++;
                    }
                    //trim():去掉字符串首尾的空格
                    states = states.trim();
                    //迁移状态不为空
                    if (states.length() > 0)
                    {
                        //根据逗号分割字符串获得各个状态
			String[] stateArray = states.split(",");
			move[i][j] = new int[stateArray.length];
                        //将迁移状态依次填入
			for (int l=0; l<move[i][j].length; l++) 
                            move[i][j][l] = Integer.parseInt(stateArray[l].trim());
                    }
                    //迁移状态为空
                    else move[i][j] = new int[0];
		}
            }
            
            //读入接受状态，用空格分隔开
            String[] temp = in.nextLine().split("\\s");
            int[] accept = new int[temp.length];
            //填入接受状态
            for (int i=0; i<accept.length; i++) 
                accept[i] = Integer.parseInt(temp[i]);
            
            //读入待匹配的字符串
            String word = in.nextLine();
            
            //在读到结束符之前，判断输入的字符串是否能匹配这个NFA
            while (word.compareTo("#") != 0)
            {
		if (Move.recognizeString(move, accept, word)) 
                    System.out.println("YES"); 
                else System.out.println("NO");
                //读入下一行字符串
		word = in.nextLine();
            }
            
            //读入新的状态数和字母表符号数
            st = new StringTokenizer(in.nextLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
	}
        
    }
    
}
