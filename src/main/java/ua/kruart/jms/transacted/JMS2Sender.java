package ua.kruart.jms.transacted;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;

/**
 * Created by kruart on 07.05.2017.
 */
public class JMS2Sender {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setProperty(ConnectionConfiguration.imqAddressList, "mq://localhost:7676");


        try(JMSContext jmsContext = cf.createContext(JMSContext.SESSION_TRANSACTED)) {
            Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
            jmsContext.createProducer().send(queue, "SELL AAPL 1000 SHARES");
            System.out.println("message 1 sent");

            Thread.sleep(3000);

            jmsContext.createProducer().send(queue, "SELL IBM 2000 SHARES");
            System.out.println("message 1 sent");
            System.out.println("committing");
            jmsContext.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
