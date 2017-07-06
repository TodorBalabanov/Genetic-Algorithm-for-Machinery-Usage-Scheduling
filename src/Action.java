
/**
 * Single action on a single machine.
 * 
 * @author Todor Balabanov
 */
public class Action {
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
	 * Reference to the previous action.
	 */
	private Action previous = null;

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
	 * @param previous
	 *            Reference to the previous action.
	 */
	public Action(int start, int duration, int end, boolean done, Machine machine, Operation operation,
			Action previous) {
		this.start = start;
		this.duration = duration;
		this.end = end;
		this.done = done;
		this.machine = machine;
		this.operation = operation;
		this.previous = previous;
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
	 * Start field getter.
	 * 
	 * @return Start time duration.
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Start field setter.
	 * 
	 * @param start
	 *            Start time.
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * Duration field getter.
	 * 
	 * @return duration Duration time.
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Duration field setter
	 *
	 * @param duration
	 *            Action duration.
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * End field getter.
	 * 
	 * @return End time duration.
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * . End field setter.
	 *
	 * @param end
	 *            Action end time.
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * Done field getter
	 * 
	 * @return Is done flag true;
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * Done field setter s *
	 * 
	 * @param end
	 *            Action end time.
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * Machine field getter.
	 * 
	 * @return Current machine.
	 * 
	 */
	public Machine getMachine() {
		return machine;
	}

	/**
	 * Machine field setter.
	 * 
	 * @param machine
	 *            Current machine.
	 */
	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	/**
	 * Operation field getter.
	 * 
	 * @return Current operation.
	 */
	public Operation getOperation() {
		return operation;
	}

	/**
	 * Operation field setter.
	 * 
	 * @param operation
	 *            Current operation.
	 */
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	/**
	 * Previous field getter.
	 * 
	 * @return The previous operation.
	 */
	public Action getPrevious() {
		return previous;
	}

	/**
	 * Previous field setter.
	 * 
	 * @param previous
	 *            Previous operation.
	 */
	public void setPrevious(Action previous) {
		this.previous = previous;
	}
}