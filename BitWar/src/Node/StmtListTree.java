package Node;

import Exception.*;
import Compiler.TokenList;

import java.util.Map;

@SuppressWarnings("all")
public class StmtListTree implements Tree{
    //stmt子树的链头和链尾
    private StmtTree firstStmt;
    private StmtTree cursor;

    public StmtListTree(){
        firstStmt = null;
    }

    //添加 stmt 子树
    public void addStmt(StmtTree tree){
        //设置第一棵 stmt 子树
        if(firstStmt == null) firstStmt = tree;
        //设置 next 属性
        else cursor.setNextStmt(tree);
        //游标指向 stmt 子树链表的尾部
        cursor = tree;
    }

    //根据单词流生长语法树的 StmtListTree 的子树: StmtTree
    @Override
    public void grow(TokenList tokens) throws CompileException {
        //stmt-list -> {stmt} stmt可以出现多次
        while (true){
            StmtTree stmtTree = new StmtTree();
            stmtTree.grow(tokens);
            //获取当前stmtTree的condition，分为 if、while、return、assign、null
            if(stmtTree.getCondition().equals("null")) break;
            //将所有的 stmtTree 串成链式结构
            else addStmt(stmtTree);
        }
    }

    //运行当前策略对应的语法树
    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        return firstStmt.run(localVal);
    }

    //输出第一棵stmt子树的单词流
    @Override
    public void print(int deep) {
        firstStmt.print(deep);
    }
}