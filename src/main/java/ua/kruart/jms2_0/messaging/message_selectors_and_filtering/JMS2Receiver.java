package ua.kruart.jms2_0.messaging.message_selectors_and_filtering;

import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * Created by kruart on 02.05.2017.
 */
public class JMS2Receiver {

    public static void main(String[] args) {
        ConnectionFactory cf = new ConnectionFactory();

        try(JMSContext jmsContext = cf.createContext()) {
            Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
            String payload = jmsContext
                    .createConsumer(queue, "Validated = FALSE")
                    .receiveBody(String.class);

            System.out.println(payload);
        }
    }
}
