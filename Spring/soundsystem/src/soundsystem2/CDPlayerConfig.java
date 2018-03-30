package soundsystem2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 这是一个配置类，启用组件扫描
 * Configuration 注解：表明这个类是一个配置类
 * ComponentScan 注解：在Spring中启用组件扫描，默认扫描与配置类相同的包。
 *                     此时CDPlayerConfig类在soundsystem包中，Spring会扫描这个包以及其子包下所有带 Component 注解的类。
 */
@Configuration
public class CDPlayerConfig {

    //@Bean 注解：告诉Spring当前方法会返回一个对象，该对象要注册为Spring应用上下文中的bean
    @Bean
    public SgtPeppers sgtPeppers(){
        return new SgtPeppers();
    }

    @Bean
    public CDPlayer cdPlayer(CompactDisc compactDisc){
        return new CDPlayer(compactDisc);
    }

}
