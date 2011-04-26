package jp.co.miningbrownie.lib.aws.sqs;

import java.util.concurrent.Future;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

/**
 * 
 * @author t.matsuoka@miningbrownie.co.jp
 * 
 */
class AmazonSQSClientManager {
	private final AmazonSQSAsync client;

	/**
	 * 
	 * @param credentials
	 */
	public AmazonSQSClientManager(AWSCredentials credentials) {
		client = new AmazonSQSAsyncClient(credentials);
	}

	/**
	 * 
	 * @return
	 */
	public AmazonSQS getClient() {
		return client;
	}

	/**
	 * 
	 * @return
	 */
	public AmazonSQSAsync getAsyncClient() {
		return client;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Future<SendMessageResult> sendMessageAsync(SendMessageRequest request) {
		return getAsyncClient().sendMessageAsync(request);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public SendMessageResult sendMessage(SendMessageRequest request) {
		return getClient().sendMessage(request);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Future<ReceiveMessageResult> receiveMessageAsync(ReceiveMessageRequest request) {
		return getAsyncClient().receiveMessageAsync(request);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public ReceiveMessageResult receiveMessage(ReceiveMessageRequest request) {
		return getClient().receiveMessage(request);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Future<Void> deleteMessageAsync(DeleteMessageRequest request) {
		return getAsyncClient().deleteMessageAsync(request);
	}

	/**
	 * 
	 * @param request
	 */
	public void deleteMessage(DeleteMessageRequest request) {
		getClient().deleteMessage(request);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Future<GetQueueAttributesResult> getQueueAttributesAsync(GetQueueAttributesRequest request) {
		return getAsyncClient().getQueueAttributesAsync(request);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public GetQueueAttributesResult getQueueAttributes(GetQueueAttributesRequest request) {
		return getClient().getQueueAttributes(request);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Future<Void> deleteQueueAsync(DeleteQueueRequest request) {
		return getAsyncClient().deleteQueueAsync(request);
	}

	/**
	 * 
	 * @param request
	 */
	public void deleteQueue(DeleteQueueRequest request) {
		getClient().deleteQueue(request);
	}

	/**
	 * 
	 * @return
	 */
	public ListQueuesResult listQueues() {
		return getClient().listQueues();
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public CreateQueueResult createQueue(CreateQueueRequest request) {
		return getClient().createQueue(request);
	}

	/**
	 * 
	 * @param endpoint
	 */
	public void setEndpoint(String endpoint) {
		getClient().setEndpoint(endpoint);
	}
}
