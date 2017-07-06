import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Machine description.
 * 
 * @author Todor Balabanov
 */
class Machine {
	/**
	 * Machine title.
	 */
	private String name = "";

	/**
	 * Is machine occupied by particular action flag.
	 */
	private boolean occupied = false;

	/**
	 * Reference to external action object.
	 */
	private Action action = null;

	/**
	 * Constructor with all parameters.
	 * 
	 * @param name
	 *            Machine title.
	 * @param occupied
	 *            Machine status.
	 * @param action
	 *            Reference to the action in progress.
	 */
	public Machine(String name, boolean occupied, Action action) {
		this.name = name;
		this.occupied = occupied;
		this.action = action;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Machine [name=" + name + ", occupied=" + occupied + "]";
	}
}

/**
 * Single action on a single machine.
 * 
 * @author Todor Balabanov
 */
class Action {
	/**
	 * Action start time.
	 */
	int start = 0;

	/**
	 * Action duration.
	 */
	int duration = 0;

	/**
	 * Action end time.
	 */
	int end = 0;

	/**
	 * Action status.
	 */
	boolean done = false;

	/**
	 * Machine used reference.
	 */
	Machine machine = null;

	/**
	 * Operation belongs reference.
	 */
	Operation operation = null;

	/**
	 * Reference to the previous action.
	 */
	Action previous = null;

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
}

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
	 * Get name of operation
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set operation name
	 * 
	 * @param name
	 *            Operation name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get list of operations
	 */
	public List<Action> getActions() {
		return actions;
	}

	/**
	 * Set the list with the given one
	 * 
	 * @param actions
	 *            list name
	 */
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	/**
	 * Get the current Job
	 */
	public Job getJob() {
		return job;
	}

	/**
	 * Set the current Job
	 * 
	 * @param job
	 *            job name
	 */
	public void setJob(Job job) {
		this.job = job;
	}

	/**
	 * Get the previous operation
	 */
	public Operation getPrevious() {
		return previous;
	}

	/**
	 * Set the previous operation
	 * 
	 * @param previous
	 *            the name of the previous operation
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

/**
 * Job is a set of operations to be taken in particular order.
 * 
 * @author Todor Balabanov
 */
class Job {
	/**
	 * Job title.
	 */
	String name = "";

	// TODO May be LinkedList is better choice for this member field.
	/**
	 * List of operations taken for this job.
	 */
	List<Operation> operations = new ArrayList<Operation>();

	/**
	 * Constructor with all parameters.
	 * 
	 * @param name
	 *            Job name.
	 */
	public Job(String name) {
		this.name = name;
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

/**
 * Application single entry point class.
 * 
 * @author Todor Balabanov
 */
public class Main {
	/**
	 * Pseudo random number generator.
	 */
	private static final Random PRNG = new Random();

	/**
	 * List of the available machines.
	 */
	private static List<Machine> machines = new ArrayList<Machine>();

	/**
	 * List of operations taken for this job.
	 */
	private static List<Job> jobs = new ArrayList<Job>();

	/**
	 * List of action references.
	 */
	private static List<Action> actions = new ArrayList<Action>();

	/**
	 * Data loading from an array.
	 * 
	 * @param data
	 *            Algorithm input data as 2D array.
	 */
	private static void load(Object[][] data) {
		/*
		 * Load machines list.
		 */ {
			for (int j = 2; j < data[0].length; j++) {
				machines.add(new Machine(data[0][j].toString(), false, null));
			}
		}

		/*
		 * Load jobs list.
		 */ {
			for (int i = 1; i < data.length; i++) {
				if (data[i][0].equals("") == false) {
					jobs.add(new Job(data[i][0].toString()));
				}
			}
		}

		/*
		 * Load operations list.
		 */ {
			int i = 1;
			for (Job job : jobs) {
				while (i < data.length
						&& (job.name.equals(data[i][0].toString()) || data[i][0].toString().equals(""))) {
					Operation previous = null;
					if (job.operations.size() > 0) {
						previous = job.operations.get(job.operations.size() - 1);
					}
					job.operations.add(new Operation(data[i][1].toString(), job, previous));
					i++;
				}
			}
		}

		/*
		 * Load actions list.
		 */ {
			int i = 1;
			for (Job job : jobs) {
				for (Operation operation : job.operations) {
					for (int j = 2; j < data[i].length; j++) {
						Action previous = null;
						if (operation.getActions().size() > 0) {
							previous = operation.getActions().get(operation.getActions().size() - 1);
						}
						Action action = new Action(0, ((Integer) data[i][j]).intValue(), 0, false, machines.get(j - 2),
								operation, previous);
						operation.getActions().add(action);
						actions.add(action);
					}

					i++;
					if (i >= data.length) {
						break;
					}
				}
			}
		}
	}

	/**
	 * Select random start times of all actions.
	 * 
	 * @param jobs
	 *            Jobs list.
	 */
	private static void takeRandomTimes(List<Job> jobs, int min, int max) {
		for (Job job : jobs) {
			for (Operation operation : job.operations) {
				for (Action action : operation.getActions()) {
					action.start = min + PRNG.nextInt(max - min + 1);
					action.end = action.start + action.duration;
				}
			}
		}
	}

	/**
	 * Simulate work unit.
	 * 
	 * @param limit
	 *            Limit discrete time for the simulation.
	 * @param machines
	 *            List of machines.
	 * @param jobs
	 *            Jobs list to be done in the work unit.
	 * @param actions
	 *            List of actions.
	 */
	private static void simulate(int limit, List<Machine> machines, List<Job> jobs, List<Action> actions) {
		for (int time = 0; time < limit; time++) {
			// TODO Do the simulation.
			for (Action action : actions) {
				/*
				 * It is time the action to be done.
				 */
				if (action.start == time && action.done == false) {
					if (action.machine.isOccupied() == false) {
						action.machine.setOccupied(true);
						action.machine.setAction(action);
					} else {
						System.err.println("Schedule collision for: " + action);
					}
				}

				/*
				 * It is time the action to be done.
				 */
				if (action.end == time) {
					if (action.machine.isOccupied() == true) {
						action.done = true;
						action.machine.setOccupied(false);
						action.machine.setAction(null);
					} else {
						System.err.println("Schedule omission for: " + action);
					}
				}
			}
		}
	}

	/**
	 * Single entry point method.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		Object data[][] = { { "Pikj", "O", "M1", "M2", "M3", "M4", "M5", }, { "J1", "O11", 2, 5, 4, 1, 2, },
				{ "", "О12", 5, 4, 5, 7, 5, }, { "", "О13", 4, 5, 5, 4, 5, }, { "J2", "O21", 2, 5, 4, 7, 8, },
				{ "", "О22", 5, 6, 9, 8, 5, }, { "", "О23", 4, 5, 4, 54, 5, }, { "J3", "О31", 9, 8, 6, 7, 9, },
				{ "", "О32", 6, 1, 2, 5, 4, }, { "", "О33", 2, 5, 4, 2, 4, }, { "", "O34", 4, 5, 2, 1, 5, },
				{ "J4", "O41", 1, 5, 2, 4, 12, }, { "", "O42", 5, 1, 2, 1, 2, }, };

		load(data);

		takeRandomTimes(jobs, 100, 1000);

		simulate(10000000, machines, jobs, actions);
	}
}
