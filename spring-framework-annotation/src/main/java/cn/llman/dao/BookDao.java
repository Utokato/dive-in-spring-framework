package cn.llman.dao;

import org.springframework.stereotype.Repository;

/**
 * @author
 * @date 2018/12/18
 */
@Repository // 名字默认是类名的首字母小写 bookDao
public class BookDao {

    private String label = "Default value";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "label='" + label + '\'' +
                '}';
    }
}
