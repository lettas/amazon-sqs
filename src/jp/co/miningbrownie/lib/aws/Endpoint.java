package jp.co.miningbrownie.lib.aws;

/**
 * 
 * @author t.matsuoka@miningbrownie.co.jp
 *
 */
public enum Endpoint {
	US_EAST("sqs.us-east-1.amazonaws.com"),
	US_WEST("sqs.us-west-1.amazonaws.com"),
	ASIA_PACIFIC("sqs.ap-southeast-1.amazonaws.com"),
	EU("sqs.eu-west-1.amazonaws.com");
	private final String domain;

	/**
	 * 
	 * @param domain
	 */
	private Endpoint(String domain) {
		this.domain = domain;
	}

	/**
	 * 
	 * @return
	 */
	public String domain() {
		return domain;
	}
}
