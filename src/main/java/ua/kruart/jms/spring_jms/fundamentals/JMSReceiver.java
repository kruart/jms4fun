package ua.kruart.jms.spring_jms.fundamentals;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JMSReceiver {

	public static void main(String[] args) throws Exception {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jms_fundamentals/app-context.xml");
		System.exit(0);
	}
}
