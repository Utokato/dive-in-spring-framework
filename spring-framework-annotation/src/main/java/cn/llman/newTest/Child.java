package cn.llman.newTest;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author
 * @date 2019/2/26
 */
@Data
public class Child {
    @Value("#{father.firstName}")
    private String firstName;
    @Value("XiaoJun")
    private String lastName;
}
