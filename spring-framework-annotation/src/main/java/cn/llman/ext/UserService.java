package cn.llman.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author
 * @date 2018/12/26
 */
@Service
public class UserService {

    @EventListener({ApplicationEvent.class})
    public void listen(ApplicationEvent event) {
        System.out.println("A listener in UserService, that listen is: " + event);
    }
}
