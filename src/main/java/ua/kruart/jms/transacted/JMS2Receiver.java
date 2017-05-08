package ua.kruart.jms.transacted;

import com.sun.messaging.ConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 07.05.2017.
 */
public class JMS2Receiver implements MessageListener {

    public JMS2Receiver() {
        ConnectionFactory cf = new ConnectionFactory();
        JMSContext jmsContext = cf.createContext(JMSContext.SESSION_TRANSACTED);
        Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
        JMSConsumer receiver = jmsContext.createConsumer(queue);
        receiver.setMessageListener(this);
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(message.getBody(String.class));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws JMSException, InterruptedException {
        new Thread(JMS2Receiver::new).start();
    }


}
