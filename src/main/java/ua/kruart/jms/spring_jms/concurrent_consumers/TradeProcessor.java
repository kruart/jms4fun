package ua.kruart.jms.spring_jms.concurrent_consumers;

public class TradeProcessor {

	public void processTrade(String trade) {
		try {
			System.out.println("Processing Trade " + trade + "\n");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
