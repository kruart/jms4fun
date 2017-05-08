package ua.kruart.jms.advanced_features.embedded.bootstrap_mode;

import org.apache.activemq.broker.BrokerService;

/**
 * Created by kruart on 08.05.2017.
 */
public class Bootstrap {

    public void startBroker() {
        try {
            BrokerService broker = new BrokerService();
            broker.addConnector("tcp://localhost:61888");
            broker.setBrokerName("embedded1");
            broker.setPersistent(false); //doesn't persist any message
            broker.start();

            System.out.println("embedded broker started");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> new Bootstrap().startBroker()).start();
    }
}
