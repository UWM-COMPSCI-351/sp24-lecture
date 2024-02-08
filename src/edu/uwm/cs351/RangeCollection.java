package edu.uwm.cs351;

import java.util.AbstractCollection;
import java.util.Iterator;

public class RangeCollection extends AbstractCollection<Integer> {
	private final int lo, hi;
	
	/**
	 * Create a range from lo to hi
	 * @param lo inclusive lower bound
	 * @param hi exclusive upper bound
	 */
	public RangeCollection(int lo, int hi) {
		this.lo = lo;
		this.hi = hi;
	}

	@Override // required
	public Iterator<Integer> iterator() {
		return new MyIterator();
	}

	@Override // required
	public int size() {
		return hi - lo;
	}

	@Override // efficiency
	public boolean contains(Object o) {
		if (!(o instanceof Integer)) return false;
		Integer i = (Integer)o;
		return lo <= i && i < hi;
	}

	private class MyIterator implements Iterator<Integer> {
		int current = lo-1;
		
		@Override // required
		public boolean hasNext() {
			return current+1 < hi;
		}

		@Override // required
		public Integer next() {
			++current;
			return current;
		}
		
	}
}
