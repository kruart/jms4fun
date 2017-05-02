package ua.kruart.jms2_0.messaging.new_features.request_reply_queue;

import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Queue;
import java.util.UUID;

/**
 * Created by kruart on 01.05.2017.
 */
public class JMS2Sender {

    public static void main(String[] args) {
        ConnectionFactory cf = new ConnectionFactory();
        try(JMSContext jmsContext = cf.createContext()) {
            Queue queueReq = jmsContext.createQueue("EM_JMS2_TRADE_REQ.Q");
            Queue queueResp = jmsContext.createQueue("EM_JMS2_TRADE_RESP.Q");

            String uuid = UUID.randomUUID().toString();


            jmsContext
                    .createProducer()
                    .setJMSReplyTo(queueResp)
                    .setProperty("uuid", uuid)
                    .send(queueReq, "BUY AAPL 100 SHARES");
            System.out.println("message sent");

            String filter = "MessageLink = \'" + uuid + "\'";
            String conf = jmsContext.createConsumer(queueResp, filter).receiveBody(String.class);
            System.out.println("conf: " + conf);
        }
    }
}
