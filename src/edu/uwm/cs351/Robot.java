package edu.uwm.cs351;

/**
 * A simple ADT for a robot constructed with some parts.
 */
public interface Robot {
	/**
	 * Add a part to the robot.
	 * @param function the type of part this is (arm, leg, etc), must not be null
	 * @param part the part to add, must not be null.
	 * @return whether the part was added.
	 * @exception NullPointerException if the function or part is null
	 */
	public boolean addPart(String function, Part part);
	
	/**
	 * Remove a part from the robot if there is one with this function.
	 * @param function the type of part to remove, null means <em>any</em> part
	 * @return part that was removed
	 */
	public Part removePart(String function);
	
	/**
	 * Return the part with the given function.
	 * @param function the type of part to look for, null means <em>any</em> part
	 * @param index zero-based index of part to return of the given type, must not be negative
	 * @return indicated part, or null if no such part (index is at least the number of this kind of kind)
	 */
	public Part getPart(String function, int index);
}
