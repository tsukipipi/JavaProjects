/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nfa;

/**
 *
 * @author pipi
 */
public class Move {
    
    //move是状态迁移函数
    //accept_state是接受状态集
    //word是读入的字符串
    static boolean recognizeString(int move[][][], int accept_state[], String word){
        
        return match(0,move,accept_state,word,0);
         
    }
    
    static boolean match(int curstate,int move[][][], int accept_state[], String word, int curchar){
        
        //要先判断是否有空串再判断是否达到最后的位置
        //因为到达最后的位置之后可能可以经过空串到达接受状态
        
        int nextstate;
        
        //存在经过空串可以到达的边
        for(int i=0; i<move[curstate][0].length; i++)
        {
            //获得下一个状态
            nextstate = move[curstate][0][i];
            //匹配下一个字符，如果该字符是我们所要找的字符，则返回true
            if(match(nextstate,move,accept_state,word,curchar))
                return true;
        }
        
        
        //递归终止条件，读取到最后一个位置
        if(curchar == word.length()){
            //获取接受状态的个数
            int length = accept_state.length;
            //判断最终状态state是否在接受状态中
            for(int i = 0; i<length; i++)
                if(curstate == accept_state[i])return true;
            //如果不是，则回溯
            return false;
        }
        
        
        //读取下一个字符
        char c = word.charAt(curchar);
        int character = c - 'a' + 1;
        
        //对于当前状态的字符，逐个匹配他的下一个状态，指导找到正确的路径
        for(int i=0; i<move[curstate][character].length; i++){
            //获得下一个状态
            nextstate = move[curstate][character][i];
            //匹配下一个字符，如果该字符是我们所要找的字符，则返回true
            if(match(nextstate,move,accept_state,word,curchar+1))
                return true;
        }
        //不存在可迁移状态（对应的状态迁移函数为空）
        return false;
    }
    
}
