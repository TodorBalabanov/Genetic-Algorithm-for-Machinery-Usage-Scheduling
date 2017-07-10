package eu.veldsoft.gamus;

/**
 * Single action on a single machine.
 * 
 * @author Todor Balabanov
 */
class Action {
	/**
	 * Action start time.
	 */
	private int start = 0;

	/**
	 * Action duration.
	 */
	private int duration = 0;

	/**
	 * Action end time.
	 */
	private int end = 0;

	/**
	 * Action status.
	 */
	private boolean done = false;

	/**
	 * Machine used reference.
	 */
	private Machine machine = null;

	/**
	 * Operation belongs reference.
	 */
	private Operation operation = null;

	/**
	 * Constructor with all parameters,
	 * 
	 * @param start
	 *            Action start time.
	 * @param duration
	 *            Action duration.
	 * @param end
	 *            Action end time.
	 * @param done
	 *            Is action done flag.
	 * @param machine
	 *            Machine reference.
	 * @param operation
	 *            Operation reference.
	 */
	public Action(int start, int duration, int end, boolean done, Machine machine, Operation operation) {
		this.start = start;
		this.duration = duration;
		this.end = end;
		this.done = done;
		this.machine = machine;
		this.operation = operation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Action [start=" + start + ", duration=" + duration + ", end=" + end + ", done=" + done + ", machine="
				+ machine + "]";
	}

	/**
	 * Start time getter.
	 * 
	 * @return Start time duration.
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Start time setter.
	 * 
	 * @param start
	 *            Start time.
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * Action duration getter.
	 * 
	 * @return Duration time.
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Action duration setter.
	 *
	 * @param duration
	 *            Action duration.
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * End time getter.
	 * 
	 * @return End time.
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * End time setter.
	 *
	 * @param end
	 *            Action end time.
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * Done flag getter.
	 * 
	 * @return Is done flag.
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * Done flag setter.
	 * 
	 * @param done
	 *            Is action done flag.
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * Machine reference getter.
	 * 
	 * @return Machine used for this action.
	 */
	public Machine getMachine() {
		return machine;
	}

	/**
	 * Machine reference setter.
	 * 
	 * @param machine
	 *            Machine to be used for this action.
	 */
	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	/**
	 * Operation reference getter.
	 * 
	 * @return Operation reference.
	 */
	public Operation getOperation() {
		return operation;
	}

	/**
	 * Operation reference setter.
	 * 
	 * @param operation
	 *            Operation reference.
	 */
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
}
