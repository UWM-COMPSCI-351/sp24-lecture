package edu.uwm.cs351;

import java.util.AbstractCollection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayCollection<E> extends AbstractCollection<E> {
	private static final int INITIAL_CAPACITY = 1;
	
	@SuppressWarnings("unchecked")
	private E[] makeArray(int cap) {
		return (E[])new Object[cap];
	}

	private E[] array;
	private int size;
	private int version;
	
	private void ensureCapacity(int cap) {
		if (cap <= array.length) return;
		int newCap = array.length * 2;
		if (newCap < cap) newCap = cap;
		E[] bigger = makeArray(newCap);
		for (int i=0; i < size; ++i) {
			bigger[i] = array[i];
		}
		array = bigger;
	}
	
	public ArrayCollection() {
		array = makeArray(INITIAL_CAPACITY);
		size = 0;
	}

	@Override // implementation 
	public boolean add(E e) {
		ensureCapacity(size+1);
		array[size] = e;
		++size;
		++version;
		return true;
	}

	@Override // required
	public Iterator<E> iterator() {
		return new MyIterator();
	}

	@Override // required
	public int size() {
		return size;
	}

	private class MyIterator implements Iterator<E> {
		private int currentIndex;
		private boolean canRemove;
		private int colVersion = version;
		
		@Override // required
		public boolean hasNext() {
			if (version != colVersion)throw new ConcurrentModificationException("stale!");
			return (canRemove ? currentIndex+1 : currentIndex) < size;
		}

		@Override // required
		public E next() {
			if (!hasNext()) throw new NoSuchElementException("no more");
			if (canRemove) {
				++currentIndex;
			}
			canRemove = true;
			return array[currentIndex];
		}

		@Override // required / implementation
		public void remove() {
			--size;
			for (int i=currentIndex; i < size; ++i) {
				array[i] = array[i+1];
			}
		}
		
	}
}
