package cn.llman.bean;

/**
 * @author
 * @date 2018/12/21
 */
public class Worker {

    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "car=" + car +
                '}';
    }
}
