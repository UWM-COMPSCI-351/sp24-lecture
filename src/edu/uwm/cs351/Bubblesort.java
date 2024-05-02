package edu.uwm.cs351;

import java.util.Comparator;

public class Bubblesort<T> {

	private final Comparator<T> comparator;
	
	public Bubblesort(Comparator<T> c) {
		comparator = c;
	}
	
	public void sort(T[] array) {
		boolean done = false;
		while (!done) {
			done = true;
			for (int i=1; i < array.length; ++i) {
				T elem1 = array[i-1];
				T elem2 = array[i];
				if (comparator.compare(elem1, elem2) > 0) {
					array[i-1] = elem2;
					array[i] = elem1;
					done = false;
				}
			}
		}
	}

}
