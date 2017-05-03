package ua.kruart.jms.publish_subscribe_topic;


import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;

/**
 * Created by kruart on 02.05.2017.
 */
public class JMS2SubscriberDurableShared implements MessageListener {

    public JMS2SubscriberDurableShared() {
        try {
            ConnectionFactory cf = new ConnectionFactory();
            JMSContext jmsContext = cf.createContext();

            Topic topic = jmsContext.createTopic("EM_TRADE_JMS2.T");
            jmsContext.createSharedDurableConsumer(topic, "sub_d:abc123").setMessageListener(this);
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
        new Thread(JMS2SubscriberDurableShared::new).start();
    }
}
