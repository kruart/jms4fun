package ua.kruart.jms.publish_subscribe_topic;


import com.sun.messaging.ConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 02.05.2017.
 */
public class JMS2SubscriberNonDurableShared implements MessageListener {

    public JMS2SubscriberNonDurableShared() {
        try {
            ConnectionFactory cf = new ConnectionFactory();
            JMSContext jmsContext = cf.createContext();

            Topic topic = jmsContext.createTopic("EM_TRADE_JMS2.T");
            jmsContext.createSharedConsumer(topic, "sub:123").setMessageListener(this);
            System.out.println("Waiting for message...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(message.getBody(String.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(JMS2SubscriberNonDurableShared::new).start();
    }
}
