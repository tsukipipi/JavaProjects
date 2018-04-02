package soundsystem5;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Import(CDPlayerConfig.class)
@ImportResource("classpath:soundsystem5/Config.xml")
public class Config {
}
