package ua.kruart.jms2_0.messaging.new_features.request_reply_queue;

import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;

/**
 * Created by kruart on 01.05.2017.
 */
public class JMS2Receiver {

    public static void main(String[] args) throws JMSException, InterruptedException {
        ConnectionFactory cf = new ConnectionFactory();
        try(JMSContext jmsContext = cf.createContext()) {
            Queue queue = jmsContext.createQueue("EM_JMS2_TRADE_REQ.Q");
            Message msg = jmsContext.createConsumer(queue).receive();
            String payload = msg.getBody(String.class);
            System.out.println("processing trade: " + payload);
            Thread.sleep(4000);
            String confirmation = "EQ-98765-JMS2";
            System.out.println("trade cofirmation: " + confirmation);

            jmsContext
                    .createProducer()
                    .setProperty("MessageLink", msg.getStringProperty("uuid"))
                    .send(msg.getJMSReplyTo(), confirmation);
        }
    }
}
