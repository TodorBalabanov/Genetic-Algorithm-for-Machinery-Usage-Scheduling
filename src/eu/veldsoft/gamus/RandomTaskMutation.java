package eu.veldsoft.gamus;

import java.util.List;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.MutationPolicy;

/**
 * Random mutation of single task in the list of tasks.
 * 
 * @author Todor Balabanov
 */
class RandomTaskMutation implements MutationPolicy {

	/**
	 * Mutate random single task in the solution.
	 */
	@Override
	public Chromosome mutate(Chromosome solution) throws MathIllegalArgumentException {
		if (solution instanceof TaskListChromosome == false) {
			return solution;
		}

		/*
		 * Select random task.
		 */
		List<Task> tasks = ((TaskListChromosome) solution).getSolution();
		Task task = tasks.get(Util.PRNG.nextInt(tasks.size()));

		/*
		 * Mutate time.
		 */
		int time = task.getTime() + Util.PRNG.nextInt(21) - 10;
		//TODO Find better way (for example as constructor parameter) to supply random range.
		if (time < 0) {
			time = 0;
		}
		task.setTime(time);

		/*
		 * Mutate machine index.
		 */
		task.setIndex(Util.PRNG.nextInt(((TaskListChromosome) solution).getWork().numberOfMachines()));

		return new TaskListChromosome(tasks, true, ((TaskListChromosome) solution).getWork());
	}
}
