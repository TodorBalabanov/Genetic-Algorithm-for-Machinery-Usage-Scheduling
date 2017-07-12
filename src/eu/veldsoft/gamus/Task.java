/**
 * 
 */
package eu.veldsoft.gamus;

/**
 * Task is small piece in the decision vector.
 * 
 * @author Todor Balabanov
 */
class Task {
	/**
	 * Index of the machine to be used in this task.
	 */
	private int index = -1;

	/**
	 * Moment in time when the task will start on the machine.
	 */
	private int time = -1;

	/**
	 * Constructor with all parameters.
	 * 
	 * @param index
	 *            Machine index.
	 * 
	 * @param time
	 *            Start time.
	 */
	public Task(int index, int time) {
		super();
		this.index = index;
		this.time = time;
	}

	/**
	 * Machine index getter.
	 * 
	 * @return Machine index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Machine index setter.
	 * 
	 * @param Machine
	 *            index.
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Start time getter.
	 * 
	 * @return Start time.
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Start time setter.
	 * 
	 * @param Start
	 *            time.
	 */
	public void setTime(int time) {
		this.time = time;
	}
}
