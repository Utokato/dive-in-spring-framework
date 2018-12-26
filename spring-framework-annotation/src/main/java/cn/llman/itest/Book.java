package cn.llman.itest;

/**
 * @author
 * @date 2018/12/25
 */
public class Book {

    private String name;

    private Integer page;

    public Book(String name, Integer page) {
        System.out.println("Bingo! The constructor of book is running.");
        System.out.println();
        this.name = name;
        this.page = page;
    }

    public void init() {
        System.out.println("Try to work with init method.");
        this.setName("War and Peace");
        this.setPage(499);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "Book[" +
                "name='" + name + '\'' +
                ", page=" + page +
                ']';
    }
}
