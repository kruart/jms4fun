package ua.kruart.jms2_0.messaging.new_features.async;

import com.sun.messaging.ConnectionFactory;

import javax.jms.CompletionListener;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Queue;
import java.util.concurrent.CountDownLatch;

/**
 * Created by kruart on 29.04.2017.
 */
public class JMS2SenderAsync {

    public static void main(String[] args) throws InterruptedException {
        ConnectionFactory cf = new ConnectionFactory();

        try (JMSContext jmsContext = cf.createContext()) {
            Queue topic = jmsContext.createQueue("EM_JMS2_TRADE.Q");

            CountDownLatch latch = new CountDownLatch(1);
            MyCompletionListener cl = new MyCompletionListener(latch);

            jmsContext.createProducer().setAsync(cl).send(topic, "Test Message");
            System.out.println("Message sent");

            for (int i = 0; i < 5; i++) {
                System.out.println("processing...");
                Thread.sleep(1000);
            }

            latch.wait();

            System.out.println("end processing...");
        }
    }
}

class MyCompletionListener implements CompletionListener {
    public CountDownLatch latch = null;

    public MyCompletionListener(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onCompletion(Message message) {
        latch.countDown();
        System.out.println("Message acknowledged by server");
    }

    @Override
    public void onException(Message message, Exception e) {
        latch.countDown();
        System.out.println("unable to send message");
    }
}


