package Runtime;

import Exception.*;
import Compiler.*;
import java.io.File;

public class Runner {

    public Runner(){}

    //根据路径载入策略文件
    public boolean load(String path) throws CompileException{
        File dir = new File(path);
        //判断路径是否存在以及是否为一个目录，载入目录下的全部文件
        if (dir.exists() && dir.isDirectory()){
            //获取当前文件夹下的所有文件和文件夹
            File []files = dir.listFiles();
            for (File file:files){
                //找出以 .txt 结尾的文件（策略文件）
                if (file.getName().endsWith(".txt")){
                    //inputProgram() 将生成的语法树和对应的文件名存储到GlobalValue中
                    //Parser.parse(file.getPath()) 根据文件路径读取文件内容拆分成单词生成语法树，返回生成的语法树
                    GlobalValue.inputProgram(file.getName(), Parser.parse(file.getPath()));
                }
            }
            return true;
        }
        //载入单个文件
        else if (dir.exists()){
            if (dir.getName().endsWith(".txt")){
                GlobalValue.inputProgram(dir.getName(), Parser.parse(dir.getPath()));
            }
            return true;
        }
        return false;
    }

    //运行某个策略  order表示策略运行的先后顺序
    public Integer run(String name, int order) throws RunningException{
        return GlobalValue.getProgram(name).run(order);
    }

    //运行某一个策略(的语法树)
    public Integer run(String name) throws RunningException{
        //GlobalValue.getProgram(name) 返回值是 program
        return GlobalValue.getProgram(name).run();
    }
}
