package ua.kruart.jms.spring_jms.fundamentals;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

public class JMSSender {

	public static void main(String[] args) throws Exception {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jms_fundamentals/app-context.xml");
		JmsTemplate jmsTemplate = (JmsTemplate)ctx.getBean("jmsTemplate");

		// first way to send message
		//		jmsTemplate.convertAndSend("BUY AAPL 2500 SHARES");		//just send message

		// second way to send message and message property
//		MessageCreator mc = (session -> {	//send message with property
//            TextMessage msg = session.createTextMessage("BUY AAPL 2500 SHARES");
//            msg.setStringProperty("Trader", "Arthur");
//            return msg;
//        });
//
//		jmsTemplate.send(mc);

		// third way to send message and message property
		MessagePostProcessor mpp = msg -> {
            msg.setStringProperty("Trader", "Arthur");
            return msg;
        };

		jmsTemplate.convertAndSend((Object) "BUY AAPL 2500 SHARES", mpp);
		System.out.println("Message sent...");

		System.exit(0);
	}
}
