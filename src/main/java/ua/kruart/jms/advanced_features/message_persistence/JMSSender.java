package ua.kruart.jms.advanced_features.message_persistence;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSSender {

	public static void main(String[] args) {

		try {
			QueueConnection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createQueueConnection();
			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_TRADE.Q");
			QueueSender sender = session.createSender(queue);
			//sender.setDeliveryMode(DeliveryMode.PERSISTENT);	 //1208 ms - it's price of the persistent message
			sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT); //169
			connection.start();

			long startTime = new java.util.Date().getTime();

			for (int i = 0; i < 1000; i++) {
				String body = "Message " + (i + 1);
				System.out.println("Sending JMS " + body);
				TextMessage msg = session.createTextMessage(body);
				msg.setLongProperty("startTime", startTime);
				msg.setLongProperty("count", (i + 1));
				sender.send(msg);
			}

			long endTime = new Long(new java.util.Date().getTime());
			System.out.println("\nElapsed Time: " + (endTime - startTime));

			connection.close();
			System.exit(0);
		} catch (Exception up) {
			up.printStackTrace();
		}
	}
}
