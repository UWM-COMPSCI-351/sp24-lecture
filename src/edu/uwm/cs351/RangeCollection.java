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
		// TODO Auto-generated method stub
		return null;
	}

	@Override // required
	public int size() {
		return hi - lo;
	}

}
