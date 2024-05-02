package edu.uwm.cs351;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Quicksort<T> {

	private final Comparator<T> comparator;
	
	public Quicksort(Comparator<T> c) {
		comparator = c;
	}
	
	private void qsort(List<T> list) {
		if (list.size() < 2) return;
		Iterator<T> it = list.iterator();
		T pivot = it.next();
		List<T> lo = new ArrayList<>();
		List<T> hi = new ArrayList<>();
		while (it.hasNext()) {
			T value = it.next();
			if (comparator.compare(value, pivot) < 0) {
				lo.add(value);
			} else {
				hi.add(value);
			}
		}
		qsort(lo);
		qsort(hi);
		for (int i = 0; i < lo.size(); ++i) {
			list.set(i,  lo.get(i));
		}
		list.set(lo.size(), pivot);
		for (int i = 0; i < hi.size(); ++i) {
			list.set(i + lo.size() + 1, hi.get(i));
		}
	}
	
	public void sort(T[] array) {
		qsort(Arrays.asList(array));
	}
}
