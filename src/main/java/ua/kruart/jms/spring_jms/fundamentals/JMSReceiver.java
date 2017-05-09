package ua.kruart.jms.spring_jms.fundamentals;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.TextMessage;

public class JMSReceiver {

	public static void main(String[] args) throws Exception {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jms_fundamentals/app-context.xml");
		JmsTemplate jmsTemplate = (JmsTemplate)ctx.getBean("jmsTemplate");
		System.out.println("Waiting for messages...");

		// first way to receive message
//		String bodyMSG = (String) jmsTemplate.receiveAndConvert();	//just receive message
//		System.out.println(bodyMSG);

		// second way to receive message and message property
//		TextMessage msg = (TextMessage)jmsTemplate.receive();
		TextMessage msg = (TextMessage)jmsTemplate.receiveSelected("Trader = 'Arthur'"); //if I want to receive messages on from Arthur
		System.out.println(msg.getStringProperty("Trader") + ", " + msg.getText());

		System.exit(0);
	}
}
