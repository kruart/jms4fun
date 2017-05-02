package ua.kruart.jms2_0.messaging.publish_subscribe_topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.text.DecimalFormat;

/**
 * Created by kruart on 02.05.2017.
 */
public class JMSPublisher {

    public static void main(String[] args) {
        try {
            Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic = session.createTopic("EM_TRADE.T");
            MessageProducer publisher = session.createProducer(topic);
            DecimalFormat df = new DecimalFormat("##.00");
            String price = df.format(95.0 + Math.random());
            TextMessage msg = session.createTextMessage("AAPL " + price);

            publisher.send(msg);
            System.out.println("Message sent: AAPL: " + price);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
