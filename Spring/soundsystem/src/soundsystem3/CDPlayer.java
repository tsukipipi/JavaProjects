package soundsystem3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CDPlayer implements MediaPlayer {
    private CompactDisc cd;

    //Autowired 注解：表明当Spring创建CDPlayer bean时会通过这个构造器来进行实例化并且传入一个CompactDisc类型的bean
    //可以用在setter等其他方法上没有限制
    @Autowired
    public CDPlayer(CompactDisc cd){
        this.cd = cd;
    }

    /*@Autowired
    public void setCompactDisc(CompactDisc cd){
        this.cd = cd;
    }*/

    public void play(){
        cd.play();
    }

}
