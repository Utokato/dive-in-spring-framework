package cn.llman.aop;

/**
 * @author
 * @date 2018/12/24
 */
public class MathCalculator {

    public int division(int i, int j) {
        System.out.println("--> The method named MathCalculator.division has called.");
        return i / j;
    }

}
