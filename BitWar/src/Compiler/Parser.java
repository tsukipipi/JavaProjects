package Compiler;

import Exception.*;
import Node.*;
import Runtime.*;
import java.io.File;
import static Compiler.Tokenizer.getTokens;

@SuppressWarnings("all")
public class Parser {

    //根据文件路径读取文件内容拆分成单词再生成语法树
    public static ProgramTree parse(String path) throws CompileException{
        File file = new File(path);
        //读入文件的内容并返回给code
        String code = Filer.readFile(path);
        //将文件的内容分解成一个一个Token
        TokenList tokens = getTokens(code);
        //根据单词流 TokenList 来编译生成一棵语法树
        ProgramTree programTree = parse(tokens);
        System.out.println(file.getName()+": compile success");
        //返回生成的语法树
        return programTree;
    }

    //根据单词流来生成语法树
    public static ProgramTree parse(TokenList tokens) throws CompileException{
        TokenList.init();
        //新建一棵语法树
        ProgramTree programTree = new ProgramTree();
        //根据单词流 TokenList 来生长这颗语法树
        programTree.grow(tokens);
        //返回生成的语法树
        return programTree;
    }

    //输出语法树上的单词
    public static void printWord(int deep, String word) {
        //根据层数来确定缩进
        for(int i = 0; i < deep; i++)
            System.out.print("\t");
        System.out.println(word);
    }

    //输出语法树
    public static void printTree(String name, Program program){
        System.out.println("------- " + name + " -------");
        program.printTree();
        System.out.println("------- end -------");
    }

}
