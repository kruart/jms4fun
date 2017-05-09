package ua.kruart.jms.advanced_features.message_persistence;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSReceiver implements MessageListener {

	public JMSReceiver() {
		try {
			QueueConnection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createQueueConnection();
			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_TRADE.Q");
			connection.start();
			QueueReceiver receiver = session.createReceiver(queue);
			receiver.setMessageListener(this);

			System.out.println("Waiting for messages...");
		} catch (Exception up) {
			up.printStackTrace();
		}
	}

	public void onMessage(Message message) {
		try {
			TextMessage msg = (TextMessage) message;
			System.out.println("Receiving JMS " + msg.getText());

			if (msg.getLongProperty("count") == 1000) {
				long endTime = new Long(new java.util.Date().getTime());
				System.out.println("\nElapsed Time: " + (endTime - msg.getLongProperty("startTime")));
			}
		} catch (Exception up) {
			up.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			Object o = new JMSReceiver();
			synchronized (o) {
				o.wait();
			}
		} catch (Exception up) {
			up.printStackTrace();
		}

	}

}
