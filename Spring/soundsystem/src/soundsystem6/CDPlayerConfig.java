package soundsystem6;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CDPlayerConfig {

    @Bean
    //这里的参数sgtPeppers是bean的名称
    //bean的名称为cdPlayer
    public CDPlayer cdPlayer(CompactDisc sgtPeppers){
        return new CDPlayer(sgtPeppers);
    }

}
