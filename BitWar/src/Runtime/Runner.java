package Runtime;

import Exception.*;
import Compiler.*;
import java.io.File;

public class Runner {

    public Runner(){}

    //根据路径载入策略文件
    public boolean load(String path) throws CompileException{
        File dir = new File(path);
        //判断路径是否存在以及是否为一个目录
        if (dir.exists() && dir.isDirectory()){
            //获取当前文件夹下的所有文件和文件夹
            File []files = dir.listFiles();
            for (File file:files){
                //找出以 .txt 结尾的文件（策略文件）
                if (file.getName().endsWith(".txt")){
                    //inputProgram() 将生成的语法树和对应的文件名存储到GlobalValue中
                    //Parser.parse(file.getPath()) 根据文件路径读取文件内容拆分成单词再生成语法树
                    GlobalValue.inputProgram(file.getName(), Parser.parse(file.getPath()));
                }
            }
            return true;
        }

        return false;
    }

    public Integer run(String name, int order) throws RunningException{
        return GlobalValue.getProgram(name).run(order);
    }

    public Integer run(String name) throws RunningException{
        return GlobalValue.getProgram(name).run();
    }
}
