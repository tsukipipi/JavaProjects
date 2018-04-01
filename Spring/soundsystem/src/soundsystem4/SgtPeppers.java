package soundsystem4;

import org.springframework.stereotype.Component;

/**
 * 这个类是一个播放歌曲SgtPeppers的CD播放器
 * Component 注解：表明这个类是一个组件类，并告知Spring创建这个类的bean
 */
@Component
public class SgtPeppers implements CompactDisc {
    //歌曲名
    private String title = "Sgt. Pepper's Lonely Hearts Club Band";
    //歌手名
    private String artist = "The Beatles";
    //播放这首音乐
    public void play(){
        System.out.println("Playing " + title + " by " + artist);
    }

}
