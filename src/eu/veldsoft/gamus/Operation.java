package eu.veldsoft.gamus;

import java.util.ArrayList;
import java.util.List;

/**
 * Operation is a set of actions to be taken in particular order.
 * 
 * @author Todor Balabanov
 */
class Operation {

	/**
	 * Operation title.
	 */
	private String name = "";

	/**
	 * List of actions taken for this operation.
	 */
	private List<Action> actions = new ArrayList<Action>();

	/**
	 * Job belongs reference.
	 */
	private Job job = null;

	/**
	 * Reference to the previous operation.
	 */
	private Operation previous = null;

	/**
	 * Is operation done flag.
	 */
	private Boolean done = null;

	/**
	 * Constructor with all parameters.
	 * 
	 * @param name
	 *            Operation name.
	 * @param job
	 *            Job reference.
	 * @param done
	 *            Is operation done flag.
	 */
	public Operation(String name, Job job, Operation previous, Boolean done) {
		this.name = name;
		this.job = job;
		this.previous = previous;
		this.done = done;
	}

	/**
	 * Name getter.
	 * 
	 * @return Name of the operation.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Name setter.
	 * 
	 * @param name
	 *            Operation name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * List of actions getter.
	 * 
	 * @return List of actions.
	 */
	public List<Action> getActions() {
		return actions;
	}

	/**
	 * List of actions setter.
	 * 
	 * @param actions
	 *            List of actions.
	 */
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	/**
	 * Job reference getter.
	 * 
	 * @return Job reference.
	 */
	public Job getJob() {
		return job;
	}

	/**
	 * Job reference setter.
	 * 
	 * @param job
	 *            Job reference.
	 */
	public void setJob(Job job) {
		this.job = job;
	}

	/**
	 * Previous operation reference getter.
	 * 
	 * @return Reference to the previous operation.
	 */
	public Operation getPrevious() {
		return previous;
	}

	/**
	 * Previous operation reference setter.
	 * 
	 * @param previous
	 *            Reference to the previous operation.
	 */
	public void setPrevious(Operation previous) {
		this.previous = previous;
	}

	/**
	 * Is operation done flag getter.
	 * 
	 * @return Is done flag.
	 */
	public boolean getDone() {
		// TODO Implement lazy initialization.
		return isDone();
	}

	/**
	 * Is operation done flag setter.
	 * 
	 * @param done
	 *            Is done flag.
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * Helper function to check is the operation done.
	 * 
	 * @return True if the operation is done, false otherwise.
	 * 
	 * @throws RuntimeException
	 *             It is thrown if more than one machine was used for this
	 *             operation.
	 */
	public boolean isDone() throws RuntimeException {
		int counter = 0;

		for (Action action : actions) {
			if (action.isDone() == true) {
				counter++;
			}
		}

		switch (counter) {
		case 0:
			return false;
		case 1:
			return true;
		default:
			throw new RuntimeException("More than one machine was used for this operation and it is not correct!");
		}
	}

	/**
	 * Is previous operation done checker.
	 * 
	 * @return True if the previous operation was done, false otherwise.
	 */
	public boolean isPreviousDone() {
		if (previous == null) {
			return false;
		} else {
			return previous.isDone();
		}
	}

	/**
	 * Provide the action used for this operation to complete.
	 * 
	 * @return Reference to the action.
	 */
	public Action getActiveAction() {
		if (isDone() == false) {
			return null;
		}

		for (Action action : actions) {
			if (action.isDone() == true) {
				return action;
			}
		}

		return null;
	}

	/**
	 * Maximum operation time getter.
	 * 
	 * @return Maximum time.
	 */
	public int getMaxDuration() {
		int duration = 0;
		int max = actions.get(0).getDuration();
		for (Action action : actions) {
			if ((duration = action.getDuration()) > max) {
				max = duration;
			}
		}

		return max;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Operation [name=" + name + ", actions=" + actions + "]";
	}

}
