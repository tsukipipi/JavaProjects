package soundsystem5;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CDConfig {

    @Bean
    //bean的名称为sgtPeppers
    public CompactDisc sgtPeppers(){
        return new SgtPeppers();
    }

}
