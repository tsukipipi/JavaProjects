package Compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Filer {

    //读入文件的内容并返回
    public static String readFile(String path){
        String result = "";
        try{
            File file = new File(path);
            InputStreamReader in = new InputStreamReader(new FileInputStream(file));
            BufferedReader buf = new BufferedReader(in);
            String line = null;
            while ((line = buf.readLine()) != null){
                result += line + " ";
            }
            in.close();
            buf.close();
        }catch (Exception e){
            System.err.println(e);
        }
        return result;
    }

}
