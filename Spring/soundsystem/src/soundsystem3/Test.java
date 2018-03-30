package soundsystem3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String [] args){

        //声明Spring应用上下文，采用xml配置
        ApplicationContext context = new ClassPathXmlApplicationContext("\\soundsystem3\\Config.xml");
        CompactDisc cd = (CompactDisc) context.getBean("compactDisc");
        MediaPlayer player = (MediaPlayer) context.getBean("cdPlayer");
        cd.play();
        player.play();

    }

}
