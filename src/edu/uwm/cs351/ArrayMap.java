package edu.uwm.cs351;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

public class ArrayMap<Value> extends AbstractMap<Integer,Value> {
	private Value[] array;
	
	/**
	 * Create a map for this array
	 * @param arr array to use, must not be null
	 */
	public ArrayMap(Value[] arr) {
		array = arr;
	}

	@Override // required
	public Set<Entry<Integer, Value>> entrySet() {
		return new EntrySet();
	}
	
	@Override // efficiency
	public Value get(Object key) {
		if (!(key instanceof Integer)) return null;
		int k = ((Integer)key).intValue();
		if (k < 0 || k >= array.length) return null;
		return array[k];
	}

	@Override // efficiency
	public boolean containsKey(Object key) {
		if (!(key instanceof Integer)) return false;
		int k = ((Integer)key).intValue();
		if (k < 0 || k >= array.length) return false;
		return true;
	}

	@Override // implementation
	public Value put(Integer key, Value value) {
		Value result = array[key];
		array[key] = value;
		return result;
	}

	private class EntrySet extends AbstractSet<Entry<Integer, Value>> {

		@Override // required
		public int size() {
			return array.length;
		}

		@Override // required
		public Iterator<Entry<Integer, Value>> iterator() {
			return new MyIterator();
		}
	}
	
	private class MyIterator implements Iterator<Entry<Integer,Value>> {
		private int current = -1;
		
		@Override // required
		public boolean hasNext() {
			return current + 1 < array.length;
		}

		@Override // required
		public Entry<Integer, Value> next() {
			++current;
			return new DefaultEntry<>(current,array[current]);
		}
		
	}
}
