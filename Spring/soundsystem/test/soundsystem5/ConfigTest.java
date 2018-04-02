package soundsystem5;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

/**
 * 使用 Spring 的 SpringJUnit4ClassRunner ，在测试开始时会自动创建Spring的应用上下文
 * ContextConfiguration 注释：告诉这个类在 CDPlayerConfig 中加载配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class ConfigTest {

    @Autowired
    @Qualifier("cdPlayer")
    private MediaPlayer player;

    @Autowired
    @Qualifier("sgtPeppers")
    private CompactDisc cd1;

    @Autowired
    @Qualifier("BlankDisc")
    private CompactDisc cd2;

    @Test
    public void cdShouldNotBeNull() {
        //断言是否为null
        assertNotNull(cd1);
        assertNotNull(cd2);
        assertNotNull(player);
    }

}