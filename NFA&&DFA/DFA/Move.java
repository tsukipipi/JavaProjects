/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfa;

/**
 *
 * @author pipi
 */
public class Move {
    

    //move是状态迁移函数
    //accept_state是接受状态集
    //word是读入的字符串
    static boolean recognizeString(int move[][], int accept_state[], String word){
        
        //起始状态为0
        int s = 0;
        //存放读取的字母
        char c;
        
        for(int j = 0; j<word.length(); j++){
            //读取下一个字母
            c = word.charAt(j);
            int i = c - 'a';
            //System.out.println(s + " " + move[s][i]);
            //迁移到下一个在状态
            s = move[s][i];
        }
        
        //获取接受状态的个数
        int length = accept_state.length;
        //判断s是否在接受状态中
        for(int i = 0; i<length; i++)
            if(s == accept_state[i])return true;
        
        //不在接受状态中
        return false;
    }
    
}
