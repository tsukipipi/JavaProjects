
package Graph;

import java.awt.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author pipi
 */
public class MyGraph {
    
    //定义基类引用数据类型
    public Vector <MyShape> shape=new Vector<MyShape>();
    
    public void displayAll(Graphics g)
    {
        for(MyShape x:shape)
            x.paint(g);
    }
    
    public synchronized boolean add(MyShape g) {
        return shape.add(g);
    }

    public synchronized MyShape remove(int index) {
        return shape.remove(index);
    }

    public synchronized MyShape get(int index) {
        return shape.get(index);
    }

    public synchronized int size() {
        return shape.size();
    }
    
    
    public void Savetxt(File f) throws IOException {
        
        //BufferedWriter(Writer out) 创建一个使用默认大小输出缓冲区的缓冲字符输出流。
        //FileWriter 用于写入字符流
        BufferedWriter bufferfile=new BufferedWriter(new FileWriter(f));
        for(MyShape shape:shape)
        {
            bufferfile.write(shape.toShortString());
            //写入行分隔符
            bufferfile.newLine();
            //刷新该流的缓冲
            bufferfile.flush();
        }
        bufferfile.close();
        
    }
    
    
    public void Readtxt(File f) throws IOException {
        
        //BufferedReader(Reader in) 创建一个使用默认大小输入缓冲区的缓冲字符输入流。
        //FileReader 用于读取字符流。
        //FileReader(File file) 创建一个在给定文件中读取数据的 FileReader。
        BufferedReader bufferfile=new BufferedReader(new FileReader(f));
        
        shape.clear();
        String GraphData=null;
        
        while(true) {
            
            GraphData=bufferfile.readLine();
            if(GraphData == null)break;
            
            if(GraphData.startsWith("C"))
                shape.add(new Circle(GraphData));
            
            else if(GraphData.startsWith("R"))
                shape.add(new Rectangle(GraphData));
            
            else if(GraphData.startsWith("T"))
                shape.add(new Triangle(GraphData));
            
            else if(GraphData.startsWith("S"))
                shape.add(new Square(GraphData));
            
            else if(GraphData.startsWith("P"))
                shape.add(new Pentagon(GraphData));
            
            else if(GraphData.startsWith("L"))
                shape.add(new Line(GraphData));
            
            else shape.add(new Text(GraphData));
        }
        
        bufferfile.close();
    }
    
    
    public void SaveObject(File f) throws IOException {
        
        ObjectOutputStream objectfile=new ObjectOutputStream(new FileOutputStream(f));
        for(MyShape c:shape)
        {
            objectfile.writeObject(c);
            objectfile.flush();
        }
        objectfile.close();
    }
    
    
    public void ReadObject(File f) throws Exception {
        
        ObjectInputStream objectfile=new ObjectInputStream(new FileInputStream(f));
        shape.clear();
        Object graph=null;
        try {
                
            while(true)
            {
                graph=(MyShape)objectfile.readObject();
                
                if(graph instanceof Circle)
                    shape.add((Circle)(graph));
            
                else if(graph instanceof Rectangle)
                    shape.add((Rectangle)(graph));
            
                else if(graph instanceof Triangle)
                    shape.add((Triangle)(graph));
            
                else if(graph instanceof Square)
                    shape.add((Square)(graph));
            
                else if(graph instanceof Pentagon)
                    shape.add((Pentagon)(graph));
            
                else if(graph instanceof Line)
                    shape.add((Line)(graph));
            
                else if(graph instanceof Text)
                    shape.add((Text)(graph));
            
            }//end while
                
        }//end try
        //读入结束抛出异常EOFException
        catch(EOFException e){
            System.out.println("图形对象读入结束");
        }
        objectfile.close();
    }
    
}
