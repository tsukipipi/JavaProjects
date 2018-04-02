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
@Import(CDConfig.class)
public class CDPlayerConfig {

    @Bean
    //这里的参数sgtPeppers是bean的名称
    public CDPlayer cdPlayer(CompactDisc sgtPeppers){
        return new CDPlayer(sgtPeppers);
    }

}
