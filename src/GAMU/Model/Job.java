package GAMU.Model;
import java.util.ArrayList;
import java.util.List;

/**
 * Job is a set of operations to be taken in particular order.
 * 
 * @author Todor Balabanov
 */
class Job {
	/**
	 * Job title.
	 */
	private String name = "";

	// TODO May be LinkedList is better choice for this member field.
	/**
	 * List of operations taken for this job.
	 */
	private List<Operation> operations = new ArrayList<Operation>();

	/**
	 * Constructor with all parameters.
	 * 
	 * @param name
	 *            Job name.
	 */
	public Job(String name) {
		this.name = name;
	}

	/**
	 * Name getter.
	 * 
	 * @return Job name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Name setter.
	 * 
	 * @param name
	 *            Job name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The job's list of operations setter.
	 * 
	 * @param operations
	 *            The list of operations that the job should consist of.
	 */
	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	/**
	 * The job's list of operations setter.
	 * 
	 * @return The list of operations that the job consists of.
	 */
	public List<Operation> getOperations() {
		return operations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Job [name=" + name + ", operations=" + operations + "]";
	}
}
