package soundsystem6;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String [] args){
        //声明Spring应用上下文，采用xml配置
        ApplicationContext context = new ClassPathXmlApplicationContext("soundsystem6/Config.xml");
        CompactDisc cd1 = (CompactDisc) context.getBean("sgtPeppers");
        CompactDisc cd2 = (CompactDisc) context.getBean("blankDisc");
        //声明Spring上下文，采用java配置类
//        ApplicationContext context1 = new AnnotationConfigApplicationContext(Config.class);
//        MediaPlayer player = (MediaPlayer) context1.getBean("cdPlayer");
        cd1.play();
        cd2.play();
//        player.play();
    }

}
