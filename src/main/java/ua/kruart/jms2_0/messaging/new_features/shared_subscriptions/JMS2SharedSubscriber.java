package ua.kruart.jms2_0.messaging.new_features.shared_subscriptions;

import com.sun.messaging.ConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 29.04.2017.
 */
public class JMS2SharedSubscriber implements MessageListener {

    public JMS2SharedSubscriber() {
        ConnectionFactory cf = new ConnectionFactory();
        try  {

            JMSContext jmsContext = cf.createContext();
            Topic topic = jmsContext.createTopic("EM_JMS2_TRADE.T");

            JMSConsumer jmsConsumer = jmsContext.createConsumer(topic);

            //register message listener
            jmsConsumer.setMessageListener(this);
            System.out.println("waiting on messages ...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(message.getBody(String.class));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            new JMS2SharedSubscriber();
        }).start();


    }
}
