package com.sg.cristina;

import com.sg.cristina.dao.UserDao;
import com.sg.cristina.entity.SgUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: jiangwei
 * @Date: 2019/4/22
 * @Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJPA {

    @Autowired
    UserDao userDao ;

    @Test
    public void t1(){
        Optional<SgUser> user = userDao.findById(1);
        System.out.println(user);

    }

    @Test
    public void t2(){
        List<SgUser> list= new ArrayList();
        String username = "test";
        String pwd = "123456";
        String mobile = "1234567890";
        for (int i =0 ;i<10;i++){
            username += i ;
            pwd += i ;
            mobile += i ;
            SgUser user = new SgUser(username, pwd, mobile);
            list.add(user);
        }
        userDao.saveAll(list);
        System.out.println("success");
    }

}
