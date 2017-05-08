package ua.kruart.jms.embedded.application_mode_vm;

import org.apache.activemq.broker.BrokerService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by kruart on 08.05.2017.
 */
public class JMSVmApplication {

    private List<String> trades = Arrays.asList(
            "BUY AAPL 2000", "BUY IBM 4000", "BUY ATT 2400",
            "SELL AAPL 1000", "SELL IBM 2200", "SELL ATT 1200");

    public void startBroker() {
        try {
            BrokerService broker = new BrokerService();
            broker.addConnector("tcp://localhost:61888");
            broker.setBrokerName("embedded1");
            broker.setPersistent(false);
            broker.start();
            System.out.println("Embedded broker started");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            JMSVmApplication app = new JMSVmApplication();
            app.startBroker();
            app.startTradeProcessors();
            app.processTrades();
        }).start();
    }

    private void startTradeProcessors() {
        new JMSVmReceiver(1);
        new JMSVmReceiver(2);
        new JMSVmReceiver(3);
        new JMSVmReceiver(4);
        new JMSVmReceiver(5);
        new JMSVmReceiver(6);
        new JMSVmReceiver(7);
        new JMSVmReceiver(8);
    }

    private void processTrades() {
        JMSVmSender sender = new JMSVmSender();
        trades.forEach(sender::sendMessage);
    }
}
