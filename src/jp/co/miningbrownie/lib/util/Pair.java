package jp.co.miningbrownie.lib.util;

public class Pair<T1, T2> implements Comparable<Pair<T1, T2>> {
	protected final T1 first;
	protected final T2 second;

	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}

	public T1 getFirst() {
		return first;
	}

	public T2 getSecond() {
		return second;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(first);
		buf.append(":");
		buf.append(second);
		return buf.toString();
	}

	@Override
	public int compareTo(Pair<T1, T2> pair) {
		if(pair == null) {
			return -1;
		}
		else if(pair.equals(this)) {
			return 0;
		}
		else if(pair.hashCode() > hashCode()) {
			return 1;
		}
		else {
			return -1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		@SuppressWarnings("unchecked")
		Pair<T1, T2> other = (Pair<T1, T2>) obj;
		if(first == null) {
			if(other.first != null) return false;
		}
		else if(!first.equals(other.first)) return false;
		if(second == null) {
			if(other.second != null) return false;
		}
		else if(!second.equals(other.second)) return false;
		return true;
	}

}
