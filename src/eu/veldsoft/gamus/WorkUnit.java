package eu.veldsoft.gamus;

import java.util.ArrayList;
import java.util.List;

/**
 * Single work unit consists of jobs.
 * 
 * @author Todor Balabanov
 */
class WorkUnit {
	/**
	 * List of the available machines.
	 */
	private List<Machine> machines = new ArrayList<Machine>();

	/**
	 * List of operations taken for this job.
	 */
	private List<Job> jobs = new ArrayList<Job>();

	/**
	 * List of action references.
	 */
	private List<Action> actions = new ArrayList<Action>();

	/**
	 * Reference to data object for the problem to be solved.
	 */
	private Object data[][] = null;

	/**
	 * Clear all action times.
	 */
	private void clearTimes() {
		for (Action action : actions) {
			action.setStart(0);
			action.setEnd(0);
		}
	}

	/**
	 * Constructor with all parameters.
	 * 
	 * @param data
	 *            Problem data object reference.
	 */
	public WorkUnit(Object[][] data) {
		this.data = data;
	}

	/**
	 * Number of machines info.
	 * 
	 * @return Number of possible machines.
	 */
	public int numberOfMachines() {
		return machines.size();
	}

	/**
	 * Data loading from an array.
	 */
	public void load() {
		/*
		 * Clear from previous use.
		 */
		machines.clear();
		jobs.clear();
		actions.clear();

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
					job.getOperations().add(new Operation(data[i][1].toString(), job, previous, null));
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
						Action action = new Action(0, ((Integer) data[i][j]).intValue(), 0, false, machines.get(j - 2),
								operation);
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
	 * Generate random, but valid solution.
	 * 
	 * @return List of tasks as single valid solution.
	 */
	public List<Task> generateRandomValidSolution() {
		List<Task> solution = new ArrayList<Task>();

		int time = 0;
		for (Job job : jobs) {
			for (Operation operation : job.getOperations()) {
				int index = Util.PRNG.nextInt(operation.getActions().size());
				Action action = operation.getActions().get(index);
				solution.add(new Task(index, time));
				time += operation.getMaxDuration() + 1;
			}
		}

		return solution;
	}

	/**
	 * Select random start times of all actions.
	 * 
	 * @param min
	 *            Minimum random value to be used.
	 * @param max
	 *            Maximum random value to be used.
	 */
	public void adjustRandomTimes(int min, int max) {
		clearTimes();

		for (Job job : jobs) {
			for (Operation operation : job.getOperations()) {
				for (Action action : operation.getActions()) {
					action.setStart(min + Util.PRNG.nextInt(max - min + 1));
					action.setEnd(action.getStart() + action.getDuration());
				}
			}
		}
	}

	/**
	 * Select predefined start times of all actions.
	 * 
	 * @param tasks
	 *            List of tasks to be loaded.
	 */
	public void adjustScheduleTimes(List<Task> tasks) {
		clearTimes();

		int t = 0;
		for (Job job : jobs) {
			for (Operation operation : job.getOperations()) {
				Task task = tasks.get(t);
				Action action = operation.getActions().get(task.getIndex());
				action.setStart(task.getTime());
				action.setEnd(action.getStart() + action.getDuration());
				t++;
			}
		}
	}

	// public int[] simulate() {
	// return simulte(limit);
	// }

	/**
	 * Simulate work unit.
	 * 
	 * @param limit
	 *            Limit discrete time for the simulation.
	 * @return Counters with the problems found.
	 */
	public int[] simulate(int limit) {
		/*
		 * Count different problems found.
		 */
		int[] counters = new int[] { 0, 0, 0, 0, 0 };

		for (int time = 0; time < limit; time++) {
			// System.out.print("=");

			/*
			 * Release all occupied machines for this moment in time.
			 */
			for (Action action : actions) {
				if (action.getEnd() == time && action.isDone() == false && action.getMachine() != null) {
					if (action.getMachine().isOccupied() == true) {
						action.setDone(true);
						action.getMachine().setOccupied(false);
						action.getMachine().setAction(null);
					} 
				}
			}

			/*
			 * 
			 */
			for (Action action : actions) {
				/*
				 * If any action in the operation list of actions is done we do
				 * not need to calculate current loop iteration.
				 */
				if (action.getOperation().isDone() == true) {
					continue;
				}
				
				/*
				 * Do not proceed if the action is not selected for this operation. 
				 */
				if(action.getEnd() == 0) {
					continue;
				}

				/*
				 * If current operation has predecessor and the predecessor is
				 * not finished yet do not calculate the action.
				 */
				Operation previous = action.getOperation().getPrevious();
				if (previous != null && previous.isDone() == false) {
					if (action.getStart() == time) {
						counters[3]++;
					}

					continue;
				}

				/*
				 * It is time the action to be done.
				 */
				if (action.getStart() == time && action.getEnd() != 0 && action.getDuration() > 0
						&& action.isDone() == false) {
					if (action.getMachine().isOccupied() == false) {
						/*
						 * Do the action on the machine.
						 */
						action.getMachine().setOccupied(true);
						action.getMachine().setAction(action);
					} else {
						/*
						 * Keep track of machine occupied times.
						 */
						counters[2]++;
					}
				}
			}
		}
		// System.out.println();

		counters[0] = totalTimeUsed();
		counters[1] = numberOfUndoneOperations();

		return counters;
	}

	/**
	 * Counting of unfinished operations.
	 * 
	 * @return Number of unfinished operation.
	 */
	public int numberOfUndoneOperations() {
		int counter = 0;

		for (Job job : jobs) {
			for (Operation operation : job.getOperations()) {
				if (operation.isDone() == false) {
					counter++;
				}
			}
		}

		return counter;
	}

	/**
	 * Report the result of the simulation.
	 * 
	 * @return Text with the report.
	 */
	public String report() {
		String result = "";

		for (Job job : jobs) {
			result += job;
			result += "\n";
			for (Operation operation : job.getOperations()) {
				result += "\t";
				result += operation;
				result += "\n";

				result += "\t";
				result += "\t";
				result += operation.getActiveAction();
				result += "\n";
			}
		}

		return result;
	}

	/**
	 * Total time used report.
	 * 
	 * @return Total time.
	 */
	public int totalTimeUsed() {
		int total = 0;

		for (Action action : actions) {
			if (action.isDone() == false) {
				continue;
			}

			if (action.getEnd() > total) {
				total = action.getEnd();
			}
		}

		return total;
	}
}
