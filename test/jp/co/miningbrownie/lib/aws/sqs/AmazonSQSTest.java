package jp.co.miningbrownie.lib.aws.sqs;

import java.util.concurrent.BlockingQueue;

import org.junit.Test;

public class AmazonSQSTest {
	private static final String AWS_ACCESS_KEY = System.getProperty("aws.accessKey");
	private static final String AWS_SECRET_ACCESS_KEY = System.getProperty("aws.secretKey");

	@Test
	public void test() {
		// connect
		AmazonSQSManager manager = AmazonSQS.connect(AWS_ACCESS_KEY, AWS_SECRET_ACCESS_KEY);

		// create
		BlockingQueue<String> queue = manager.createQueueAccessor("amazon-sqs-test");

		// add
		queue.add("hogehoge");
		queue.add("fugafuga");
		queue.add("foobar");

		// take
		String message;
		try {
			message = queue.take();
			System.out.println(message);
			message = queue.take();
			System.out.println(message);
			message = queue.take();
			System.out.println(message);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
