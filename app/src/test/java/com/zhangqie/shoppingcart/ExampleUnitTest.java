package com.zhangqie.shoppingcart;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void radians() {
        double tan = Math.tan(Math.toRadians(25.0));
        BigDecimal bg = new BigDecimal(tan);
        double f1 = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        double atan = Math.atan(tan);
        bg = new BigDecimal(atan);
        double f2 = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        double cos = Math.cos(atan);
        bg = new BigDecimal(cos);
        double f3 = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();


        System.out.println(f1);
        System.out.println(f2);
        System.out.println(f3);
    }
}