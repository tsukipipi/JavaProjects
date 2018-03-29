package soundsystem1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String [] args){

        //声明Spring上下文，采用java配置类
        //ApplicationContext context = new AnnotationConfigApplicationContext(CDPlayerConfig.class);
        //声明Spring应用上下文，采用xml配置
        ApplicationContext context = new ClassPathXmlApplicationContext("\\soundsystem1\\CDPlayerConfig.xml");
        SgtPeppers cd = (SgtPeppers) context.getBean("lonelyHeartsClub");
        cd.play();

    }

}
