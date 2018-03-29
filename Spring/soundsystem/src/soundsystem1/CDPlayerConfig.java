package soundsystem1;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 这是一个配置类，启用组件扫描
 * Configuration 注解：
 * ComponentScan 注解：在Spring中启用组件扫描，默认扫描与配置类相同的包。
 *                     此时CDPlayerConfig类在soundsystem包中，Spring会扫描这个包以及其子包下所有带 Component 注解的类。
 */
@Configuration
@ComponentScan
public class CDPlayerConfig {
}
