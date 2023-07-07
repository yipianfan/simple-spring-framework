package com.simple.common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Coffee implements Serializable {
    // 基本类型为final与String类型为final并且值用字面量时,才是在这里初始化数据,其他的都是在构造函数里初始化数据
    public final String name = "美式黑咖啡";
    public final int price = 19;

    public int capacity = 250;
    public String color = "Green";

    public final Integer density = 97;
    public final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public final Date date;

    public String producingArea = new String("America");

    public Coffee() {
        date = new Date();
        System.out.println("Coffee() called.");
    }
}
