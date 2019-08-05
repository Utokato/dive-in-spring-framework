package cn.llman.newTest;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author
 * @date 2019/2/26
 */
@Data
public class Father {
    @Value("${person.firstName}")
    private String firstName;
    @Value("${person.lastName}")
    private String lastName;
}
