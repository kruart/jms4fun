package ua.kruart.jms.new_features.shared_subscriptions;

import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Topic;
import java.text.DecimalFormat;

/**
 * Created by kruart on 29.04.2017.
 */
public class JMS2SharedPublisher {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory cf = new ConnectionFactory();

        try (JMSContext jmsContext = cf.createContext()) {
            Topic topic = jmsContext.createTopic("EM_JMS2_TRADE.T");
            DecimalFormat df = new DecimalFormat("##.00");
            String price = df.format(98.0 + Math.random());
            jmsContext.createProducer().send(topic, price);
            System.out.println("Message published");
        }
    }
}
