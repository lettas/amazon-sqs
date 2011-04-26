package jp.co.miningbrownie.lib.aws.sqs;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

/**
 * 
 * @author t.matsuoka@miningbrownie.co.jp
 * 
 */
class QueueAccessor extends AbstractQueue<String> implements BlockingQueue<String> {
	private final int VALID_MAX_NUMBER_OF_MESSAGES = 10;
	private final AmazonSQSClientManager clientManager;
	private final MessageRequestFactory requestFactory;

	/**
	 * 
	 * @param manager
	 * @param factory
	 */
	public QueueAccessor(AmazonSQSClientManager manager,
			MessageRequestFactory factory) {
		this.clientManager = manager;
		this.requestFactory = factory;
	}

	/**
	 * 
	 * @return
	 */
	protected AmazonSQSClientManager getClientManager() {
		return clientManager;
	}

	/**
	 * 
	 * @return
	 */
	protected MessageRequestFactory getRequestFactory() {
		return requestFactory;
	}

	@Override
	public String peek() {
		String ret = null;
		ReceiveMessageRequest request = getRequestFactory().newReceiveMessageRequest();
		ReceiveMessageResult result = getClientManager().receiveMessage(request);
		for(Message message : result.getMessages()) {
			ret = message.getBody();
			break;
		}
		return ret;
	}

	@Override
	public String poll() {
		String ret = null;
		ReceiveMessageRequest receive = getRequestFactory().newReceiveMessageRequest();
		ReceiveMessageResult result = getClientManager().receiveMessage(receive);
		for(Message message : result.getMessages()) {
			ret = message.getBody();
			DeleteMessageRequest delete = getRequestFactory().newDeleteMessageRequest(message.getReceiptHandle());
			getClientManager().deleteMessageAsync(delete);
			break;
		}
		return ret;
	}

	@Override
	public String poll(long timeout, TimeUnit unit) throws InterruptedException {
		String ret = null;
		try {
			ReceiveMessageRequest receive = getRequestFactory().newReceiveMessageRequest();
			ReceiveMessageResult result = getClientManager().receiveMessageAsync(receive).get(timeout, unit);
			for(Message message : result.getMessages()) {
				ret = message.getBody();
				DeleteMessageRequest delete = getRequestFactory().newDeleteMessageRequest(message.getReceiptHandle());
				getClientManager().deleteMessageAsync(delete);
				break;
			}
		}
		catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
		catch (TimeoutException e) {
			Logger.getLogger(QueueAccessor.class.toString()).info("poll() : timeout");
		}
		return ret;
	}

	@Override
	public String take() throws InterruptedException {
		String ret = null;
		try {
			ReceiveMessageRequest receive = getRequestFactory().newReceiveMessageRequest();
			ReceiveMessageResult result = getClientManager().receiveMessageAsync(receive).get();
			while (result.getMessages().isEmpty()) {
				Thread.sleep(1);
				result = getClientManager().receiveMessageAsync(receive).get();
			}
			for(Message message : result.getMessages()) {
				ret = message.getBody();
				DeleteMessageRequest delete = getRequestFactory().newDeleteMessageRequest(message.getReceiptHandle());
				getClientManager().deleteMessageAsync(delete);
				break;
			}
		}
		catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
		return ret;
	}

	@Override
	public void put(String e) throws InterruptedException {
		SendMessageRequest send = getRequestFactory().newSendMessageRequest(e);
		SendMessageResult result = getClientManager().sendMessage(send);
		while (result.getMessageId().isEmpty()) {
			Thread.sleep(1);
			result = getClientManager().sendMessage(send);
		}
	}

	@Override
	public boolean offer(String e) {
		SendMessageRequest send = getRequestFactory().newSendMessageRequest(e);
		SendMessageResult result = getClientManager().sendMessage(send);
		return !result.getMessageId().isEmpty();
	}

	@Override
	public boolean offer(String e, long timeout, TimeUnit unit) throws InterruptedException {
		try {
			SendMessageRequest send = getRequestFactory().newSendMessageRequest(e);
			SendMessageResult result = getClientManager().sendMessageAsync(send).get(timeout, unit);
			return !result.getMessageId().isEmpty();
		}
		catch (ExecutionException e1) {
			throw new RuntimeException(e1);
		}
		catch (TimeoutException e1) {
			Logger.getLogger(QueueAccessor.class.toString()).info("offer() : timeout");
		}
		return false;
	}

	@Override
	public int remainingCapacity() {
		return Integer.MAX_VALUE;
	}

	/**
	 * AmazonSQSの仕様により、正確なsizeを取得することができないことに注意してください。
	 */
	@Override
	public int size() {
		GetQueueAttributesRequest request = getRequestFactory().newGetQueueAttributesRequest();
		GetQueueAttributesResult result = getClientManager().getQueueAttributes(request);
		QueueAttribute attribute = QueueAttribute.fromMap(result.getAttributes());
		return Integer.valueOf(attribute.getApproximateNumberOfMessages());
	}

	/**
	 * AmazonSQSの仕様により、正確なsizeを取得することができないため、必ずしも正しい結果が変えるとは限りません。
	 */
	@Override
	public boolean isEmpty() {
		return size() <= 0;
	}

	@Override
	public int drainTo(Collection<? super String> c) {
		return drainTo(c, VALID_MAX_NUMBER_OF_MESSAGES);
	}

	@Override
	public int drainTo(Collection<? super String> c, int maxElements) {
		int max = (maxElements > VALID_MAX_NUMBER_OF_MESSAGES) ? VALID_MAX_NUMBER_OF_MESSAGES : maxElements;
		ReceiveMessageRequest receive = getRequestFactory().newReceiveMessageRequest().withMaxNumberOfMessages(max);
		ReceiveMessageResult result = getClientManager().receiveMessage(receive);
		for(Message message : result.getMessages()) {
			c.add(message.getBody());
			DeleteMessageRequest delete = getRequestFactory().newDeleteMessageRequest(message.getReceiptHandle());
			getClientManager().deleteMessageAsync(delete);
		}
		return result.getMessages().size();
	}

	@Deprecated
	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {

			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public String next() {
				return null;
			}

			@Override
			public void remove() {
				// do nothing.
			}
		};
	}
}
