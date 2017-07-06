import java.util.ArrayList;
import java.util.List;

/**
 * Operation is a set of actions to be taken in particular order.
 * 
 * @author Todor Balabanov
 */
public class Operation {
	/**
	 * Operation title.
	 */
	private String name = "";

	// TODO May be LinkedList is better choice for this member field.
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
	 * Constructor with all parameters.
	 * 
	 * @param name
	 *            Operation name.
	 * @param job
	 *            Job reference.
	 */
	public Operation(String name, Job job, Operation previous) {
		this.name = name;
		this.job = job;
		this.previous = previous;
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
