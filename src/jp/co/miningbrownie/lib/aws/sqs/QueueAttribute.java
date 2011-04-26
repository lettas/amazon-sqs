package jp.co.miningbrownie.lib.aws.sqs;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author t.matsuoka@miningbrownie.co.jp
 * 
 */
class QueueAttribute {
	private String visibilityTimeout;
	private String approximateNumberOfMessages;
	private String approximateNumberOfMessagesNotVisible;
	private String createdTimestamp;
	private String lastModifiedTimestamp;
	private String maximumMessageSize;
	private String messageRetentionPeriod;

	/**
	 * 
	 * @param map
	 * @return
	 */
	public static QueueAttribute fromMap(Map<String, String> map) {
		QueueAttribute attribute = new QueueAttribute();
		if(map.containsKey("VisibilityTimeout")) {
			attribute.setVisibilityTimeout(map.get("VisibilityTimeout"));
		}
		if(map.containsKey("ApproximateNumberOfMessages")) {
			attribute.setApproximateNumberOfMessages(map.get("ApproximateNumberOfMessages"));
		}
		if(map.containsKey("ApproximateNumberOfMessagesNotVisible")) {
			attribute.setApproximateNumberOfMessagesNotVisible(map.get("ApproximateNumberOfMessagesNotVisible"));
		}
		if(map.containsKey("CreatedTimestamp")) {
			attribute.setCreatedTimestamp(map.get("CreatedTimestamp"));
		}
		if(map.containsKey("LastModifiedTimestamp")) {
			attribute.setLastModifiedTimestamp(map.get("LastModifiedTimestamp"));
		}
		if(map.containsKey("MaximumMessageSize")) {
			attribute.setMaximumMessageSize(map.get("MaximumMessageSize"));
		}
		if(map.containsKey("MessageRetentionPeriod")) {
			attribute.setMessageRetentionPeriod(map.get("MessageRetentionPeriod"));
		}
		return attribute;
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		if(getVisibilityTimeout() != null) {
			map.put("VisibilityTimeout", getVisibilityTimeout());
		}
		if(getApproximateNumberOfMessages() != null) {
			map.put("ApproximateNumberOfMessages", getApproximateNumberOfMessages());
		}
		if(getApproximateNumberOfMessagesNotVisible() != null) {
			map.put("ApproximateNumberOfMessagesNotVisible", getApproximateNumberOfMessagesNotVisible());
		}
		if(getCreatedTimestamp() != null) {
			map.put("CreatedTimestamp", getCreatedTimestamp());
		}
		if(getLastModifiedTimestamp() != null) {
			map.put("LastModifiedTimestamp", getLastModifiedTimestamp());
		}
		if(getMaximumMessageSize() != null) {
			map.put("MaximumMessageSize", getMaximumMessageSize());
		}
		if(getMessageRetentionPeriod() != null) {
			map.put("MessageRetentionPeriod", getMessageRetentionPeriod());
		}
		return map;
	}

	/**
	 * @return the visibilityTimeout
	 */
	public String getVisibilityTimeout() {
		return visibilityTimeout;
	}

	/**
	 * @param visibilityTimeout
	 *            the visibilityTimeout to set
	 */
	public void setVisibilityTimeout(String visibilityTimeout) {
		this.visibilityTimeout = visibilityTimeout;
	}

	/**
	 * @return the approximateNumberOfMessages
	 */
	public String getApproximateNumberOfMessages() {
		return approximateNumberOfMessages;
	}

	/**
	 * @param approximateNumberOfMessages
	 *            the approximateNumberOfMessages to set
	 */
	public void setApproximateNumberOfMessages(String approximateNumberOfMessages) {
		this.approximateNumberOfMessages = approximateNumberOfMessages;
	}

	/**
	 * @return the approximateNumberOfMessagesNotVisible
	 */
	public String getApproximateNumberOfMessagesNotVisible() {
		return approximateNumberOfMessagesNotVisible;
	}

	/**
	 * @param approximateNumberOfMessagesNotVisible
	 *            the approximateNumberOfMessagesNotVisible to set
	 */
	public void setApproximateNumberOfMessagesNotVisible(String approximateNumberOfMessagesNotVisible) {
		this.approximateNumberOfMessagesNotVisible = approximateNumberOfMessagesNotVisible;
	}

	/**
	 * @return the createdTimestamp
	 */
	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	/**
	 * @param createdTimestamp
	 *            the createdTimestamp to set
	 */
	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	/**
	 * @return the lastModifiedTimestamp
	 */
	public String getLastModifiedTimestamp() {
		return lastModifiedTimestamp;
	}

	/**
	 * @param lastModifiedTimestamp
	 *            the lastModifiedTimestamp to set
	 */
	public void setLastModifiedTimestamp(String lastModifiedTimestamp) {
		this.lastModifiedTimestamp = lastModifiedTimestamp;
	}

	/**
	 * @return the maximumMessageSize
	 */
	public String getMaximumMessageSize() {
		return maximumMessageSize;
	}

	/**
	 * @param maximumMessageSize
	 *            the maximumMessageSize to set
	 */
	public void setMaximumMessageSize(String maximumMessageSize) {
		this.maximumMessageSize = maximumMessageSize;
	}

	/**
	 * @return the messageRetentionPeriod
	 */
	public String getMessageRetentionPeriod() {
		return messageRetentionPeriod;
	}

	/**
	 * @param messageRetentionPeriod
	 *            the messageRetentionPeriod to set
	 */
	public void setMessageRetentionPeriod(String messageRetentionPeriod) {
		this.messageRetentionPeriod = messageRetentionPeriod;
	}
}
