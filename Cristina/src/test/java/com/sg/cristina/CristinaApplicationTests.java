package com.sg.cristina;

import com.sg.cristina.dao.SgUserMapper;
import com.sg.cristina.entity.SgUser;
import com.sg.cristina.util.crypto.AES;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CristinaApplicationTests {

	@Autowired
	SgUserMapper userMapper ;

	@Test
	public void contextLoads() {
		String context = "thisisaes";
		AES aes = new AES("2930a074d6c34888a1bd173d0fd2a62d".toCharArray());
		byte[] plainData = new byte[0];
		try {
			plainData = context.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(aes.encrypt(plainData));
	}


	@Test
	public void testdb(){
		SgUser user = userMapper.selectByPrimaryKey(1);
		System.out.println("ok");

	}

}
