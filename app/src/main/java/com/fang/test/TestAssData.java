package com.fang.test;

import com.fang.model.Ass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bull on 16-6-5.
 */
public class TestAssData {

    public static List<Ass> getAssData(){

        List<Ass> list = new ArrayList<Ass>();
        Ass a1 = new Ass("张三", 10, 10, 1000);
        a1.setAss_id(1);
        Ass a2 = new Ass("李四", 33, 33, 3333);
        a2.setAss_id(2);
        Ass a3 = new Ass("王五", 20, 20, 2222);
        a3.setAss_id(3);
        list.add(a1);
        list.add(a2);
        list.add(a3);

        return list;
    }

}
