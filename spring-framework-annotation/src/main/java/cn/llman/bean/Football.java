package cn.llman.bean;

import lombok.*;

/**
 * {@link FootballFactoryBean}
 * 通过FactoryBean的方式进行注册到容器中
 *
 * @author lma
 * @date 2019/08/08
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Football {
    private int id;
    private int size;
}
