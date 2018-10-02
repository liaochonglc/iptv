package com.ido.iptv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class IptvApplicationTests {


	@Test
	public void contextLoads() {
        String s = "eyJhbGciOiJIUzI1NiJ9" +
                ".eyJleHAiOjE1Mzc4NzI4OTQsInN1YiI6Ind1anVuIiwicm9sZXMiOiJhZG1pbiJ9" +
                ".4tkM5985ad3xx4X9x4J7rjqPIwrIQJSf8fZdMdKiKTQ";

		System.out.println(new String(Base64.getDecoder().decode("4tkM5985ad3xx4X9x4J7rjqPIwrIQJSf8fZdMdKiKTQ")));
	}

}
