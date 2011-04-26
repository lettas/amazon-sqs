package jp.co.miningbrownie.lib.util.cache;

public interface Computable<A, V> {
	public V compute(A arg) throws InterruptedException;
}
