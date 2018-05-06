package Node;

import Exception.*;
import Compiler.TokenList;

import java.util.Map;

import static Compiler.TokenJudge.isId;

@SuppressWarnings("all")
public class StmtTree implements Tree{
    private IfTree ifTree;
    private WhileTree whileTree;
    private AssignTree assignTree;
    private ReturnTree returnTree;
    //指向下一棵 stmt 子树
    private StmtTree nextStmt;
    //condition 区分为 if、while、return、assign、null
    private String condition;

    public StmtTree(){
        nextStmt = null;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    //判断是否还有下一棵stmt子树（是否到达链尾）
    public boolean hasNext(){
        return nextStmt != null;
    }

    public void setNextStmt(StmtTree nextStmt) {
        this.nextStmt = nextStmt;
    }

    //根据单词流生长语法树的 StmtTree 的子树: if、while、return、assign
    @Override
    public void grow(TokenList tokens) throws CompileException {
        //stmt -> if-stmt | while-stmt | assign-stmt | return-stmt
        //watch()查看下一个单词
        String token = tokens.watch();
        if(token.equals("if")){
            ifTree = new IfTree();
            ifTree.grow(tokens);
            setCondition("if");
        }
        else if(token.equals("while")){
            whileTree = new WhileTree();
            whileTree.grow(tokens);
            setCondition("while");
        }
        else if(token.equals("return")){
            returnTree = new ReturnTree();
            returnTree.grow(tokens);
            setCondition("return");
        }
        else if(isId(token)){
            assignTree = new AssignTree();
            assignTree.grow(tokens);
            setCondition("assign");
        }
        else {
            setCondition("null");
        }
    }

    @Override
    public Integer run(Map<String, Integer> localVal) throws RunningException {
        Integer val = null;
        switch (getCondition()){
            case "if":
                val = ifTree.run(localVal);
                if(val==null) return hasNext()?nextStmt.run(localVal):null;
                else return val;
            case "while":
                val = whileTree.run(localVal);
                if(val==null) return hasNext()?nextStmt.run(localVal):null;
                else return val;
            case "assign":
                assignTree.run(localVal);
                return hasNext()?nextStmt.run(localVal):null;
            case "return": return returnTree.run(localVal);
            default:
                throw new RunningException("stmt grammar error");
        }
    }

    //输出stmt子树链表的单词流
    @Override
    public void print(int deep) {
        //根据生长语法树(grow)时设置的condition来判断是哪一种情况
        switch (getCondition()){
            case "if": ifTree.print(deep);break;
            case "while": whileTree.print(deep);break;
            case "assign": assignTree.print(deep);break;
            case "return": returnTree.print(deep);break;
            default:;
        }
        //输出下一棵stmt子树的单词流
        if(hasNext()) nextStmt.print(deep);
    }
}
