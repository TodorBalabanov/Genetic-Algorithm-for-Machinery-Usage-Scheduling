import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
						&& (job.getName().equals(data[i][0].toString()) || data[i][0].toString().equals(""))) {
					Operation previous = null;
					if (job.getOperations().size() > 0) {
						previous = job.getOperations().get(job.getOperations().size() - 1);
					}
					job.getOperations().add(new Operation(data[i][1].toString(), job, previous));
					i++;
				}
			}
		}

		/*
		 * Load actions list.
		 */ {
			int i = 1;
			for (Job job : jobs) {
				for (Operation operation : job.getOperations()) {
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
			for (Operation operation : job.getOperations()) {
				for (Action action : operation.getActions()) {
					action.setStart(min + PRNG.nextInt(max - min + 1));
					action.setEnd(action.getStart() + action.getDuration());
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
				if (action.getStart() == time && action.isDone() == false) {
					if (action.getMachine().isOccupied() == false) {
						action.getMachine().setOccupied(true);
						action.getMachine().setAction(action);
					} else {
						System.err.println("Schedule collision for: " + action);
					}
				}

				/*
				 * It is time the action to be done.
				 */
				if (action.getEnd() == time) {
					if (action.getMachine().isOccupied() == true) {
						action.setDone(true);
						action.getMachine().setOccupied(false);
						action.getMachine().setAction(null);
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
