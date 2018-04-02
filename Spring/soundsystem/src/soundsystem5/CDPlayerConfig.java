package soundsystem5;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 这是一个配置类，启用组件扫描
 * Configuration 注解：表明这个类是一个配置类
 * Import 注解：导入另一个类
 */
@Configuration
//@Import(CDConfig.class)
public class CDPlayerConfig {

    //@Bean 注解也可以实现setter方法注入
    @Bean
    public CDPlayer cdPlayer(CompactDisc compactDisc){
        CDPlayer cdPlayer = new CDPlayer();
        cdPlayer.setCompactDisc(compactDisc);
        return cdPlayer;
    }

}
