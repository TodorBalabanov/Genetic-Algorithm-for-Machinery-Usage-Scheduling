package eu.veldsoft.gamus;

import java.util.ArrayList;
import java.util.Arrays;
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
	 * Simulation default time limit.
	 */
	private int limit = 0;

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
	 * @param limit
	 *            Simulation default time limit.
	 */
	public WorkUnit(Object[][] data, int limit) {
		this.data = data;
		this.limit = limit;
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
	 * Sum of all operations durations.
	 * 
	 * @return Sum of maximum operations durations.
	 */
	public int sumOfDurations() {
		int sum = 0;

		for (Action action : actions) {
			sum += action.getDuration();
		}

		return sum;
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
					for (int j = 2, index = 0; j < data[i].length; j++, index++) {
						Action action = new Action(index, 0, ((Integer) data[i][j]).intValue(), 0, false,
								machines.get(j - 2), operation);
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
	 * Reset all internal structures.
	 */
	public void reset() {
		for (Machine machine : machines) {
			machine.reset();
		}
		for (Action action : actions) {
			action.reset();
		}
	}

	/**
	 * Generate random, but valid solution.
	 * 
	 * @return List of tasks as single valid solution.
	 */
	public List<Task> generateRandomValidSolution() {
		List<Task> solution = new ArrayList<Task>();

		int index;
		int time = 0;
		for (Job job : jobs) {
			for (Operation operation : job.getOperations()) {
				do {
					// TODO If all actions are with zero duration it will be an
					// infinite loop.
					index = Util.PRNG.nextInt(operation.getActions().size());
				} while (operation.getActions().get(index).getDuration() == 0);

				/*
				 * Only machines which can be used are selected.
				 */
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

	/**
	 * Filter only the earliest actions for each operation when particular action is
	 * not selected by optimization method.
	 */
	public void filterOnlyEarliest() {
		for (Job job : jobs) {
			for (Operation operation : job.getOperations()) {
				/*
				 * Find first valid action.
				 */
				Action earliest = null;
				for (Action action : operation.getActions()) {
					if (action.getDuration() > 0) {
						earliest = action;
						break;
					}
				}

				/*
				 * Find the earliest action for current optimization.
				 */
				for (Action action : operation.getActions()) {
					/*
					 * Only actions which a valid should be checked.
					 */
					if (action.getDuration() <= 0) {
						continue;
					}

					if (action.getStart() < earliest.getStart()) {
						earliest = action;
					}
				}

				/*
				 * Set end time only for the action which will be used for current operation.
				 */
				for (Action action : operation.getActions()) {
					/*
					 * Keep end time only for the selected action.
					 */
					if (action == earliest) {
						action.setEnd(action.getStart() + action.getDuration());
						continue;
					}

					action.setEnd(0);
				}
			}
		}
	}

	/**
	 * Simulate work unit.
	 * 
	 * @param limit
	 *            Limit discrete time for the simulation.
	 * 
	 * @return Counters with the problems found.
	 */
	public double[][] simulate(int limit) {
		/*
		 * Count different problems found. Second array is for coefficients for the
		 * importance of the problem.
		 */
		double[][] counters = { { 0, 0, 0, 0 }, { -0.00001, -0.01, -0.01, -0.01 }, };
		for (int time = 0; time < limit; time++) {
			// System.out.print("=");

			/*
			 * Release all occupied machines for this moment in time.
			 */
			for (Action action : actions) {
				/*
				 * It is not time for release.
				 */
				if (action.getEnd() != time) {
					continue;
				}

				/*
				 * There is no machine to be released.
				 */
				if (action.getMachine() == null) {
					continue;
				}

				/*
				 * Machine was released already.
				 */
				if (action.getMachine().isOccupied() == false) {
					continue;
				}

				/*
				 * Machine was released already.
				 */
				if (action.isDone() == true) {
					continue;
				}

				/*
				 * Machine release.
				 */
				action.setDone(true);
				action.getMachine().setOccupied(false);
				action.getMachine().setAction(null);
			}

			/*
			 * Run operations on the machines.
			 */
			for (Action action : actions) {
				/*
				 * If any action in the operation list of actions is done we do not need to
				 * calculate current loop iteration.
				 */
				if (action.getOperation().isDone() == true) {
					continue;
				}

				/*
				 * If the action is done already there is nothing to be started.
				 */
				if (action.isDone() == true) {
					continue;
				}

				/*
				 * Do not proceed if the action is not selected for this operation.
				 */
				if (action.getEnd() == 0) {
					continue;
				}

				/*
				 * Do not proceed if the action is not possible for this operation. Operation
				 * duration should be positive integer number.
				 */
				if (action.getDuration() <= 0) {
					continue;
				}

				/*
				 * It is not time to start.
				 */
				if (action.getStart() != time) {
					continue;
				}

				/*
				 * If current operation has predecessor and the predecessor is not finished yet
				 * do not calculate the action.
				 */
				Operation previous = action.getOperation().getPrevious();
				if (previous != null && previous.isDone() == false) {
					/*
					 * Operation can not start if the previous operation is not finished.
					 */
					if (action.getStart() == time) {
						counters[0][3]++;
					}

					continue;
				}

				/*
				 * It is a problem if the machine is occupied.
				 */
				if (action.getMachine().isOccupied() == true) {
					/*
					 * Keep track of machine occupied times.
					 */
					counters[0][2]++;

					continue;
				}

				/*
				 * Do the action on the machine.
				 */
				action.getMachine().setOccupied(true);
				action.getMachine().setAction(action);
			}
		}
		// System.out.println();

		/*
		 * The bigger fitness is for better chromosome. It means that if it takes too
		 * much time it is not good.
		 */
		counters[0][0] = totalTimeUsed();

		/*
		 * There should be operations which are not done.
		 */
		counters[0][1] = numberOfUndoneOperations();
		counters[0][1] = (counters[0][1] <= 0 ? 0 : Math.log(counters[0][1] + Math.random()));

		/*
		 * Machine should not collide.
		 */
		counters[0][2] = counters[0][2];
		counters[0][2] = (counters[0][2] <= 0 ? 0 : Math.log(counters[0][2] + Math.random()));

		/*
		 * Operations should run in strict order.
		 */
		counters[0][3] = counters[0][3];
		counters[0][3] = (counters[0][3] <= 0 ? 0 : Math.log(counters[0][3] + Math.random()));

		return counters;
	}

	/**
	 * Simulate work unit.
	 * 
	 * @return Counters with the problems found.
	 */
	public double[][] simulate() {
		return simulate(limit);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WorkUnit [machines=" + machines + ", jobs=" + jobs + ", actions=" + actions + ", data="
				+ Arrays.deepToString(data) + ", limit=" + limit + "]";
	}

}
