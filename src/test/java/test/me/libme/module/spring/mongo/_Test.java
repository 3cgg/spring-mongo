package test.me.libme.module.spring.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import(_TestConfig.class)
@SpringBootTest(properties={"spring.profiles.active=test,dev-error"},
	webEnvironment=WebEnvironment.RANDOM_PORT
		)
public class _Test {

	
	@Autowired
	private Environment environment;
	
	private String contextPath(){
		return environment.getProperty("server.contextPath");
	}
	
	@Test
	public void one(){
		System.out.println("server.contextPath : "+contextPath());
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
