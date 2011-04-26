package jp.co.miningbrownie.lib.aws.sqs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import jp.co.miningbrownie.lib.aws.Endpoint;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;

/**
 * 
 * @author t.matsuoka@miningbrownie.co.jp
 * 
 */
public final class AmazonSQSManager {
	private final QueueRequestFactory requestFactory;
	private final AmazonSQSClientManager clientManager;
	private final Set<String> queueUrls;

	/**
	 * 
	 * @param accessKey
	 * @param secretKey
	 */
	AmazonSQSManager(String accessKey, String secretKey) {
		this(new BasicAWSCredentials(accessKey, secretKey));
	}

	/**
	 * 
	 * @param credentials
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 */
	AmazonSQSManager(AWSCredentials credentials) throws AmazonServiceException,
			AmazonClientException {
		this.clientManager = new AmazonSQSClientManager(credentials);
		ListQueuesResult r = getClientManager().listQueues();
		this.queueUrls = Collections.synchronizedSet(new HashSet<String>());
		this.queueUrls.addAll(r.getQueueUrls());
		this.requestFactory = new QueueRequestFactory();
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public BlockingQueue<String> createQueueAccessor(String name) {
		String url = searchQueueUrlByName(name);
		if(url == null) {
			url = createQueue(name);
		}
		return new QueueAccessor(getClientManager(), new MessageRequestFactory(url));
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public String createQueue(String name) {
		CreateQueueRequest request = getRequestFactory().newCreateQueueRequest(name);
		CreateQueueResult result = getClientManager().createQueue(request);
		String url = result.getQueueUrl();
		queueUrls.add(url);
		return url;
	}

	/**
	 * 
	 * @param name
	 */
	public void deleteQueue(String name) {
		String url = searchQueueUrlByName(name);
		if(url != null) {
			DeleteQueueRequest request = getRequestFactory().newDeleteQueueRequest(url);
			getClientManager().deleteQueueAsync(request);
		}
	}

	/**
	 * 
	 * @return
	 */
	public Set<String> getQueueUrls() {
		return Collections.unmodifiableSet(queueUrls);
	}

	/**
	 * 
	 * @param endpoint
	 */
	public void setEndpoint(Endpoint endpoint) {
		getClientManager().setEndpoint(endpoint.domain());
	}

	/**
	 * 
	 * @param endpoint
	 */
	public void setEndpoint(String endpoint) {
		getClientManager().setEndpoint(endpoint);
	}

	/**
	 * 
	 * @return
	 */
	protected QueueRequestFactory getRequestFactory() {
		return requestFactory;
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
	 * @param name
	 * @return
	 */
	private String searchQueueUrlByName(String name) {
		String url = null;
		for(String u : getQueueUrls()) {
			if(u.matches(String.format("/%s$", name))) {
				url = u;
				break;
			}
		}
		return url;
	}
}
