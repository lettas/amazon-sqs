package jp.co.miningbrownie.lib.aws.sqs;

import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.ListQueuesRequest;

/**
 * 
 * @author t.matsuoka@miningbrownie.co.jp
 * 
 */
class QueueRequestFactory {
	/**
	 * 
	 * @param name
	 * @return
	 */
	public CreateQueueRequest newCreateQueueRequest(String name) {
		return new CreateQueueRequest(name);
	}

	/**
	 * 
	 * @param queueUrl
	 * @return
	 */
	public DeleteQueueRequest newDeleteQueueRequest(String queueUrl) {
		return new DeleteQueueRequest(queueUrl);
	}

	/**
	 * 
	 * @return
	 */
	public ListQueuesRequest newListQueuesRequest() {
		return new ListQueuesRequest();
	}
}
