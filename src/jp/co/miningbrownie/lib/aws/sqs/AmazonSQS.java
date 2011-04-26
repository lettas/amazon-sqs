package jp.co.miningbrownie.lib.aws.sqs;

import jp.co.miningbrownie.lib.util.Pair;
import jp.co.miningbrownie.lib.util.cache.Computable;
import jp.co.miningbrownie.lib.util.cache.Memoizer;

import com.amazonaws.auth.AWSCredentials;

/**
 * 
 * @author t.matsuoka@miningbrownie.co.jp
 *
 */
public class AmazonSQS {
	private static final Memoizer<Pair<String, String>, AmazonSQSManager> instancePool = new Memoizer<Pair<String, String>, AmazonSQSManager>(new Computable<Pair<String, String>, AmazonSQSManager>() {
		@Override
		public AmazonSQSManager compute(Pair<String, String> a) throws InterruptedException {
			return new AmazonSQSManager(a.getFirst(), a.getSecond());
		}
	});

	/**
	 * 
	 * @param credentials
	 * @return
	 */
	public static AmazonSQSManager connect(AWSCredentials credentials) {
		return connect(credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey());
	}

	/**
	 * 
	 * @param accessKey
	 * @param secretKey
	 * @return
	 */
	public static AmazonSQSManager connect(String accessKey, String secretKey) {
		try {
			return instancePool.compute(new Pair<String, String>(accessKey, secretKey));
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
