package soundsystem;

import static junit.framework.TestCase.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 使用 Spring 的 SpringJUnit4ClassRunner ，在测试开始时会自动创建Spring的应用上下文
 * ContextConfiguration 注释：告诉这个类在 CDPlayerConfig 中加载配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CDPlayerConfig.class)
public class CDPlayerTest {

    //Autowired 注释：
    @Autowired
    private CompactDisc cd;

    @Test
    public void cdShouldNotBeNull(){
        //断言cd是否为null。
        //若cd不为null，即Spring能发现CompactDisc类，自动在Spring上下文创建其bean并注入到测试代码的cd
        assertNotNull(cd);
    }

}