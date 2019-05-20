package com.sg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @Author: jiangwei
 * @Date: 2019-05-07
 * @Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SptringbootTest {

    /**
     * ArrayList有序，可重复，可为空
     */
    @Test
    public void test1() {
        List list = new ArrayList();
        Iterator iterator = list.iterator();
        list.add(null);
        list.add(1);
        list.add(null);
        System.out.println(list);

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * LinkedList有序，可重复，可为空
     */
    @Test
    public void test2() {
        List list = new LinkedList();
        list.add(null);
        list.add(1);
        list.add(2);
        list.add(null);
        list.add(2);
        System.out.println(list);
    }

    /**
     * Set存储  无序，不可重复，可以为空
     */
    @Test
    public void test3() {
        Set set = new HashSet<>();
        set.add(null);
        set.add(1);
        set.add(2);
        set.add(3);
        System.out.println(set);
        set.add(null);
        System.out.println(set);
    }

    /**
     * Set存储  无序，不可重复，可以为空
     */
    @Test
    public void test4() {
        Set set = new TreeSet();
        set.add(null);
        set.add(1);
        set.add(2);
        set.add(3);
        System.out.println(set);
        set.add(null);
        System.out.println(set);
    }


    /**
     * Map 的键可以为空，且仅有一个，key不能重复
     * Map的值可以为空，可以为多个，值可以重复
     */
    @Test
    public void test5() {
        Map map = new HashMap();
        String s = "jiangwei";
        map.put(1, "jiangwei");
        map.put(2, "jiangwei");
        map.put(3, "jiangwei");
        map.put(4, s);
        map.put(5, null);
        map.put(6, null);
        map.put(null, "jiangwei");
        map.put(null, "yingying");
        map.put(3, "yingying");
        System.out.println(map.toString());
    }

    @Test
    public void test6() {
        List list = new LinkedList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        System.out.println(list);
        ListIterator iterator = list.listIterator();
        while (iterator.hasNext()){
            Integer next = (Integer)iterator.next();
            if (next==3){
                iterator.set(22);
            }
        }
        System.out.println(list);
    }

    @Autowired
    UserService userService ;

    @Test
    public void trst6(){
        SgUser jiangwei = userService.selectByUsername("jiangwei");
        System.out.println(jiangwei);
    }

}
