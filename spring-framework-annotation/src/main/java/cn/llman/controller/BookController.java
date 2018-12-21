package cn.llman.controller;

import cn.llman.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author
 * @date 2018/12/18
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

}
