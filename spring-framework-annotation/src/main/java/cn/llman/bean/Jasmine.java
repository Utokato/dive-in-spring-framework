package cn.llman.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.BeanNameAware;

/**
 * @author
 * @date 2018/12/29
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Jasmine implements BeanNameAware {

    private String id;
    private String name;
    private Integer num;

    public void setBeanName(String str) {
        this.id = str;
    }
}