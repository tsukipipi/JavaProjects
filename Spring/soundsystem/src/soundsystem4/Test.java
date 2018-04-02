package soundsystem4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String [] args){

        //声明Spring应用上下文，采用xml配置
        ApplicationContext context = new ClassPathXmlApplicationContext("soundsystem4/Config.xml");
        CompactDisc cd1 = (CompactDisc) context.getBean("compactDiscSgt");
        CompactDisc cd2 = (CompactDisc) context.getBean("compactDiscBlank");
        cd1.play();
        cd2.play();

    }

}
