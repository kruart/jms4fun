package ua.kruart.jms.basics.message_browsing;

import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import java.util.Enumeration;

/**
 * Created by kruart on 08.05.2017.
 */
public class JMS2Browser {

    public static void main(String[] args) {
        ConnectionFactory cf = new ConnectionFactory();
        try(JMSContext jmsContext = cf.createContext()) {
            Queue queue = jmsContext.createQueue("EM_JMS2_TRADE_Q");
            Enumeration<?> e = jmsContext.createBrowser(queue).getEnumeration();


            while (e.hasMoreElements()) {
                System.out.println(((Message)e.nextElement()).getBody(String.class));
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
