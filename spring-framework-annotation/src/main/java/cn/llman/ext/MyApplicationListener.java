package cn.llman.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date 2018/12/26
 */
@Component
public class MyApplicationListener implements ApplicationListener {

    /**
     * 当容器中发布该事件event时，会触发该方法
     *
     * @param event
     */
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("The received event is: " + event);
    }
}
