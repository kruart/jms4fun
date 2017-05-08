package ua.kruart.jms.basics.publish_subscribe_topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 02.05.2017.
 */
public class JMSSubscriberNonDurable implements MessageListener {

    public JMSSubscriberNonDurable() {
        try {
            TopicConnection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createTopicConnection();
            connection.start();
            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("EM_TRADE.T");
            TopicSubscriber subscriber = session.createSubscriber(topic);
            subscriber.setMessageListener(this);
            System.out.println("Waiting for message...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage) message;
            System.out.println(msg.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(JMSSubscriberNonDurable::new).start();
    }
}
