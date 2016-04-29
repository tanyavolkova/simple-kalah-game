package com.levi9.tvolkova;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tanyavolkova.examples.KalahGameApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KalahGameApplication.class)
@WebAppConfiguration
public class KalahGameApplicationTests {

	@Test
	public void contextLoads() {
	}

}
