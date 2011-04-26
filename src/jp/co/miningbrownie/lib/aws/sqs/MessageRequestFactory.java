package jp.co.miningbrownie.lib.aws.sqs;

import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

/**
 * 
 * @author t.matsuoka@miningbrownie.co.jp
 * 
 */
class MessageRequestFactory {
	private final String queueUrl;

	/**
	 * 
	 * @param queueUrl
	 */
	public MessageRequestFactory(String queueUrl) {
		this.queueUrl = queueUrl;
	}

	/**
	 * 
	 * @return
	 */
	public ReceiveMessageRequest newReceiveMessageRequest() {
		return new ReceiveMessageRequest(queueUrl);
	}

	/**
	 * 
	 * @param receiptHandle
	 * @return
	 */
	public DeleteMessageRequest newDeleteMessageRequest(String receiptHandle) {
		return new DeleteMessageRequest(queueUrl, receiptHandle);
	}

	/**
	 * 
	 * @param messageBody
	 * @return
	 */
	public SendMessageRequest newSendMessageRequest(String messageBody) {
		return new SendMessageRequest(queueUrl, messageBody);
	}

	/**
	 * 
	 * @param queueUrl
	 * @return
	 */
	public GetQueueAttributesRequest newGetQueueAttributesRequest() {
		return new GetQueueAttributesRequest(queueUrl).withAttributeNames("All");
	}
}
