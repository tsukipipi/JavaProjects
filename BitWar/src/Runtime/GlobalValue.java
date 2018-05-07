package Runtime;

import Node.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class GlobalValue {
    //存储编译好的语法树和对应的文件名
    private static Map<String, Program> programs = new HashMap<>();
    //存储对战双方的历史对战结果
    private static Map<String, Object> globalVal = new HashMap<>();

    //将编译好的语法树和对应的文件名保存起来
    public static void inputProgram(String name, ProgramTree program){
        programs.put(name, new Program(program));
    }

    //根据文件名获得对应的语法树
    public static Program getProgram(String name){
        return programs.get(name);
    }

    //设置globalval
    public static void setGlobalVal(String id, Object value){
        globalVal.put(id, value);
    }

    //获取globalval
    public static Object getGlobalVal(String id) {
        return globalVal.get(id);
    }

    //判断globalval是否包含 id
    public static boolean contain(String id){
        return globalVal.containsKey(id);
    }

    //清空globalval的值
    public static void clearGlobalVal(){
        globalVal.clear();
    }

    //返回所有编译好的语法树
    public static Map<String, Program> getPrograms(){
        return programs;
    }

    //输出programs保存的语法树对应的文件名
    public static void printAllPrograms(){
        if(programs.size()==0) System.out.println("no programs here");
        for(String key:programs.keySet()){
            System.out.println(key);
        }
    }

    //判断是否存在 name (文件名)以及对应的语法树
    public static boolean hasProgram(String name){
        return programs.keySet().contains(name);
    }
}
