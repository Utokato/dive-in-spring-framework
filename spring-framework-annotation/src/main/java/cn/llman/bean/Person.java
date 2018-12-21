package cn.llman.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author
 * @date 2018/12/18
 */
public class Person {

    /**
     * 使用@Value {@link Value}赋值：
     * 1. 基本数值
     * 2. SPEL:#{}
     * 3. ${} 取出配置文件(.properties文件)中的值，也就是运行环境中的值(context中的 environment值)
     */

    @Value("lma")
    private String name;
    @Value("#{27-2}")
    private Integer age;
    @Value("${person.nickName}")
    private String nickName;

    public Person(String name, Integer age, String nickName) {
        this.name = name;
        this.age = age;
        this.nickName = nickName;
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
