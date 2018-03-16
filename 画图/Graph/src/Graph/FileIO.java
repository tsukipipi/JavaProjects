
package Graph;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author pipi
 */
public class FileIO {
    
    public static String getFileExt(File file)
    {   
        String fileExtension=null;
        // String trim() 返回字符串的副本，忽略前导空白和尾部空白。
        String name=file.getName().trim();
        //String substring(int beginIndex) 返回一个新的字符串，它是此字符串的一个子字符串。
        //获得文件名后缀
        fileExtension=name.substring(name.indexOf('.')+1);
        return fileExtension;
    }
    
    
    static public  File getImageFile(Container c)
    {
        //static String[] getWriterFileSuffixes() 
        //Returns an array of Strings listing all of the file suffixes associated with the formats understood by the current set of registered writers.
        final String[] suf=javax.imageio.ImageIO.getWriterFileSuffixes();
        
        //创建文件选择器
        JFileChooser filechooser = new JFileChooser();
        
        //设置当前目录
        filechooser.setCurrentDirectory(new File("."));
        
        //public void setAcceptAllFileFilterUsed(boolean b)
        //确定是否将 AcceptAll FileFilter 用作可选择过滤器列表中一个可用选项。
        //如果为 false，则从可用文件过滤器的列表中移除 AcceptAll 文件过滤器。如果为 true，则 AcceptAll 文件过滤器将成为可用的文件过滤器。 
        filechooser.setAcceptAllFileFilterUsed(false);
        
        //public void addChoosableFileFilter(FileFilter filter)
        //向用户可选择的文件过滤器列表添加一个过滤器。        
        //显示所有文件
        filechooser.addChoosableFileFilter(new FileFilter(){
            
        public boolean accept(File file) { 
            if(file.isFile())
            {
                for(String s:suf)
                    if(getFileExt(file).equalsIgnoreCase(s)) return true;
                return false;
            }
            return true;
        }
        
        public String getDescription() {
            return "image图像文件(*.*)";
        }
        
        });
        
        //int showSaveDialog(Component parent)  弹出一个 "Save File" 文件选择器对话框
        filechooser.showSaveDialog(c);
        
        //File getSelectedFile()返回选中的文件
        return filechooser.getSelectedFile();
    }
    
    
    static public  File getFile(Container c)
    {
        
        //创建文件选择器
        JFileChooser filechooser = new JFileChooser();
        
        //设置当前目录
        filechooser.setCurrentDirectory(new File("."));
        
        
        //int showSaveDialog(Component parent)  弹出一个 "Save File" 文件选择器对话框
        filechooser.showSaveDialog(c);
        
        //File getSelectedFile()返回选中的文件
        return filechooser.getSelectedFile();
    }
}
