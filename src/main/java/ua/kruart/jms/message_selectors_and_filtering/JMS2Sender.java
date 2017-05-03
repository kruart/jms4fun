package ua.kruart.jms.message_selectors_and_filtering;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;

/**
 * Created by kruart on 02.05.2017.
 */
public class JMS2Sender {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setProperty(ConnectionConfiguration.imqAddressList, "mq://localhost:7676");

        try(JMSContext jmsContext = cf.createContext()) {
            Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
            jmsContext
                    .createProducer()
                    .setProperty("Validated", false)
                    .send(queue, "BUY AAPL 1000 SHARES");
            System.out.println("message sent");
        }
    }
}
