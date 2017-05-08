package ua.kruart.jms.basics.publish_subscribe_topic;

import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Topic;
import java.text.DecimalFormat;

/**
 * Created by kruart on 02.05.2017.
 */
public class JMS2Publisher {

    public static void main(String[] args) {
        ConnectionFactory cf = new ConnectionFactory();

        try(JMSContext jmsContext = cf.createContext()) {
            Topic topic = jmsContext.createTopic("EM_TRADE_JMS2.T");

            DecimalFormat df = new DecimalFormat("##.00");
            String price = df.format(95.0 + Math.random());
            jmsContext.createProducer().send(topic, price);
            System.out.println("Message sent: " + price);

        }
    }
}
