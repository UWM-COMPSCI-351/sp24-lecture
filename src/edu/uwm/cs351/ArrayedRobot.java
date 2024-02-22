package edu.uwm.cs351;

import java.util.Comparator;
import java.util.function.Consumer;

public class ArrayedRobot implements Robot {
	private static Consumer<String> reporter = (s) -> System.out.println("Invariant error: "+ s);
	
	private boolean report(String error) {
		reporter.accept(error);
		return false;
	}
	
	public static class FunctionalPart extends Part {
		public FunctionalPart() {
			super("W");
		}
		
		// the following fields are mutable:
		String function;
		
		/**
		 * Return the function of this part.
		 * @return string of this part, null if this part not in a robot
		 */
		public String getFunction() {
			return function;
		}
	}
	
	private FunctionalPart[] parts; 
	private Comparator<FunctionalPart> comparator; // never null
	private int size;
	
	private boolean wellFormed() {
		if(comparator == null) return report("comparator is null");
		if(parts == null) return report("dummy is null");
		if (size < 0) return report("size is negative");
		if (size > parts.length) return report("size too big: " + size + " > " + parts.length);
		for (int i=0; i < size; ++i) {
			if (parts[i] == null) return report("part #" + i + " is null");
			if (parts[i].function == null) return report("part #" + i + " has null function");
		}
		return true;
	}

	/**
	 * Return once the data structure has been updated so that 
	 * the capacity of the array is at least the parameter.
	 * If we create new arrays, they will at least twice as long as the existing arrays.
	 * @param cap capacity desired
	 */
	private void ensureCapacity(int cap) {
		if (cap <= parts.length) return; // enough!
		int newCap = cap;
		if (cap < parts.length*2) newCap = parts.length*2;
		FunctionalPart[] newParts = new FunctionalPart[newCap];
		for (int i=0; i < size; ++i) {
			newParts[i]= parts[i];
		}
		parts = newParts;
	}

	/**
	 * Create a wired robot without parts
	 * and no order.
	 */
	public ArrayedRobot() {
		this(null);
	}
	
	/**
	 * Create a wired robot without parts
	 * with the given order 
	 * @param comp order to use, if null, then unordered
	 */
	public ArrayedRobot(Comparator<FunctionalPart> comp) {
		if (comp == null) throw new NullPointerException("no comparator given");
		comparator = comp;
		parts = new FunctionalPart[1];
		assert wellFormed() : "Invariant not established by constructor";
	}
	
	/**
	 * Return the first part in this robot.
	 * @return the first part, null if this robot is empty
	 */
	public FunctionalPart getFirst() {
		return (FunctionalPart)getPart(null, 0);
	}
	
	@Override // required
	public boolean addPart(String function, Part part) {
		assert wellFormed(): "invariant broke before add";
		if (function == null || part == null) throw new NullPointerException();
		if(!(part instanceof FunctionalPart)) throw new IllegalArgumentException("parameter part must be a Functional Part");
		ensureCapacity(size+1);
		FunctionalPart n = (FunctionalPart)part;
		if (n.function != null) throw new IllegalArgumentException("part is already in a robot");
		n.function = function;
		
		insertPart(n);
		
		assert wellFormed(): "invariant broke by add";
		return true;
	}

	private void insertPart(FunctionalPart n) {
		int hole = size;
		++size;		
		while (hole > 0) {
			FunctionalPart prev = parts[hole-1];
			if (comparator.compare(n, prev) < 0) {
				parts[hole] = prev;
				--hole;
			} else break;
		}
		parts[hole] = n;
	}

	@Override // required
	public Part removePart(String function) {
		assert wellFormed(): "invariant broke before remove";
		FunctionalPart result = null;
		for (int i=0; i < size; ++i) {
			if (function == null || parts[i].function.equals(function)) {
				result = parts[i];
				--size;
				for (int j=i; j < size; ++j) {
					parts[j] = parts[j+1];
				}
			}
		}
		assert wellFormed(): "invariant broke by remove";
		return result;
	}

	@Override // required
	public Part getPart(String function, int index) {
		assert wellFormed(): "invariant broke before getPart";
		if(index < 0) throw new IllegalArgumentException("negative index");
		Part result = null;
		Part result1 = result;
		if (function == null) {
			if (index < size) result1 = parts[index];
		} else {
			for (int j=0; j < size; ++j) {
				if (parts[j].function.equals(function)) {
					if (index-- == 0) {
						result1 =  parts[j];
						break;
					}
				}
			}
		}
		result = result1;
		assert wellFormed(): "invariant broke by getPart";
		return result;
	}

	/**
	 * Change the comparator used to order the robot parts.
	 * The parts will be reorganized as necessary to accomodate the new order,
	 * but two parts will be reordered only if necessary.
	 * (The sorting is "stable".) 
	 * @param comp comparator to use, must not be null
	 */
	public void setComparator(Comparator<FunctionalPart> comp) {
		assert wellFormed() : "invariant broken in setComparator";
		if (comp == null) throw new NullPointerException();
		if (comp != comparator) {
			comparator = comp;
			int savedSize = size;
			size = 1;
			for (int i=1; i < savedSize; ++i) {
				insertPart(parts[i]);
			}
			
			// TODO: insertion sort
		}
		assert wellFormed() : "invariant broken by setComparator";
	}
	

}
