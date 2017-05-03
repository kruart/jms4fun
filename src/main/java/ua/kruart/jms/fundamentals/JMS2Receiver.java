package ua.kruart.jms.fundamentals;

import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;

/**
 * Created by kruart on 29.04.2017.
 */
public class JMS2Receiver {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory cf = new ConnectionFactory();
//        cf.setProperty(ConnectionConfiguration.imqAddressList, "t://localhost:7676");

        try(JMSContext jmsContext = cf.createContext()) {
            Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
            Message receive = jmsContext
                    .createConsumer(queue)
                    .receive();
            System.out.println(receive.getBody(String.class));
            System.out.println(receive.getStringProperty("TraderName"));
        }
    }
}
