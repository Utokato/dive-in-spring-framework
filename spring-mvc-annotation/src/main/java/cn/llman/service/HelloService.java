package cn.llman.service;

import org.springframework.stereotype.Service;

/**
 * @author
 * @date 2018/12/28
 */
@Service
public class HelloService {

    public String sayHello(String name) {
        return "Hello, " + name;
    }

}
