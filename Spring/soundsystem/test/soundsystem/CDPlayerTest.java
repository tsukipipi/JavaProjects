package soundsystem;

//import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
//import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
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

    @Rule
    //public final StandardOutputStreamLog log = new StandardOutputStreamLog();

    @Autowired
    private MediaPlayer player;

    @Autowired
    private CompactDisc cd;

    @Test
    public void cdShouldNotBeNull(){
        //断言cd是否为null。
        //若cd不为null，即Spring能发现CompactDisc类，自动在Spring上下文创建其bean并注入到测试代码的cd
        assertNotNull(cd);
    }

    @Test
    public void play(){
        player.play();
        //assertEquals("Playing Sgt. Pepper's Lonely Hearts Club Band" +
        //" by The Beatles\n",log.getLog());
    }

}