package cn.llman.controller;

import cn.llman.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 * @date 2018/12/28
 */
@Controller
public class HelloController {

    @Autowired
    HelloService helloService;

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return helloService.sayHello("marlonn");
    }

    /**
     * 根据配置的jsp视图解析器，会找到：/WEB-INF/views/success.jsp 文件
     * @return
     */
    @RequestMapping("/success")
    public String success(){
        return "success";
    }

    @RequestMapping("index")
    public String index(){
        return "index";
    }


}
