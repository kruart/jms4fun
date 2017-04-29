package ua.kruart.jms2_0.messaging.new_features.delivery_delay;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;

/**
 * Created by kruart on 29.04.2017.
 */
public class JMS2SenderWithDelay {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setProperty(ConnectionConfiguration.imqAddressList, "mq://localhost:7676");

        try(JMSContext jmsContext = cf.createContext()) {
            Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
            jmsContext
                    .createProducer()
                    .setDeliveryDelay(5000)
                    .send(queue, "SELL AAPL 1500 SHARES");
            System.out.println("message sent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
