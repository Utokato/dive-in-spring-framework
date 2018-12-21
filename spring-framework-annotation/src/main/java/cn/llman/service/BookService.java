package cn.llman.service;

import cn.llman.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * @author
 * @date 2018/12/18
 */
@Service
public class BookService {


    // @Qualifier("bookDao")
    // @Autowired(required = false)
    // @Resource(name = "bookDao2")
    @Inject
    private BookDao bookDao;

    public void print() {
        System.out.println(bookDao + " in BookService used @Autowired to inject.");
    }
}
